package com.url.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long urlId;
	private String createdAt;
	private String actualURL;
	private String shortURL;
	private String expiryDate;
}
