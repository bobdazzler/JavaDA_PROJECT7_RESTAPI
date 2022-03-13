package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

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
public class TradeController {
	Logger logger = LoggerFactory.getLogger(TradeController.class);
	// TODO: Inject Trade service
	@Autowired
	TradeService tradeService;

	@RequestMapping("/trade/list")
	public ModelAndView home(Model model, HttpServletRequest request) {
		// TODO: find all Trade, add to model
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<Trade> tradeListOfAUser = tradeService.listOfTradeByAUser(userId);
			model.addAttribute("tradeListOfUser", tradeListOfAUser);
			logger.info("list of trades are " + tradeListOfAUser.toString());
			return new ModelAndView("trade/list");
		}
		return new ModelAndView("/");
	}

	@GetMapping("/trade/add")
	public ModelAndView addUser(Trade bid) {
		return new ModelAndView("trade/add");
	}

	@PostMapping("/trade/validate")
	public ModelAndView validate(@Valid Trade trade, BindingResult result, Model model, HttpServletRequest request) {
		// TODO: check data valid and save to db, after saving return Trade list
		if (!result.hasErrors()) {
		Integer userId = (int) request.getSession().getAttribute("userId");
		trade.setUserId(userId);
		tradeService.saveATrade(trade);
		logger.info("trade is saved");
		return new ModelAndView("trade/list");
		}
		return new ModelAndView("trade/add");
	}

	@GetMapping("/trade/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		// TODO: get Trade by Id and to model then show to the form
		Trade trade = tradeService.getTradeById(id);
		model.addAttribute("bidListOfUsers", trade);
		logger.info("trade updated " + trade.toString());
		return new ModelAndView("trade/update");
	}

	@PostMapping("/trade/update/{id}")
	public ModelAndView updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result,
			HttpServletRequest request,Model model) {
		// TODO: check required fields, if valid call service to update Trade and return
		// Trade list
		Integer userId = (int) request.getSession().getAttribute("userId");
		trade.setUserId(userId);
		tradeService.saveATrade(trade);
		return new ModelAndView("redirect:/trade/list");
	}

	@GetMapping("/trade/delete/{id}")
	public ModelAndView deleteTrade(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		// TODO: Find Trade by Id and delete the Trade, return to Trade list
		Trade trade = tradeService.getTradeById(id);
		tradeService.deleteATrade(trade);
		logger.info("trade updated " + trade.toString());
		return new ModelAndView("redirect:/trade/list");
	}
}
