package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {	
	
	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private Integer id;
	
	@Column(name = "role_name")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private List<User> users = new ArrayList<>();
		
	
}
