package com.nnk.springboot.controllers;

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
	Logger logger = LoggerFactory.getLogger(RuleNameController.class);
	/**
	 * Inject RuleName service
	 */
	@Autowired
	RuleNameService ruleNameService;

	/**
	 * find all RuleName, add to model
	 * 
	 * @param model
	 * @param request
	 * @return ruleName/list if a loggIn user
	 */
	@RequestMapping("/ruleName/list")
	public ModelAndView home(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<RuleName> ruleNameByUserId = ruleNameService.listOfRuleNameByUserId(userId);
			model.addAttribute("ruleNameOfAUser", ruleNameByUserId);
			logger.info("list of ruleNames " + ruleNameByUserId.toString());
			return new ModelAndView("ruleName/list");
		}
		return new ModelAndView("/");

	}

	/**
	 * show a path form of ruleName/add
	 * 
	 * @param ruleName
	 * @return ruleName/add
	 */
	@GetMapping("/ruleName/add")
	public ModelAndView showRuleForm(@ModelAttribute("ruleName") RuleName ruleName) {
		return new ModelAndView("ruleName/add");
	}

	/**
	 * check's if data valid and save to db, after saving return RuleName list
	 * 
	 * @param ruleName
	 * @param result
	 * @param model
	 * @param request
	 * @return ruleName/list if valid or ruleName/add if not
	 */
	@PostMapping("/ruleName/validate")
	public ModelAndView validate(@Valid @ModelAttribute("ruleName") RuleName ruleName, BindingResult result,
			Model model, HttpServletRequest request) {
		if (!result.hasErrors()) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			ruleName.setUserId(userId);
			ruleNameService.saveRuleName(ruleName);
			logger.info("Bid saved is " + ruleName);
			return new ModelAndView("redirect:/ruleName/list");
		}
		return new ModelAndView("ruleName/add");
	}

	/**
	 * get RuleName by Id and to model then show to the form
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return ruleName/update
	 */

	@GetMapping("/ruleName/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		RuleName ruleName = ruleNameService.getRuleNameById(id);
		model.addAttribute("ruleName", ruleName);
		logger.info("bid to be updated " + ruleName);
		return new ModelAndView("ruleName/update");
	}

	/**
	 * check required fields, if valid call service to update RuleName and return
	 * RuleName list
	 * 
	 * @param id
	 * @param ruleName
	 * @param result
	 * @param model
	 * @param request
	 * @return path ruleName/list
	 */
	@PostMapping("/ruleName/update/{id}")
	public ModelAndView updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model, HttpServletRequest request) {

		Integer userId = (int) request.getSession().getAttribute("userId");
		ruleName.setUserId(userId);
		ruleNameService.saveRuleName(ruleName);
		return new ModelAndView("redirect:/ruleName/list");
	}

	/**
	 * Find RuleName by Id and delete the RuleName, return to Rule list
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @return ruleName/list
	 */
	@GetMapping("/ruleName/delete/{id}")
	public ModelAndView deleteRuleName(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		RuleName ruleName = ruleNameService.getRuleNameById(id);
		logger.info("ruleName to be deleted is " + ruleName);
		ruleNameService.deleteRuleName(ruleName);
		return new ModelAndView("redirect:/ruleName/list");
	}
}
