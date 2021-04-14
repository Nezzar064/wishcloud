package com.example.demo.controllers;

import com.example.demo.models.Wish;
import com.example.demo.models.Wishlist;
import com.example.demo.service.WishService;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/wishlists/")
public class WishlistController {

    private WishlistService wishlistService;
    private WishService wishService;

    @Autowired
    public WishlistController(WishlistService wishlistService, WishService wishService) {
        this.wishlistService = wishlistService;
        this.wishService = wishService;
    }

    //WISHLISTS
    @GetMapping("createWishlist")
    public String showWishlistForm(Wishlist wishlist) {
        return "add-wishlist";
    }

    @PostMapping(value = "addWishlist")
    public ModelAndView createNewWish(@Valid Wishlist wishlist, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-wishlist");
        } else {
            wishlistService.createWishlist(wishlist);
            modelAndView.addObject("successMessage", "Wishlist has been registered successfully!");
            modelAndView.addObject("wishlist", new Wishlist());
            modelAndView.setViewName("add-wishlist");

        }
        return modelAndView;
    }

    @GetMapping("list")
    public String showWishlists(Model model) {
        model.addAttribute("wishlist", wishlistService.getAllForUser());
        return "list-of-wishlist";
    }

    @GetMapping("delete/{id}")
    public String deleteWishlist(@PathVariable("id") long id, Model model) {
        Wishlist wishlist = wishlistService.findWishlistById(id);
        wishlistService.deleteWishlist(wishlist);
        model.addAttribute("wishlists", wishlistService.getAllForUser());
        return "redirect:/wishlists/list";
    }

    //WISHES
    // FIX GETTING WISHLIST WHEN SAVING
    @GetMapping(value="{current_wishlist}/createWish")
    public ModelAndView makeAwish(@PathVariable (value = "current_wishlist") String wishListName) {
        ModelAndView modelAndView = new ModelAndView();
        Wish wish = new Wish();
        modelAndView.addObject("wishlist_name", wishListName);
        modelAndView.addObject("wish", wish);
        modelAndView.setViewName("add-wish");
        return modelAndView;
    }

    @PostMapping(value = "{current_wishlist}/addWish")
    public ModelAndView createNewWish(@Valid Wish wish, BindingResult bindingResult, @PathVariable (value = "current_wishlist") String wishListName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("wishlist_name", wishListName);
        Wishlist wishlist = wishlistService.findWishlistByName(wishListName);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-wish");
        } else {
            wishService.createWish(wish, wishlist);
            modelAndView.addObject("successMessage", "Wish has been registered successfully!");
            modelAndView.addObject("wish", new Wish());
            modelAndView.setViewName("add-wish");

        }
        return modelAndView;
    }

    @GetMapping("{current_wishlist}/list")
    public String showWishes(Model model, @PathVariable (value = "current_wishlist") String wishListName) {
        model.addAttribute("wishlist_name", wishListName);
        model.addAttribute("wishes", wishService.getAllWishesForUserId());
        return "list-of-wishes";
    }

    @GetMapping("deleteWish/{id}")
    public String deleteWish(@PathVariable("id") long id, Model model) {
        Wishlist wishlist = wishlistService.findWishlistById(id);
        wishlistService.deleteWishlist(wishlist);
        model.addAttribute("wishlists", wishlistService.getAllForUser());
        return "redirect:/wishlists/list";
    }



    //MAKE SHARE BUTTON
}
