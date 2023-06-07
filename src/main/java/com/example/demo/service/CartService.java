package com.example.demo.service;

import com.example.demo.dto.CartDto;
import com.example.demo.model.*;
import com.example.demo.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public CartDto createCart(CartDto cartDto) {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartDto.class);
    }

    public CartDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        return modelMapper.map(cart, CartDto.class);
    }

    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
    }

    public CartDto updateCart(Long id, CartDto updatedCartDto) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            return null;
        }

        cart.setUser(modelMapper.map(updatedCartDto.getUser(), User.class));
        List<Product> products = updatedCartDto.getProducts().stream()
                .map(productDto -> modelMapper.map(productDto, Product.class))
                .collect(Collectors.toList());
        cart.setProducts(products);
        cart.setActivePromoCode(updatedCartDto.getActivePromoCode());
        cart.setExpirationDate(updatedCartDto.getExpirationDate());

        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }


    public boolean deleteCart(Long id) {
        cartRepository.deleteById(id);
        return false;
    }
}
