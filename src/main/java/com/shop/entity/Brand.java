package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "brand")
public class Brand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "brand_id")
	private Integer id;
	
	@Column(name = "brand_name")
	private String name;
	
	@Column(name = "brand_country")
	private String country;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "brand")
	private Image logo;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	private List<Item> instruments = new ArrayList<Item>();

	public Brand() {
	}

	public Brand(Integer id, String name, String country, Image logo, List<Item> instruments) {
		this.id = id;
		this.name = name;
		this.country = country;
		this.logo = logo;
		this.instruments = instruments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public Image getLogo() {
		return logo;
	}

	public void setLogo(Image logo) {
		this.logo = logo;
	}

	public List<Item> getInstruments() {
		return instruments;
	}
	public void setInstruments(List<Item> instruments) {
		this.instruments = instruments;
	}
	
	
	
}
