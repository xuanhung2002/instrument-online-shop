package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

//	@Column(name = "logo")
//	private String logo;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	private List<Item> instruments = new ArrayList<Item>();

	public Brand() {
	}
	
	public Brand(Integer id, String name, String country) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
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

	public List<Item> getInstruments() {
		return instruments;
	}
	public void setInstruments(List<Item> instruments) {
		this.instruments = instruments;
	}
	
	
	
}
