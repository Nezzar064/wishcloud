package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.models.Wish;
import com.example.demo.models.Wishlist;
import com.example.demo.repositories.WishRepository;
import com.example.demo.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishService {

    private WishRepository wishRepository;
    private WishlistRepository wishlistRepository;

    @Autowired
    public WishService(WishRepository wishRepository, WishlistRepository wishlistRepository) {
        this.wishRepository = wishRepository;
        this.wishlistRepository = wishlistRepository;
    }

    //MAKE SURE ID GOES THROUGH!
    public Wish createWish(Wish wish, long id) {
        Wishlist wishlist = wishlistRepository.findById(id);
        wish.setWishlist(wishlist);
        wish.setReserved(false);
        return wishRepository.save(wish);
    }

    public Long findWishlistIdByWishId(long id) {
        return wishRepository.findById(id).getWishlist().getId();
    }

    public List<Wish> getAllWishesForWishlistId(long wishlistId) {
        return wishRepository.findByWishlistId(wishlistId);
    }

    public void deleteWish(long id) {
        Wish wish = wishRepository.findById(id);
        wishRepository.delete(wish);
    }


    public Wish reserveWish(long id) {
        Wish wish = wishRepository.findById(id);
        wish.setReserved(true);
        return wishRepository.save(wish);
    }


}
