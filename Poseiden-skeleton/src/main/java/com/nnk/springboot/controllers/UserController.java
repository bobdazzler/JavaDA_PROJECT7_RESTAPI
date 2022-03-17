package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class UserController {
	/**
	 * inject userRepository
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * returns a list of users in db
	 * 
	 * @param model
	 * @return user/list
	 */
	@RequestMapping("/user/list")
	public ModelAndView home(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return new ModelAndView("user/list");
	}

	/**
	 * shows a path or form to add a user or create a user
	 * 
	 * @param bid
	 * @return user/add path
	 */
	@GetMapping("/user/add")
	public ModelAndView addUser(User bid) {
		return new ModelAndView("user/add");
	}

	/**
	 * validate a user and then save the user to db
	 * 
	 * @param user
	 * @param result
	 * @param model
	 * @return user/list if there is no error, then user/add if there is an error
	 */
	@PostMapping("/user/validate")
	public ModelAndView validate(@Valid User user, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			model.addAttribute("users", userRepository.findAll());
			return new ModelAndView("redirect:/user/list");
		}
		return new ModelAndView("user/add");
	}

	/**
	 * shows user form for update
	 * 
	 * @param id
	 * @param model
	 * @return user/update
	 */
	@GetMapping("/user/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");
		model.addAttribute("user", user);
		return new ModelAndView("user/update");
	}

	/**
	 * save updated user to db
	 * 
	 * @param id
	 * @param user
	 * @param result
	 * @param model
	 * @return path user/update if there is an error else return user/list
	 */
	@PostMapping("/user/update/{id}")
	public ModelAndView updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return new ModelAndView("user/update");
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setId(id);
		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());
		return new ModelAndView("redirect:/user/list");
	}

	/**
	 * get user by id and delete user
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/user/delete/{id}")
	public ModelAndView deleteUser(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		return new ModelAndView("redirect:/user/list");
	}
}
