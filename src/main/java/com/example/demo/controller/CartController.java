package com.example.demo.controller;

import com.example.demo.dto.CartDto;
import com.example.demo.exception.CustomException;
import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController(CartService cartService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        logger.info("Creating a new cart");
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Cart createdCart = cartService.createCart(cart);
        CartDto createdCartDto = modelMapper.map(createdCart, CartDto.class);
        logger.info("Cart created with ID: {}", createdCartDto.getId());
        return ResponseEntity.ok(createdCartDto);
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        logger.info("Getting all carts");
        List<Cart> cartList = cartService.getAllCarts();
        List<CartDto> cartDtoList = cartList.stream()
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
        logger.info("Returned {} carts", cartDtoList.size());
        return ResponseEntity.ok(cartDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long id) {
        logger.info("Getting cart with ID: {}", id);
        Cart cart = cartService.getCartById(id);
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        if (cart != null) {
            logger.info("Cart found with ID: {}", id);
        }
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long id, @RequestBody CartDto updateCartDto) {
        logger.info("Updating cart with ID: {}", id);
        Cart cart = modelMapper.map(updateCartDto, Cart.class);
        Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart != null) {
            CartDto cartDto = modelMapper.map(cart, CartDto.class);
            logger.info("Cart updated with ID: {}", id);
            return ResponseEntity.ok(cartDto);
        }
        logger.info("Cart not found with ID: {}", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        logger.info("Deleting cart with ID: {}", id);
        boolean deleted = cartService.deleteCart(id);
        if (deleted) {
            logger.info("Cart deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            throw new CustomException("Cart not found", 404, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        logger.info("Adding item with ID: {} to cart with ID: {}", productId, cartId);
        Cart cart = cartService.addItemToCart(cartId, productId);
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        logger.info("Item added to cart with ID: {}", cartId);
        return ResponseEntity.ok(cartDto);
    }
}
