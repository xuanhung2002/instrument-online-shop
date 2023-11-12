package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Integer id;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Image(String imageUrl, Item item) {
        super();
        this.imageUrl = imageUrl;
        this.item = item;
    }

    public Image(String imageUrl, Brand brand) {
        super();
        this.imageUrl = imageUrl;
        this.brand = brand;
    }
}
