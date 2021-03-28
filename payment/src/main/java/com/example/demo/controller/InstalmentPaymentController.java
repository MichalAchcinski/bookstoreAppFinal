package com.example.demo.controller;


import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RolesAllowed({"ROLE_USER"})
@RequestMapping("/api/")

@RequiredArgsConstructor
public class InstalmentPaymentController {

    private final OrderService orderService;

    @PostMapping("InstalmentPayment")
    public ResponseEntity<String> createInstallmentsOrderAndSendMails(@RequestParam String customerEmail,
                                                                      @RequestParam String customerId) {
        return orderService.createInstallmentsOrderAndSendMails(customerEmail, customerId);
    }
}
