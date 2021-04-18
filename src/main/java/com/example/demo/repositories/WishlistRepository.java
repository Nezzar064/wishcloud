package com.example.demo.repositories;

import com.example.demo.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findById(long id);

    Wishlist findWishlistByWishlistName(String name);

}
