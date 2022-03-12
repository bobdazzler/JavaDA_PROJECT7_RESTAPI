package com.nnk.springboot.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
@Service
public class RatingService {
@Autowired
	RatingRepository ratingRepository;
public List<Rating> getListOfRatingOfAUser(int userId){
	return ratingRepository.getRatingByUserID(userId);
}
public Rating saveRating(Rating rating) {
	return ratingRepository.save(rating);
}
public void deleteRating(Rating rating) {
	ratingRepository.delete(rating);
}
public Rating getRatingById(int id) {
	Optional <Rating> optional = ratingRepository.findById(id);
	Rating rating = null;
	if(optional.isPresent()) {
	rating = optional.get();
	}else { 
		throw new RuntimeException(" BidList not found for id :: " + id);
	}
	return rating;
}
}
