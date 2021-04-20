package com.example.demo.models;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "wishes")
public class Wish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "wish_id_seq", sequenceName = "wish_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wish_id_seq")
    @Column(name = "wish_id")
    private long id;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @Column(name = "item_name")
    @NotEmpty(message = "*Please provide the name of the item!")
    private String itemName;

    @Column(name = "link")
    @NotEmpty(message = "*Please provide a link to the item!")
    private String link;

    @Column(name = "price")
    @Range(min = -2147483648, max = 2147483647, message= "*'Price' must be a number between -2147483648 and 2147483647 Example: 9346")
    @NotEmpty
    private String price;

    @Column(name = "reserved")
    private Boolean reserved;

    public Wish() {
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
