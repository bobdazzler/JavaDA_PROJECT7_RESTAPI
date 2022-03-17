package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidService;
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
public class BidListController {
	Logger logger = LoggerFactory.getLogger(BidListController.class);
	/**
	 * Inject's Bid service
	 */
	@Autowired
	private BidService bidService;

	/**
	 * This endPoint returns a path of bid list at log in
	 * 
	 * @param request
	 * @param model
	 * @return bidList/list
	 */
	@RequestMapping("/bidList/list")
	public ModelAndView home(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<BidList> bidListByUserId = bidService.gettingBidListByUserId(userId);
			model.addAttribute("bidListOfUsers", bidListByUserId);
			logger.info("list of bid " + bidListByUserId);
			return new ModelAndView("bidList/list");
		}
		return new ModelAndView("/");
	}

	@GetMapping("/bidList/add")
	public ModelAndView addBidForm(@ModelAttribute("bidList") BidList bid) {
		return new ModelAndView("bidList/add");
	}

	/**
	 * this endPoint save a bidList after passing a validation and then returns
	 * redirect:/bidList/list, If not it fails and return a path of bidList/add
	 * 
	 * @param bid
	 * @param result
	 * @param model
	 * @param request
	 * @return bidList/list or bidList/add
	 */
	@PostMapping("/bidList/validate")
	public ModelAndView validate(@Valid @ModelAttribute("bidList") BidList bid, BindingResult result, Model model,
			HttpServletRequest request) {
		if (!result.hasErrors()) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			bid.setUserId(userId);
			bidService.saveBidList(bid);
			logger.info("Bid saved is " + bid);
			return new ModelAndView("redirect:/bidList/list");
		}
		return new ModelAndView("bidList/add");
	}

	/**
	 * this endPoint returns a path to update a bid list
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/bidList/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get Bid by Id and to model then show to the form
		BidList bidListById = bidService.getBidListById(id);
		model.addAttribute("bidList", bidListById);
		logger.info("bid to be updated " + bidListById);
		return new ModelAndView("bidList/update");

	}

	/**
	 * this endPoint returns a path of bidList/list after saving the bidList to the
	 * database
	 * 
	 * @param id
	 * @param bidList
	 * @param result
	 * @param request
	 * @param model
	 * @return bidList/list
	 */

	@PostMapping("/bidList/update/{id}")
	public ModelAndView updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result,
			HttpServletRequest request, Model model) {
		Integer userId = (int) request.getSession().getAttribute("userId");
		bidList.setUserId(userId);
		logger.info("bid updated " + bidList);
		bidService.saveBidList(bidList);
		return new ModelAndView("redirect:/bidList/list");
	}

	/**
	 * this endPoint deletes a bid from the database by id
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping("/bidList/delete/{id}")
	public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		BidList bid = bidService.getBidListById(id);
		logger.info("bid deleted " + bid);
		bidService.deleteBid(bid);
		return new ModelAndView("redirect:/bidList/list");
	}
}
