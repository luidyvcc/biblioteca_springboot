package br.biblioteca.livros.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.entidades.Role;
import br.biblioteca.livros.entidades.User;
import br.biblioteca.livros.services.RoleService;
import br.biblioteca.livros.services.SecurityService;
import br.biblioteca.livros.services.UserService;
import br.biblioteca.livros.validator.LoginValidator;
import br.biblioteca.livros.validator.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final String TEMPLATE = "users";

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService serviceRole;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private LoginValidator loginValidator;

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("users/login", "userForm", new User());
	}

	@PostMapping("/autentication")
	public ModelAndView autentication(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			Model model) {

		loginValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("users/login");
		}

		securityService.login(userForm.getUsername(), userForm.getPassword());

		return new ModelAndView("redirect:/");
	}

	@GetMapping("/list")
	public ModelAndView index() {
		List<User> users = this.userService.listaUsers();
		return new ModelAndView(TEMPLATE + "/index", "listaUsers", users);
	}

	@GetMapping(value = "/novo")
	public ModelAndView create(@ModelAttribute User user) {

		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
		Iterable<Role> roles = this.serviceRole.listaRoles();
		modelAndView.addObject("listaRoles", roles);
		return modelAndView;

	}

	/*
	 * @GetMapping(value = "/gravar") public ModelAndView registration() {
	 * 
	 * return new ModelAndView(TEMPLATE + "/registration", "userForm", new User());
	 * }
	 */

	@PostMapping(value = "/gravar")
	public ModelAndView registrationform(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {

			ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
			Iterable<Role> roles = this.serviceRole.listaRoles();
			modelAndView.addObject("listaRoles", roles);
			return modelAndView;

		}

		String password = userForm.getPassword();

		userService.save(userForm);

		try {

			// securityService.login(userForm.getUsername(), password);

			return new ModelAndView("redirect:/" + TEMPLATE + "/list");

		} catch (Exception e) {

			return new ModelAndView("redirect:/" + TEMPLATE + "/novo");
		}
	}

	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		User user = this.userService.buscarUser(id);
		Iterable<Role> roles = this.serviceRole.listaRoles();
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
		modelAndView.addObject("listaRoles", roles);
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/" + TEMPLATE + "/login";
	}

}