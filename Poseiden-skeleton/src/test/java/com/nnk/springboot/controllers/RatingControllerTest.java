package com.nnk.springboot.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingControllerTest {
	@Mock
	RedirectAttributes attribute;
	@Mock
	Model model;
	@Mock
	MockHttpServletRequest request;
	@Mock
	BindingResult result;
	@Mock
	RatingService ratingService;
	@InjectMocks
	RatingController ratingController;
	@Test
	public void testHomeWhenHttpRequestisNull() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(null);
		List<Rating> rating = new ArrayList<>();
		Rating userRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		Rating userRating1 = new Rating("Moodys Rating1", "Sand PRating1", "Fitch Rating1", 10);
		rating.add(userRating1);
		rating.add(userRating);
		when(ratingService.getListOfRatingOfAUser(Mockito.anyInt())).thenReturn(rating);
		ModelAndView modelAndView = ratingController.home(request, model);
		assertEquals(modelAndView.getViewName(),"/");
	}
	@Before
	public void setUp() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(1);
	}
	@Test
	public void testHomeWhenHttpRequestExist() {
		List<Rating> rating = new ArrayList<>();
		Rating userRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		Rating userRating1 = new Rating("Moodys Rating1", "Sand PRating1", "Fitch Rating1", 10);
		rating.add(userRating1);
		rating.add(userRating);
		when(ratingService.getListOfRatingOfAUser(Mockito.anyInt())).thenReturn(rating);
		ModelAndView modelAndView = ratingController.home(request, model);
		assertEquals(modelAndView.getViewName(),"rating/list");
	}
	@Test
	public void testShowRatingForm() {
		Rating userRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		ModelAndView modelAndView = ratingController.showRatingForm(userRating);
		assertEquals(modelAndView.getViewName(),"rating/add");
	}

	@Test
	public void testValidate() {
		Rating userRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		when(result.hasErrors()).thenReturn(false);
		when(ratingService.saveRating(Mockito.any())).thenReturn(userRating);
		ModelAndView modelAndView = ratingController.validate(userRating, result, request, model);
		assertEquals(modelAndView.getViewName(),"rating/list");
	}

	@Test
	public void testShowUpdateForm() {
		Rating userRating = new Rating(1,"Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		when(ratingService.getRatingById(Mockito.anyInt())).thenReturn(userRating);
		ModelAndView modelAndView = ratingController.showUpdateForm(1, request, model);
		assertEquals(modelAndView.getViewName(),"rating/update");
	}

	@Test
	public void testUpdateRating() {
		Rating userRating = new Rating(1,"Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		when(ratingService.saveRating(Mockito.any())).thenReturn(userRating);
		ModelAndView modelAndView = ratingController.updateRating(1, userRating, result, request, model);
		assertEquals(modelAndView.getViewName(),"redirect:/rating/list");
	}

	@Test
	public void testDeleteRating() {
		Rating userRating = new Rating(1,"Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		when(ratingService.getRatingById(Mockito.anyInt())).thenReturn(userRating);
		doNothing().when(ratingService).deleteRating(userRating);
		ratingController.deleteRating(1, model);
		verify(ratingService,times(1)).deleteRating(userRating);
	}

}
