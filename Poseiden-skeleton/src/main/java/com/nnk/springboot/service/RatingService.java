package com.nnk.springboot.service;
import java.util.List;

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
}
