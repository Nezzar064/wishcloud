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

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    private UserRepository userRepository;
    private WishlistRepository wishlistRepository;
    private WishRepository wishRepository;

    @Autowired
    public WishlistService(UserRepository userRepository, WishlistRepository wishlistRepository, WishRepository wishRepository) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
        this.wishRepository = wishRepository;
    }

    public List<Wishlist> getAllForUser() {
        List<Wishlist> fullList = wishlistRepository.findAll();
        List<Wishlist> usersList = new ArrayList<>();
        for (int i = 0; i < fullList.size(); i++) {
            if (fullList.get(i).getUser().getId() == getUserId()) {
                usersList.add(fullList.get(i));
            }
        }
        return usersList;
    }

    public Wishlist findWishlistById(long id) {
        return wishlistRepository.findById(id);
    }

    public Wishlist createWishlist(Wishlist wishlist) {
        User user = userRepository.findById(getUserId());
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    public void deleteWishlist(long id) {
        Wishlist wishlist = wishlistRepository.findById(id);
        boolean wishExists = true;
        while(wishExists) {
            if (wishRepository.findAll().contains(id)) {
                Wish wish = wishRepository.findById(id);
                wishlist.getWishes().remove(wish);
            }
            else {
                wishExists = false;
            }
        }
        wishlistRepository.delete(wishlist);
    }

    private long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedInUser = authentication.getName();
        return userRepository.findByUsername(currentLoggedInUser).getId();
    }

}
