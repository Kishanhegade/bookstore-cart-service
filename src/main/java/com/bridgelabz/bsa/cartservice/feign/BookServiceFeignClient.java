package com.bridgelabz.bsa.cartservice.feign;

import com.bridgelabz.bsa.cartservice.responsedto.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("BOOK-SERVICE")
public interface BookServiceFeignClient {

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable long bookId);
}
