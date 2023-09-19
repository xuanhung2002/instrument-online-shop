package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
public class Role {	
	
	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private Integer id;
	
	@Column(name = "role_name")
	private String name;

	//add cascade after...
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private List<User> users = new ArrayList<>();

	public Role() {
	}
	public Role(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
	
}
