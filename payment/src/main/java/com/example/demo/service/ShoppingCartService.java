package com.example.demo.service;


import com.example.demo.entity.CartItem;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@RequiredArgsConstructor
@Service
@Log4j2
public class ShoppingCartService {
    final CartItemRepository cartItemRepository;
    final BookRepository bookRepository;

    public List<CartItem> listCartItems(String customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    public double getAmountTotal(String customerId) {
        double total = 0;
        List<CartItem> listCart = listCartItems(customerId);
        for (CartItem cartItem : listCart) {
            total += cartItem.getBook().getPrice();
        }
        BigDecimal totalBD = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        return totalBD.doubleValue();
    }

}
