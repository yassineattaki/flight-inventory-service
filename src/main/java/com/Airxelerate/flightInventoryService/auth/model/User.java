package com.Airxelerate.flightInventoryService.auth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 2, max = 20, message = "Minimum name length is 2 characters")
	@Column(unique = true)
	private String username;

	@NotBlank
	@Size(min = 2, max = 120, message = "Minimum password length is 2 characters")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();

	public User(@NotBlank @Size(min = 2, max = 20, message = "Minimum name length is 2 characters") String username,
			@NotBlank @Size(min = 2, max = 120, message = "Minimum password length is 2 characters") String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
}
