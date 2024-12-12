package com.bridgelabz.bsa.cartservice.repository;


import com.bridgelabz.bsa.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndBookId(long userId, long bookId);

    @Query("SELECT c FROM Cart c WHERE c.userId = :userId")
    List<Cart> findAllByUserId(@Param("userId") Long userId);
}
