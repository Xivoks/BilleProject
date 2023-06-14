package com.example.demo.service;

import com.example.demo.exception.CustomException;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart not found", 404, HttpStatus.NOT_FOUND));
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart updateCart(Long id, Cart updatedCart) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart not found", 404, HttpStatus.NOT_FOUND));

        cart.setUser(updatedCart.getUser());
        List<Product> products = new ArrayList<>(updatedCart.getProducts());
        cart.setProducts(products);
        cart.setActivePromoCode(updatedCart.getActivePromoCode());
        cart.setExpirationDate(updatedCart.getExpirationDate());

        return cartRepository.save(cart);
    }

    public Cart addItemToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CustomException("Cart not found", 404, HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found", 404, HttpStatus.NOT_FOUND));

        cart.getProducts().add(product);

        return cartRepository.save(cart);
    }

    public boolean deleteCart(Long id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isPresent()) {
            cartRepository.deleteById(id);
            return true;
        } else {
            throw new CustomException("Cart not found", 404, HttpStatus.NOT_FOUND);
        }
    }
}
