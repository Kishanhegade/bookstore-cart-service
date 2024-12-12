package com.bridgelabz.bsa.cartservice.controller;


import com.bridgelabz.bsa.cartservice.responsedto.CartResponse;
import com.bridgelabz.bsa.cartservice.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @PostMapping("/carts/add")
    public ResponseEntity<CartResponse> addToCart
            (@RequestHeader("userId") String authHeader,
             @RequestParam Long bookId,
             @RequestParam int quantity)
    {
        Long userId = Long.valueOf(authHeader);
        CartResponse cartResponse = cartService.addToCart(userId, bookId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @PatchMapping("/carts/{cartId}/update-quantity")
    public ResponseEntity<CartResponse> updateQuantity(
            @PathVariable long cartId,
            @RequestParam int quantity) {
        CartResponse cartResponse = cartService.updateQuantity(cartId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponse);
    }

    @DeleteMapping("/carts/{cartId}/remove")
    public ResponseEntity<CartResponse> removeFromCartByCartId(@PathVariable long cartId) {
        CartResponse cartResponse = cartService.removeFromCartByCartId(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponse);
    }

    @DeleteMapping("/carts/user/remove-all")
    public ResponseEntity<List<CartResponse>> removeFromCartByUserId(@RequestHeader("userId") String authHeader) {
        Long userId = Long.valueOf(authHeader);
        List<CartResponse> cartResponses = cartService.removeFromCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponses);
    }

    @GetMapping("/carts/user")
    public ResponseEntity<List<CartResponse>> getAllCartItemsForUser(@RequestHeader("userId") String authHeader) {
        Long userId = Long.valueOf(authHeader);
        List<CartResponse> cartResponses = cartService.getAllCartItemsForUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponses);
    }

    @GetMapping("/carts/{cartId}")
    public ResponseEntity<CartResponse> getCartByCartId(@PathVariable long cartId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByCartId(cartId));
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getAllCartItems() {
        List<CartResponse> cartResponses = cartService.getAllCartItems();
        return ResponseEntity.status(HttpStatus.OK).body(cartResponses);
    }
}