package com.nnk.springboot.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CustomUserDetails;

@Controller
public class HomeController {
	@Autowired
	UserRepository userService;

	@RequestMapping("/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * home endPoint for the admin
	 * 
	 * @param request
	 * @param customUserDetials
	 * @param model
	 * @return bidList/list
	 */
	@RequestMapping("/admin/home")
	public ModelAndView adminHome(HttpServletRequest request,
			@AuthenticationPrincipal CustomUserDetails customUserDetials, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		User loggedInUser = userService.findByUserName(userDetail.getUsername());
		model.addAttribute("loggedInUser", loggedInUser);
		request.getSession().setAttribute("userId", loggedInUser.getId());
		return new ModelAndView("redirect:/bidList/list");
	}

}
