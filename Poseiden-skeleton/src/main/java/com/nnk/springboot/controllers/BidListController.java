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
	// TODO: Inject Bid service
	@Autowired
	private BidService bidService;

	@RequestMapping("/bidList/list")
	public ModelAndView home(HttpServletRequest request, Model model) {
		// TODO: call service find all bids to show to the view
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<BidList> bidListByUserId = bidService.gettingBidListByUserId(userId);
			model.addAttribute("bidListOfUsers", bidListByUserId);
			logger.info("list of Users are " + bidListByUserId);
			return new ModelAndView("bidList/list");
		}
		return new ModelAndView("/");
	}

	@GetMapping("/bidList/add")
	public ModelAndView addBidForm(@ModelAttribute("bidList") BidList bid) {
		return new ModelAndView("bidList/add");
	}

	@PostMapping("/bidList/validate")
	public ModelAndView validate(@Valid @ModelAttribute("bidList") BidList bid, BindingResult result, Model model,
			HttpServletRequest request) {
		// TODO: check data valid and save to db, after saving return bid list
		if (!result.hasErrors()) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			bid.setUserId(userId);
			bidService.saveBidList(bid);
			logger.info("Bid saved is " + bid);
			return new ModelAndView("redirect:/bidList/list");
		}
		return new ModelAndView("bidList/add");
	}

	@GetMapping("/bidList/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		// TODO: get Bid by Id and to model then show to the form
		BidList bidListById = bidService.getBidListById(id);
		model.addAttribute("bidList", bidListById);
		logger.info("bid to be updated " + bidListById);
		return new ModelAndView("bidList/update");

	}

	@PostMapping("/bidList/update/{id}")
	public ModelAndView updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result,
			HttpServletRequest request, Model model) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid
		Integer userId = (int) request.getSession().getAttribute("userId");
		bidList.setUserId(userId);
		bidService.saveBidList(bidList);
		logger.info("bid updated " + bidList);
		return new ModelAndView("redirect:/bidList/list");
	}

	@GetMapping("/bidList/delete/{id}")
	public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		BidList bid = bidService.getBidListById(id);
		bidService.deleteBid(bid);
		logger.info("bid deleted " +bid);
		return new ModelAndView("redirect:/bidList/list");
	}
}
