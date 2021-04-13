package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.models.Wish;
import com.example.demo.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class WishController {

    // https://www.javaguides.net/2019/08/spring-boot-jpa-hibernate-one-to-many-example-tutorial.html

    private WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }


    @RequestMapping(value="/addWish", method = RequestMethod.GET)
    public ModelAndView makeAwish() {
        ModelAndView modelAndView = new ModelAndView();
        Wish wish = new Wish();
        modelAndView.addObject("wish", wish);
        modelAndView.setViewName("addAwish");
        return modelAndView;
    }

    //FIND A WAY IN WHICH YOU GET THE CURRENT LOGGED IN USERS ID WHEN YOU PRESS "ADD A WISH" BUTTON!
    //doesnt work atm, requestmethod get not supported?

    @RequestMapping(value = "/addWish/{user_id}", method = RequestMethod.POST)
    public ModelAndView createNewWish(@Valid Wish wish, BindingResult bindingResult, @PathVariable(value = "user_id") long userId) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("addAwish");
        } else {
            wishService.createWish(wish, userId);
            modelAndView.addObject("successMessage", "Wish has been registered successfully!");
            modelAndView.addObject("wish", new Wish());
            modelAndView.setViewName("addAwish");

        }
        return modelAndView;
    }

    @GetMapping("/{user_id}/wishlist")
    public String showUpdateForm(Model model, @PathVariable(value = "user_id") long userId) {
        model.addAttribute("wishes", wishService.getAllWishesForUserId(userId));
        return "showWishes";
    }

    /*
    @DeleteMapping("/instructors/{instructorId}/courses/{courseId}")
    public ResponseEntity < ? > deleteCourse(@PathVariable(value = "instructorId") Long instructorId,
        @PathVariable(value = "courseId") Long courseId) throws ResourceNotFoundException {
        return courseRepository.findByIdAndInstructorId(courseId, instructorId).map(course - > {
            courseRepository.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
            "Course not found with id " + courseId + " and instructorId " + instructorId));
     */
}
