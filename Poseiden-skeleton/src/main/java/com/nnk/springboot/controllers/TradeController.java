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
import org.springframework.web.bind.annotation.ModelAttribute;
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
	/**
	 * Inject Trade service
	 */
	@Autowired
	TradeService tradeService;
/**
 *find all Trade, add to model
 * @param model
 * @param request
 * @return trade/list
 */
	@RequestMapping("/trade/list")
	public ModelAndView home(Model model, HttpServletRequest request) {
		
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<Trade> tradeListOfAUser = tradeService.listOfTradeByAUser(userId);
			model.addAttribute("tradeListOfUser", tradeListOfAUser);
			logger.info("list of trades are " + tradeListOfAUser.toString());
			return new ModelAndView("trade/list");
		}
		return new ModelAndView("/");
	}
/**
 *  show form / path trade/add
 * @param bid
 * @return trade/add
 */
	@GetMapping("/trade/add")
	public ModelAndView addUser(Trade bid) {
		return new ModelAndView("trade/add");
	}
	/**
	 * check data valid and save to db, after saving return Trade list
	 * @param trade
	 * @param result
	 * @param model
	 * @param request
	 * @return trade/list if valid, if not valid return trade/add
	 */

	@PostMapping("/trade/validate")
	public ModelAndView validate(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model, HttpServletRequest request) {
		
		if (!result.hasErrors()) {
		Integer userId = (int) request.getSession().getAttribute("userId");
		trade.setUserId(userId);
		tradeService.saveATrade(trade);
		logger.info("trade is saved");
		return new ModelAndView("trade/list");
		}
		return new ModelAndView("trade/add");
	}
/**
 * gets Trade by Id add to model then show to the form
 * @param id
 * @param model
 * @param request
 * @return trade/update
 */
	@GetMapping("/trade/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		
		Trade trade = tradeService.getTradeById(id);
		model.addAttribute("trade", trade);
		logger.info("trade updated " + trade.toString());
		return new ModelAndView("trade/update");
	}
/**
 * check required fields, if valid call service to update Trade and return Trade list
 * @param id
 * @param trade
 * @param result
 * @param request
 * @param model
 * @return trade/list
 */
	@PostMapping("/trade/update/{id}")
	public ModelAndView updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result,
			HttpServletRequest request,Model model) {		
		Integer userId = (int) request.getSession().getAttribute("userId");
		trade.setUserId(userId);
		tradeService.saveATrade(trade);
		logger.info("tradeupdated");
		return new ModelAndView("redirect:/trade/list");
	}
	/**
	 * Find Trade by Id and delete the Trade, return to Trade list
	 * @param id
	 * @param model
	 * @param request
	 * @return trade/list
	 */

	@GetMapping("/trade/delete/{id}")
	public ModelAndView deleteTrade(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		Trade trade = tradeService.getTradeById(id);
		tradeService.deleteATrade(trade);
		logger.info("trade updated " + trade.toString());
		return new ModelAndView("redirect:/trade/list");
	}
}
