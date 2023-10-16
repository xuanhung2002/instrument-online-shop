package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "role")
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

	
}
