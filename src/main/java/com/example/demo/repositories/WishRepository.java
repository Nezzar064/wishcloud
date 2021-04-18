package com.example.demo.repositories;

import com.example.demo.models.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    Wish findById(long wishId);

    List<Wish> findByWishlistId(long wishlistId);

    Wish findSingleByWishlistId(long wishlistId);

}
