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

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Cart createdCart = cartService.createCart(cart);
        CartDto createdCartDto = modelMapper.map(createdCart, CartDto.class);
        return ResponseEntity.ok(createdCartDto);
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        List<Cart> cartList = cartService.getAllCarts();
        List<CartDto> cartDtoList = cartList.stream()
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        if (cart != null) {
            CartDto cartDto = modelMapper.map(cart, CartDto.class);
            return ResponseEntity.ok(cartDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long id, @RequestBody CartDto updateCartDto) {
        Cart cart = modelMapper.map(updateCartDto, Cart.class);
        Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart != null) {
            CartDto cartDto = modelMapper.map(cart, CartDto.class);
            return ResponseEntity.ok(cartDto);
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
