package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart updateCart(Long id, Cart updatedCart) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            return null;
        }

        cart.setUser(updatedCart.getUser());
        List<Product> products = new ArrayList<>(updatedCart.getProducts());
        cart.setProducts(products);
        cart.setActivePromoCode(updatedCart.getActivePromoCode());
        cart.setExpirationDate(updatedCart.getExpirationDate());

        return cartRepository.save(cart);
    }

    public Cart addItemToCart(Long cartId, Long productId) throws NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new NotFoundException("Cart not found");
        }

        Product product = getProductById(productId);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }

        cart.getProducts().add(product);

        return cartRepository.save(cart);
    }

    public boolean deleteCart(Long id) throws NotFoundException {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isPresent()) {
            cartRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException("Cart not found");
        }
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

}
