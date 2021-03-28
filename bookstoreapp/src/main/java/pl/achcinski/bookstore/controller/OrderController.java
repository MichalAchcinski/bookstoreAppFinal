package pl.achcinski.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.achcinski.bookstore.logic.MailService;
import pl.achcinski.bookstore.logic.OrderService;
import pl.achcinski.bookstore.logic.ShoppingCartService;
import pl.achcinski.bookstore.model.order.OrderRepository;
import pl.achcinski.bookstore.model.order.ShippingInformation;
import pl.achcinski.bookstore.model.order.ShippingInformationRepository;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RolesAllowed({"ROLE_USER"})
@RequestMapping("shop/cart/order")
@Controller
public class OrderController {

    public static final String HTTP_LOCALHOST_8082_API_INSTALMENT_PAYMENT = "http://localhost:8082/api/InstalmentPayment/";
    final OrderRepository orderRepository;
    final ShippingInformationRepository shippingInformationRepository;
    final OrderService orderService;
    final ShoppingCartService shoppingCartService;
    final MailService mailService;
    final JavaMailSender javaMailSender;

    @GetMapping("/shippinginfo")
    String addShippingInformation(Model model) {
        ShippingInformation shippingInformation = orderService.loadShippingInfo();
        model.addAttribute("addShippingInfo", shippingInformation);
        return "shippingInfo";
    }

    @PostMapping("/saveshippinginfo")
    String saveShippingInformation(@ModelAttribute("saveshippinginfo") @Valid ShippingInformation shippingInformation,
                                   BindingResult bindingResult, Model model, HttpServletRequest request) throws MessagingException {
        if (bindingResult.hasErrors()) {
            return "redirect:/shop/cart";
        }

        shippingInformation.setCustomerId(orderService.getCustomerId());
        shippingInformationRepository.save(shippingInformation);
        orderService.createOrderAndSendMails(request);
        String result = "Wysłano zamówienie!\tKolejne kroki znajdziesz na emailu!";
        model.addAttribute("message", result);
        return "orderFinish";
    }

    @PostMapping("/saveshippinginfoinstallments")
    String saveShippingInformationInstallments(@ModelAttribute("saveshippinginfo") @Valid ShippingInformation shippingInformation,
                                               BindingResult bindingResult, Model model, HttpServletRequest request) throws MessagingException {
        RestTemplate rest = new RestTemplate();
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String customerEmail = principal.getAccount().getKeycloakSecurityContext().getIdToken().getEmail();

        if (bindingResult.hasErrors()) {
            return "redirect:/shop/cart";
        }
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerId =  authentication.getName();
        map.add("customerEmail", customerEmail);
        map.add("customerId", customerId);
        shippingInformation.setCustomerId(orderService.getCustomerId());
        shippingInformationRepository.save(shippingInformation);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, new HttpHeaders());
        try {
            ResponseEntity<String> result = rest.postForEntity(HTTP_LOCALHOST_8082_API_INSTALMENT_PAYMENT, httpEntity, String.class);
            model.addAttribute("message", result.getBody());
        }catch (Exception e){
            model.addAttribute("message", "Brak możliwości zakupu ratalnego");
        }
        return "orderFinish";
    }

}
