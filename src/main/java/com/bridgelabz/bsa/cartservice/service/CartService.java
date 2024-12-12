package com.bridgelabz.bsa.cartservice.service;


import com.bridgelabz.bsa.cartservice.exception.CartNotFoundByIdException;
import com.bridgelabz.bsa.cartservice.exception.InvalidRequestException;
import com.bridgelabz.bsa.cartservice.feign.BookServiceFeignClient;
import com.bridgelabz.bsa.cartservice.mapper.CartMapper;
import com.bridgelabz.bsa.cartservice.model.Cart;
import com.bridgelabz.bsa.cartservice.repository.CartRepository;
import com.bridgelabz.bsa.cartservice.responsedto.BookResponse;
import com.bridgelabz.bsa.cartservice.responsedto.CartResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private BookServiceFeignClient bookService;

    @Transactional
    public CartResponse addToCart(long userId, Long bookId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidRequestException("Quantity must be greater than zero");
        }
        BookResponse bookResponse = bookService.getBookById(bookId).getBody();
        Cart cart = cartRepository.findByUserIdAndBookId(userId, bookId)
                .map(existingCart -> {
                    existingCart.setQuantity(bookResponse.getQuantity() + quantity);
                    existingCart.setTotalPrice(bookResponse.getQuantity() * bookResponse.getPrice());
                    return existingCart;
                }).orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setBookId(bookId);
                    newCart.setQuantity(quantity);
                    newCart.setTotalPrice(quantity * bookResponse.getPrice());
                    return newCart;
                });

        cartRepository.save(cart);
        return cartMapper.mapToCartResponse(cart);

    }

    @Transactional
    public CartResponse removeFromCartByCartId(long cartId) {
        return cartRepository.findById(cartId)
                .map(cart->{
                    cartRepository.delete(cart);
                    return cartMapper.mapToCartResponse(cart);
                }).orElseThrow(()->new CartNotFoundByIdException("Unable to remove from cart"));
    }

    @Transactional
    public List<CartResponse> removeFromCartByUserId(long userId) {
        return cartRepository.findAllByUserId(userId)
                .stream()
                .map(cart -> {
                    cartRepository.delete(cart);
                    return cartMapper.mapToCartResponse(cart);
                }).toList();
    }

    public CartResponse updateQuantity(long cartId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidRequestException("Quantity must be greater than zero");
        }
        return cartRepository.findById(cartId)
                .map(cart -> {
                    cart.setQuantity(quantity);
                    cart.setTotalPrice(cart.getTotalPrice()*quantity);
                    cart = cartRepository.save(cart);
                    return cartMapper.mapToCartResponse(cart);
                }).orElseThrow(()->new CartNotFoundByIdException("Unable to update quantity"));
    }

    public List<CartResponse> getAllCartItemsForUser(long userId) {
        return cartRepository.findAllByUserId(userId)
                .stream().map(
                        cart -> cartMapper.mapToCartResponse(cart)
                ).toList();
    }

    public List<CartResponse> getAllCartItems() {
        return cartRepository.findAll()
                .stream().map(cart -> cartMapper.mapToCartResponse(cart)).toList();
    }

    public List<Cart> findAllByUserId(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    public CartResponse getCartByCartId(long cartId) {
        return cartRepository.findById(cartId)
                .map(cart->cartMapper.mapToCartResponse(cart))
                .orElseThrow(()->new CartNotFoundByIdException("Failed to get Cart"));
    }
}
