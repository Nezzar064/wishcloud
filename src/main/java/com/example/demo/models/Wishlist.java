package com.example.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    //ADD ERRORS LIKE USER!

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wishlist_id")
    private long id;

    @Column(name = "name")
    private String wishlistName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wishlist")
    private List<Wish> wishes;

    public Wishlist() {
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistname) {
        this.wishlistName = wishlistname;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
