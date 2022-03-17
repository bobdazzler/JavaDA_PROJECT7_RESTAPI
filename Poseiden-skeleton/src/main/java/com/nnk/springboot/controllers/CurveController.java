package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
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

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CurveController {
	Logger logger = LoggerFactory.getLogger(CurveController.class);
	/**
	 * Inject Curve Point service
	 */
	@Autowired
	CurvePointService curvePointService;

	/**
	 * this endPoint find all Curve Point, add's it to the model
	 * 
	 * @param request
	 * @param model
	 * @return curvePoint/list
	 */
	@RequestMapping("/curvePoint/list")
	public ModelAndView home(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("userId") != null) {
			Integer userId = (int) request.getSession().getAttribute("userId");
			List<CurvePoint> curvePointOfUserById = curvePointService.gettingCurvePointByUserId(userId);
			model.addAttribute("curvePointOfUser", curvePointOfUserById);
			return new ModelAndView("curvePoint/list");
		}
		return new ModelAndView("/");
	}

	/**
	 * this endPoint returns a curvePoint/add path
	 * 
	 * @param bid
	 * @return curvePoint/add
	 */

	@GetMapping("/curvePoint/add")
	public ModelAndView addBidForm(CurvePoint bid) {
		return new ModelAndView("curvePoint/add");
	}

	/**
	 * this endPoint checks if data is valid and save to db, after saving return's
	 * Curve list
	 * 
	 * @param curvePoint
	 * @param result
	 * @param model
	 * @param request
	 * @return curveList
	 */

	@PostMapping("/curvePoint/validate")
	public ModelAndView validate(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result,
			Model model, HttpServletRequest request) {
		if (!result.hasErrors()) {
			Date asOfTime = new Date();
			Integer userId = (int) request.getSession().getAttribute("userId");
			curvePoint.setAsOfDate(asOfTime);
			curvePoint.setCreationDate(asOfTime);
			curvePoint.setUserId(userId);
			curvePointService.saveCurvePoint(curvePoint);
			logger.info("CurvePoint saved is " + curvePoint);
			return new ModelAndView("redirect:/curvePoint/list");
		}
		return new ModelAndView("curvePoint/add");
	}

	/**
	 * this endPoint get's CurvePoint by Id adds it to model then show to the form
	 * 
	 * @param id
	 * @param model
	 * @return curvePoint/update
	 */
	@GetMapping("/curvePoint/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointService.getCurvePointById(id);
		model.addAttribute("curvePoint", curvePoint);
		logger.info("curvePoint updated");
		return new ModelAndView("curvePoint/update");
	}

	/**
	 * this endPoint check required fields, if valid call service to update Curve and return Curve list
	 * @param id
	 * @param curvePoint
	 * @param result
	 * @param request
	 * @param model
	 * @return curvePoint/list
	 */

	@PostMapping("/curvePoint/update/{id}")
	public ModelAndView updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			HttpServletRequest request, Model model) {
		Integer userId = (int) request.getSession().getAttribute("userId");
		curvePoint.setUserId(userId);
		Date asOfTime = new Date();
		curvePoint.setAsOfDate(asOfTime);
		curvePoint.setCreationDate(asOfTime);
		curvePointService.saveCurvePoint(curvePoint);
		logger.info("curvePoint updated " + curvePoint);
		return new ModelAndView("redirect:/curvePoint/list");
	}
	/**
	 * this endPoin Find's Curve by Id and delete's the Curve, return to Curve list
	 * @param id
	 * @param model
	 * @param request
	 * @return curveList
	 */

	@GetMapping("/curvePoint/delete/{id}")
	public ModelAndView deleteBid(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		
		CurvePoint curve = curvePointService.getCurvePointById(id);
		curvePointService.deleteCurvePoint(curve);
		logger.info("a curve is deleted");
		return new ModelAndView("redirect:/curvePoint/list");
	}
}
