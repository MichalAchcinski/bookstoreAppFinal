package pl.achcinski.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.account.UserRepresentation;
import org.keycloak.representations.idm.PublishedRealmRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/logout")
    String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @RolesAllowed({"ROLE_USER"})
    @GetMapping("/login")
    String login() {
        return "redirect:/";
    }

    @GetMapping
    String index(Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customer = authentication.getName();
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();

        if (!customer.equals("anonymousUser")) {
            String customerName = principal.getAccount().getKeycloakSecurityContext().getIdToken().getGivenName();
            model.addAttribute("welcome", "Witaj " + customerName + "!");
        } else {
            model.addAttribute("welcome", "Witaj gościu!");
        }
        return "index";
    }

}
