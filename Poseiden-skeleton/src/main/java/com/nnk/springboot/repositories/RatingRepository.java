package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
	 @Query("SELECT rating FROM Rating rating WHERE rating.userId = ?1")
		List<Rating> getRatingByUserID(int userID);
}
