package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.models.Wish;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private WishRepository wishRepository;
    private UserRepository userRepository;

    @Autowired
    public WishService(WishRepository wishRepository, UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
    }

    //MAKE SURE ID GOES THROUGH!
    public Wish createWish(Wish wish, long userId) {
        User user = userRepository.findById(userId);
        wish.setUser(user);
        return wishRepository.save(wish);
    }

    public List<Wish> getAllWishesForUserId(long userId) {
        return wishRepository.findByUserId(userId);
    }
}
