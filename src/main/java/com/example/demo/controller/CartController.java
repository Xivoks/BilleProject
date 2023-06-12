package com.example.demo.controller;

import com.example.demo.dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;

    public CartController(CartService cartService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDto cartDto) {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.ok(createdCart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody CartDto cartDto) {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) throws NotFoundException {
        boolean deleted = cartService.deleteCart(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
