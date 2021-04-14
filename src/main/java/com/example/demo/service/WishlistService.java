package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.models.Wishlist;
import com.example.demo.repositories.UserRepository;
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

    @Autowired
    public WishlistService(UserRepository userRepository, WishlistRepository wishlistRepository) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist findWishlistByName(String name) {
        List<Wishlist> wishlists = wishlistRepository.findAll();
        Wishlist foundWishlist = new Wishlist();
        for (int i = 0; i < wishlists.size(); i++) {
            if (wishlists.get(i).getWishlist_name().equals(name)) {
                foundWishlist = wishlists.get(i);
            }
        }
        return foundWishlist;
    }

    public List<Wishlist> getAllForUser() {
        List<Wishlist> fullList = wishlistRepository.findAll();
        List<Wishlist> usersList = new ArrayList<>();
        for (int i = 0; i < fullList.size(); i++) {
            if (fullList.get(i).getUser().getId() == getUserId()) {
                usersList.add(fullList.get(i));
            }
            else {
                return usersList;
            }
        }
        return usersList;
    }

    public Wishlist findWishlistById (long id) {
        return wishlistRepository.findById(id);
    }

    public Wishlist createWishlist(Wishlist wishlist) {
        User user = userRepository.findById(getUserId());
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    public void deleteWishlist(Wishlist wishlist) {
        wishlistRepository.delete(wishlist);
    }

    private long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedInUser = authentication.getName();
        return userRepository.findByUsername(currentLoggedInUser).getId();
    }

}