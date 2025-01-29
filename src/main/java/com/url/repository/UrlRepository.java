package com.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.model.UrlModel;

@Repository
public interface UrlRepository extends JpaRepository<UrlModel, Long>{
	
	UrlModel findByShortURL(String shortURL);
	
	UrlModel findByActualURL(String actualURL);
	

}
