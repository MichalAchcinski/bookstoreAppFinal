package pl.achcinski.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.achcinski.bookstore.logic.ShoppingCartService;
import pl.achcinski.bookstore.model.cartitem.CartItem;
import pl.achcinski.bookstore.model.cartitem.CartItemRepository;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequiredArgsConstructor
@RolesAllowed({"ROLE_USER"})
@RequestMapping("/shop")
@Controller
public class ShoppingCartController {
    final ShoppingCartService shoppingCartService;
    final CartItemRepository cartItemRepository;

    @GetMapping("/cart")
    public String showShoppingCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customer = authentication.getName();
        List<CartItem> cartItems = shoppingCartService.listCartItems(customer);
        double amountTotal = shoppingCartService.getAmountTotal(customer);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("amountTotal", amountTotal);
        return "shoppingCart";
    }

    @GetMapping(value = "/addbooktocart/{id}")
    String addBookToCart(@PathVariable("id") int bookId, RedirectAttributes redirAttrs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerId = authentication.getName();
        shoppingCartService.addItemCart(bookId, customerId);
        redirAttrs.addFlashAttribute("message", "dodano ksiazke do koszyka!");
        return "redirect:/books";
    }

    @GetMapping("/deletecartitem/{id}")
    String deleteBook(@PathVariable("id") int id, RedirectAttributes redirAttrs) {
        CartItem current = cartItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        cartItemRepository.delete(current);
        String result = "Usunięto książkę z koszyka: " + current.getBook().getTitle();
        redirAttrs.addFlashAttribute("message", result);
        return "redirect:/shop/cart";
    }

}
