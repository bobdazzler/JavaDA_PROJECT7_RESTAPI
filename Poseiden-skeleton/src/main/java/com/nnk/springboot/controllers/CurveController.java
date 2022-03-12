package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

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
public class CurveController {
	Logger logger = LoggerFactory.getLogger(CurveController.class);
	// TODO: Inject Curve Point service
	@Autowired
	CurvePointService curvePointService;

	@RequestMapping("/curvePoint/list")
	public ModelAndView home(HttpServletRequest request, Model model) {
		// TODO: find all Curve Point, add to model
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<CurvePoint> curvePointOfUserById = curvePointService.gettingCurvePointByUserId(userId);
			model.addAttribute("curvePointOfUser", curvePointOfUserById);
			return new ModelAndView("curvePoint/list");
		}
		return new ModelAndView("/");
	}

	@GetMapping("/curvePoint/add")
	public ModelAndView addBidForm(CurvePoint bid) {
		return new ModelAndView("curvePoint/add");
	}

	@PostMapping("/curvePoint/validate")
	public ModelAndView validate(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, Model model,
			HttpServletRequest request) {
		// TODO: check data valid and save to db, after saving return Curve list
		if (!result.hasErrors()) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			curvePoint.setUserId(userId);
			curvePointService.saveCurvePoint(curvePoint);
			logger.info("Bid saved is " + curvePoint);
			return new ModelAndView("redirect:/curvePoint/list");
		}
		return new ModelAndView("curvePoint/add");
	}

	@GetMapping("/curvePoint/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get CurvePoint by Id and to model then show to the form
		CurvePoint curvePoint = curvePointService.getCurvePointById(id);
		model.addAttribute("curveList", curvePoint);
		logger.info("curvePoint updated");
		return new ModelAndView("curvePoint/update");
	}

	@PostMapping("/curvePoint/update/{id}")
	public ModelAndView updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			HttpServletRequest request,Model model) {
		// TODO: check required fields, if valid call service to update Curve and return
		// Curve list
		Integer userId = (int) request.getSession().getAttribute("userId");
		curvePoint.setUserId(userId);
		curvePointService.saveCurvePoint(curvePoint);
		return new ModelAndView("redirect:/curvePoint/list");
	}

	@GetMapping("/curvePoint/delete/{id}")
	public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		// TODO: Find Curve by Id and delete the Curve, return to Curve list
		CurvePoint curve = curvePointService.getCurvePointById(id);
		curvePointService.deleteCurvePoint(curve);
		logger.info("a bid is deleted");
		return new ModelAndView("redirect:/curvePoint/list");
	}
}
