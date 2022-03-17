package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	/**
	 * Inject Rating service
	 */
	@Autowired
	RatingService ratingService;

	/**
	 * finds all Rating, add's it to model
	 * 
	 * @param request
	 * @param model
	 * @return rating/list
	 */
	@RequestMapping("/rating/list")
	public ModelAndView home(HttpServletRequest request, Model model) {

		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<Rating> ratingByUser = ratingService.getListOfRatingOfAUser(userId);
			model.addAttribute("ratingByUser", ratingByUser);
			return new ModelAndView("rating/list");
		}
		return new ModelAndView("/");
	}

	/**
	 *
	 * @param rating
	 * @return path rating/add
	 */

	@GetMapping("/rating/add")
	public ModelAndView showRatingForm(Rating rating) {
		return new ModelAndView("rating/add");
	}

	/**
	 * checks data, if valid save to db, after saving return Rating list
	 * 
	 * @param rating
	 * @param result
	 * @param request
	 * @param model
	 * @return path ratingList if valid or rating/add if not valid
	 */
	@PostMapping("/rating/validate")
	public ModelAndView validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result,
			HttpServletRequest request, Model model) {
		if (!result.hasErrors()) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			rating.setUserId(userId);
			ratingService.saveRating(rating);
			logger.info("rating saved is " + rating);
			return new ModelAndView("redirect:/rating/list");
		}
		return new ModelAndView("rating/add");
	}

	/**
	 * get Rating by Id, add to model then show to the form
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/rating/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		Rating rating = ratingService.getRatingById(id);
		model.addAttribute("rating", rating);
		logger.info("rating to be updated " + rating);
		return new ModelAndView("rating/update");

	}

	/**
	 * check required fields, if valid call service to update Rating and return
	 * Rating list
	 * 
	 * @param id
	 * @param rating
	 * @param result
	 * @param request
	 * @param model
	 * @return rating/list
	 */
	@PostMapping("/rating/update/{id}")
	public ModelAndView updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			HttpServletRequest request, Model model) {

		Integer userId = (int) request.getSession().getAttribute("userId");
		rating.setUserId(userId);
		ratingService.saveRating(rating);
		return new ModelAndView("redirect:/rating/list");
	}

	/**
	 * Find Rating by Id and delete the Rating, return to Rating list
	 * 
	 * @param id
	 * @param model
	 * @return rating/list
	 */
	@GetMapping("/rating/delete/{id}")
	public ModelAndView deleteRating(@PathVariable("id") Integer id, Model model) {

		Rating rating = ratingService.getRatingById(id);
		ratingService.deleteRating(rating);
		logger.info("a rating is deleted");
		return new ModelAndView("redirect:/rating/list");
	}
}
