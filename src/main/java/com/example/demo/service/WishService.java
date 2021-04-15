package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.models.Wish;
import com.example.demo.models.Wishlist;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WishRepository;
import com.example.demo.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private WishRepository wishRepository;
    private UserRepository userRepository;
    private WishlistRepository wishlistRepository;

    @Autowired
    public WishService(WishRepository wishRepository, UserRepository userRepository, WishlistRepository wishlistRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
    }

    //MAKE SURE ID GOES THROUGH!
    public Wish createWish(Wish wish, long id) {
        User user = userRepository.findById(getUserId());
        Wishlist wishlist = wishlistRepository.findById(id);
        wish.setWishlist(wishlist);
        wish.setUser(user);
        return wishRepository.save(wish);
    }

    public List<Wish> getAllWishesForUserId() {
        return wishRepository.findByUserId(getUserId());
    }

    public void deleteWish(Wish wish) {
        wishRepository.delete(wish);
    }

    //maybe make this as a static bean or move it to userservice
    private long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedInUser = authentication.getName();
        return userRepository.findByUsername(currentLoggedInUser).getId();
    }

    //DELETE WISH FUNCTION
}
