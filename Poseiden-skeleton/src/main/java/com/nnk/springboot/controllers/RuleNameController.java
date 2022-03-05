package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

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
public class RuleNameController {
	// TODO: Inject RuleName service
	Logger logger = LoggerFactory.getLogger(RuleNameController.class);
	@Autowired
	RuleNameService ruleNameService;

	@RequestMapping("/ruleName/list")
	public ModelAndView home(Model model, HttpServletRequest request) {
		// TODO: find all RuleName, add to model
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<RuleName> ruleNameByUserId = ruleNameService.listOfRuleNameByUserId(userId);
			model.addAttribute("bidListOfUsers", ruleNameByUserId);
			logger.info("list of Users are " + ruleNameByUserId.toString());
			return new ModelAndView("ruleName/list");
		}
		return new ModelAndView("home");

	}

	@GetMapping("/ruleName/add")
	public ModelAndView addRuleForm(@ModelAttribute("ruleName") RuleName ruleName) {
		return new ModelAndView("ruleName/add");
	}

	@PostMapping("/ruleName/validate")
	public ModelAndView validate(@Valid @ModelAttribute("ruleName") RuleName ruleName, BindingResult result,
			Model model, HttpServletRequest request) {
		// TODO: check data valid and save to db, after saving return RuleName list
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			ruleName.setUserId(userId);
			ruleNameService.saveRuleName(ruleName);
			logger.info("Bid saved is " + ruleName);
			return new ModelAndView("ruleName/list");
		}
		return new ModelAndView("home");
	}

	@GetMapping("/ruleName/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		// TODO: get RuleName by Id and to model then show to the form
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<RuleName> ruleNameListByUserId = ruleNameService.listOfRuleNameByUserId(userId);
			for (RuleName ruleName : ruleNameListByUserId)
				if (ruleName.getId() == id) {
					model.addAttribute("bidListOfUser", ruleName);
					logger.info("bid has been updated");
				}
			return new ModelAndView("ruleName/update");
		}
		return new ModelAndView("home");

	}

	@PostMapping("/ruleName/update/{id}")
	public ModelAndView updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update RuleName and
		// return RuleName list
		ruleNameService.saveRuleName(ruleName);
		return new ModelAndView("redirect:/ruleName/list");
	}

	@GetMapping("/ruleName/delete/{id}")
	public ModelAndView deleteRuleName(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		// TODO: Find RuleName by Id and delete the RuleName, return to Rule list
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<RuleName> ruleNameListByUserId = ruleNameService.listOfRuleNameByUserId(userId);
			for (RuleName ruleName : ruleNameListByUserId) {
				if (ruleName.getId() == id) {
					ruleNameService.deleteRuleName(ruleName);
					logger.info("ruleName has been deleted");
				}
				return new ModelAndView("redirect:/ruleName/list");
			}
		}
		return new ModelAndView("home");
	}
}
