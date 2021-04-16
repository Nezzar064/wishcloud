package com.example.demo.controllers;

import com.example.demo.service.WishService;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sharedLists/")
public class ShareController {

    private WishService wishService;
    private WishlistService wishlistService;

    @Autowired
    public ShareController(WishService wishService, WishlistService wishlistService) {
        this.wishService = wishService;
        this.wishlistService = wishlistService;
    }

    @GetMapping(value = "{wishlistId}")
    public String shareWishlist(@PathVariable("wishlistId") long wishlistId, Model model) {
        String currentOwner = wishlistService.findWishlistById(wishlistId).getUser().getName() + " " + wishlistService.findWishlistById(wishlistId).getUser().getLastName();
        model.addAttribute("wishlistOwner", currentOwner);
        model.addAttribute("currentWishlist", wishlistService.findWishlistById(wishlistId));
        model.addAttribute("wishes", wishService.getAllWishesForWishlistId(wishlistId));
        return "shared-list";
    }

    @GetMapping(value = "reserve/{id}")
    public String reserveWish(@PathVariable("id") long wishId) {
        Long wishlistId = wishService.findWishlistIdByWishId(wishId);
        try {
            wishService.reserveWish(wishId);
            return "redirect:/sharedLists/" + wishlistId;
        }
        catch (Exception e) {
            return "redirect:/sharedLists/" + wishlistId;
        }
    }
}
