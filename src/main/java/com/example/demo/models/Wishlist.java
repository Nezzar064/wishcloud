package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "wishlists")
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "wishlist_id_seq", sequenceName = "wishlist_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishlist_id_seq")
    @Column(name = "wishlist_id")
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a wishlist name")
    private String wishlistName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wishlist", cascade=CascadeType.ALL)
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
