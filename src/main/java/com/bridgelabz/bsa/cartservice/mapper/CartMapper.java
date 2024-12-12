package com.bridgelabz.bsa.cartservice.mapper;


import com.bridgelabz.bsa.cartservice.model.Cart;
import com.bridgelabz.bsa.cartservice.responsedto.CartResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CartMapper {


    public CartResponse mapToCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setBookId(cart.getBookId());
        cartResponse.setUserId(cart.getUserId());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setTotalPrice(cart.getTotalPrice());
        return cartResponse;
    }
}
