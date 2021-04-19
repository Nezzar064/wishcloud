package com.example.demo.models;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wishlists")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class Wishlist {

    @Id
    @SequenceGenerator(name = "wishlist_id_seq", sequenceName = "wishlist_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishlist_id_seq")
    @Column(name = "wishlist_id")
    private long id;

    @Column(name = "name")
    private String wishlistName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
