package com.nnk.springboot.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceTest {
	@InjectMocks
	RatingService ratingService;
	@Mock
	RatingRepository ratingRepository;
	@Test
	public void testGetListOfRatingOfAUser() {
		List<Rating> rating = new ArrayList<>();
		Rating userRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		Rating userRating1 = new Rating("Moodys Rating1", "Sand PRating1", "Fitch Rating1", 10);
		rating.add(userRating1);
		rating.add(userRating);
		when(ratingRepository.getRatingByUserID(Mockito.anyInt())).thenReturn(rating);
		List<Rating> methodUnderTest= ratingService.getListOfRatingOfAUser(1);
		assertEquals(rating,methodUnderTest);
	}

	@Test
	public void testSaveRating() {
		Rating userRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		when(ratingRepository.save(Mockito.any())).thenReturn(userRating);
		Rating methodUnderTest = ratingService.saveRating(userRating);
		assertEquals(userRating,methodUnderTest);
	}

	@Test
	public void testDeleteRating() {
		Rating userRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		doNothing().when(ratingRepository).delete(userRating);
		ratingService.deleteRating(userRating);
		verify(ratingRepository,times(1)).delete(userRating);
	}
	@Test
	public void testGetRatingByIdWhenRatingExist() {
		Optional <Rating> optional = Optional.of(new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10));
		Rating rating = optional.get();
		when(ratingRepository.findById(Mockito.anyInt())).thenReturn(optional);
		Rating methodUnderTest = ratingService.getRatingById(1);
		assertEquals(methodUnderTest,rating);
	}
	@Test public void testGetRatingByIdWhenRatingNull() {
		Optional <Rating> optional = null;
		when(ratingRepository.findById(Mockito.anyInt())).thenReturn(optional);
		assertThrows(RuntimeException.class, () -> ratingService.getRatingById(1));
	}

}
