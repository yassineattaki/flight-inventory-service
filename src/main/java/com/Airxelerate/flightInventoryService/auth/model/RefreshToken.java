package com.Airxelerate.flightInventoryService.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "refreshtoken")
@Data
@NoArgsConstructor
public class RefreshToken {
	
		@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  @OneToOne
	  @JoinColumn(name = "user_id", referencedColumnName = "id")
	  private User user;
	  @Column(nullable = false, unique = true)
	  private String token;
	  @Column(nullable = false)
	  private Date expiryDate;

}
