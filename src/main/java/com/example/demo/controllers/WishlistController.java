package com.example.demo.controllers;

import com.example.demo.models.Wish;
import com.example.demo.models.Wishlist;
import com.example.demo.service.WishService;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/wishlists/")
public class WishlistController {

    private WishlistService wishlistService;
    private WishService wishService;

    @Autowired
    public WishlistController(WishlistService wishlistService, WishService wishService) {
        this.wishlistService = wishlistService;
        this.wishService = wishService;
    }

    //WISHLISTS
    @GetMapping(value ="createWishlist")
    public String showWishlistForm(Wishlist wishlist) {
        return "add-wishlist";
    }

    @PostMapping(value = "addWishlist")
    public String createNewWish(@Valid Wishlist wishlist, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-wishlist";
        }
        model.addAttribute("successMessage", "Wishlist has been registered successfully!");
        model.addAttribute("wishlist", wishlist);
        wishlistService.createWishlist(wishlist);
        return "redirect:/wishlists/lists";


    }

    @GetMapping(value = "lists")
    public String showWishlists(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String baseUrl = request.getScheme()
                + "://" + request.getServerName()
                + ":" + request.getServerPort()
                + request.getContextPath()
                + "/sharedLists/";
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("listUserName","Greetings " + auth.getName());
        model.addAttribute("wishlist", wishlistService.getAllForUser());
        return "list-of-wishlist";
    }

    //Needs fix for getting wishes as well and deleting them
    @GetMapping(value = "delete/{id}")
    public String deleteWishlist(@PathVariable("id") long id, Model model) {
        try {
            wishlistService.deleteWishlist(id);
            model.addAttribute("wishlists", wishlistService.getAllForUser());
            return "redirect:/wishlists/lists";
        } catch (Exception e) {
            return "redirect:/wishlists/lists";
        }
    }

    //WISHES
    @GetMapping(value = "createWish/{wishlistId}")
    public String makeAwish(@PathVariable(value = "wishlistId") long wishlistId, Model model) {
        Wish wish = new Wish();
        model.addAttribute("currentWishlist", wishlistService.findWishlistById(wishlistId));
        model.addAttribute("wish", wish);
        return "add-wish";
    }

    @RequestMapping(value = "addWish/{wishlistId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String createNewWish(@Valid Wish wish, BindingResult bindingResult, @PathVariable(value = "wishlistId") long wishlistId, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-wish";
        }
        model.addAttribute("currentWishlist", wishlistService.findWishlistById(wishlistId));
        model.addAttribute("successMessage", "Wish has been registered successfully!");
        model.addAttribute("wish", wish);
        wishService.createWish(wish, wishlistId);
        return "add-wish";


    }

    @GetMapping(value = "itemList/{wishlistId}")
    public String showWishes(Model model, @PathVariable(value = "wishlistId") long wishlistId) {
        model.addAttribute("currentWishlist", wishlistService.findWishlistById(wishlistId));
        model.addAttribute(wishlistService.findWishlistById(wishlistId));
        model.addAttribute("wishes", wishService.getAllWishesForWishlistId(wishlistId));
        return "list-of-wishes";
    }

    @GetMapping(value = "deleteWish/{id}")
    public String deleteWish(@PathVariable("id") long wishId, Model model) {
        Long wishlistId = wishService.findWishlistIdByWishId(wishId);
        try {
            model.addAttribute("wish", wishService.getAllWishesForWishlistId(wishId));
            wishService.deleteWish(wishId);
            return "redirect:/wishlists/itemList/" + wishlistId;
        } catch (Exception e) {
            return "redirect:/wishlists/itemList/" + wishlistId;
        }
    }

    /*
    @GetMapping(value = "share/{wishlistId}")
    public String shareWishlist(@PathVariable("wishlistId") long wishlistId, Model model) {
        String currentOwner = wishlistService.findWishlistById(wishlistId).getUser().getName() + " " + wishlistService.findWishlistById(wishlistId).getUser().getLastName();
        model.addAttribute("wishlistOwner", currentOwner);
        model.addAttribute("currentWishlist", wishlistService.findWishlistById(wishlistId));
        model.addAttribute("wishes", wishService.getAllWishesForWishlistId(wishlistId));
        return "shared-list";
    }

    @GetMapping(value = "share/reserve/{id}")
    public String reserveWish(@PathVariable("id") long wishId) {
        Long wishlistId = wishService.findWishlistIdByWishId(wishId);
        try {
            wishService.reserveWish(wishId);
            return "redirect:/wishlists/share/" + wishlistId;
        }
        catch (Exception e) {
            return "redirect:/wishlists/share/" + wishlistId;
        }
    }

     */

    //TODO: maybe add a copy link button for wishlist sharing


}
