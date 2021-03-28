package com.example.demo.service;


import com.example.demo.entity.Book;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.ShippingInformation;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShippingInformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final MailService mailService;
    private final ShoppingCartService shoppingCartService;
    private final ShippingInformationRepository shippingInformationRepository;
    private final CartItemRepository cartItemRepository;


    public ResponseEntity<String> createInstallmentsOrderAndSendMails(String customerEmail, String customerId) {
        List<String> bookNameList = getUserBooksToOrder(customerId);
        StringBuilder sb = new StringBuilder();
        for (String s : bookNameList) {
            sb.append(s);
            sb.append(", ");
        }
        Order order = new Order();
        order.setPrice(shoppingCartService.getAmountTotal(customerId));
        order.setCustomerId(customerId);
        order.setBoughtBooks(sb.toString());
        orderRepository.save(order);

        String mailSubject = "Twoje zamówienie!";
        String mailText = "Dane odnośnie twojego zamówienia poniżej!";
        String bankAccountNumber = "Wpłacaj pieniądze w formie 3 miesięcznych rat do 10dnia miesiąca -" +
                " pieniądze przelewem na: 06 1160 2202 0000 0001 9078 2527";
        String price = "Cena wynosi: " + shoppingCartService.getAmountTotal(customerId) + " zł";
        String boughtBooks = "Książki: " + sb.toString() + "\r\n";

        String mailText2 = "Dane do wysyłki poniżej";
        String name = loadShippingInfo(customerId).getFirstName() + " " + loadShippingInfo(customerId).getLastName();
        String address = loadShippingInfo(customerId).getAddress();
        String city = loadShippingInfo(customerId).getPostcode() + " " + loadShippingInfo(customerId).getCity();
        int phoneNumber = loadShippingInfo(customerId).getPhoneNumber();

        String result = "<h1>" + mailText + "</h1>" + "<h2>" + bankAccountNumber + "<h2>" + "<h2>" + price + "<h2>" +
                "<h2>" + boughtBooks + "<h2>" + "<h1>" + "________________________________________" + "<h1>" + "<h1>" +
                mailText2 + "</h1>" + "<h2>" + name + "<h2>" + "<h2>" + address + "<h2>" + "<h2>" + city + "<h2>" +
                "<h2>" + phoneNumber + "<h2>";

        try {
            mailService.sendMail("bookstoreappma@gmail.com", mailSubject, result, true);
            mailService.sendMail(customerEmail,mailSubject,result,true);

        } catch (MessagingException e) {
           log.error("Error during send Mail " + Arrays.toString(e.getStackTrace()));
        }
        return new ResponseEntity<>( "Wysłano zamówienie!\tPłatność ratalna!\tKolejne kroki znajdziesz na emailu!", HttpStatus.CREATED);

    }

    public List<String> getUserBooksToOrder(String customerId) {
        List<CartItem> cartItemList = cartItemRepository.findByCustomerId(customerId);
        List<Book> bookList = cartItemList.stream().map(CartItem::getBook).collect(Collectors.toList());
        return bookList.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    public ShippingInformation loadShippingInfo(String customerId) {
        ShippingInformation shippingInformation;
        if (!shippingInformationRepository.existsByCustomerId(customerId)) {
            shippingInformation = new ShippingInformation();
        } else {
            shippingInformation = shippingInformationRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + customerId));
        }

        return shippingInformation;
    }
}
