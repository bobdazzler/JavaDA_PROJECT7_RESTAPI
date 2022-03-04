package com.nnk.springboot.controllers;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class HomeController
{
	@Autowired
	UserRepository userService;
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetials, Model model)
	{
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add( new SimpleGrantedAuthority("ADMIN"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		if(userDetail.getAuthorities() == authorities) {
		User loggedInUser = userService.findByUserName(userDetail.getUsername());
		model.addAttribute("loggedInUser", loggedInUser);
		request.getSession().setAttribute("userId", loggedInUser.getId());
		return new ModelAndView("home");
		}
		return new ModelAndView("/bidList/list");
	}
	@RequestMapping("/")
	public ModelAndView adminHome(Model model)
	{
		return new ModelAndView( "home");
	}
	
//	@RequestMapping("/admin/home")
//	public ModelAndView adminHome(Model model)
//	{
//		return new ModelAndView( "redirect:/bidList/list");
//	}


}
