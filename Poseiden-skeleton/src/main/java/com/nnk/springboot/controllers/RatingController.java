package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RatingController {
	Logger logger = LoggerFactory.getLogger(RatingController.class);
	// TODO: Inject Rating service
	@Autowired
	RatingService ratingService;

	@RequestMapping("/rating/list")
	public ModelAndView home(HttpServletRequest request, Model model) {
		// TODO: find all Rating, add to model
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<Rating> ratingByUser = ratingService.getListOfRatingOfAUser(userId);
			model.addAttribute("ratingByUser", ratingByUser);
			return new ModelAndView("rating/list");
		}
		return new ModelAndView("home");
	}

	@GetMapping("/rating/add")
	public ModelAndView addRatingForm(Rating rating) {
		return new ModelAndView("rating/add");
	}

	@PostMapping("/rating/validate")
	public ModelAndView validate(@Valid Rating rating, BindingResult result, HttpServletRequest request, Model model) {
		// TODO: check data valid and save to db, after saving return Rating list
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			rating.setId(userId);
			ratingService.saveRating(rating);
			logger.info("Bid saved is " + rating);
			return new ModelAndView("rating/add");
		}
		return new ModelAndView("home");

	}

	@GetMapping("/rating/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		// TODO: get Rating by Id and to model then show to the form
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<Rating> ratingByUserId = ratingService.getListOfRatingOfAUser(userId);
			for (Rating rating : ratingByUserId)
				if (rating.getId() == id) {
					model.addAttribute("bidListOfUser", rating);
					logger.info("bid has been updated");
				}
			return new ModelAndView("rating/update");
		}
		return new ModelAndView("home");

	}

	@PostMapping("/rating/update/{id}")
	public ModelAndView updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Rating and
		// return Rating list
		ratingService.saveRating(rating);
		return new ModelAndView("redirect:/rating/list");
	}

	@GetMapping("/rating/delete/{id}")
	public ModelAndView deleteRating(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		// TODO: Find Rating by Id and delete the Rating, return to Rating list
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<Rating> ratingListByUserId = ratingService.getListOfRatingOfAUser(userId);
			for (Rating rating : ratingListByUserId)
				if (rating.getId() == id) {
					ratingService.deleteRating(rating);
					logger.info("a bid is deleted");
				}
			return new ModelAndView("redirect:/rating/list");
		}
		return new ModelAndView("home");

	}
}
