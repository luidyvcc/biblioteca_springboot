package br.biblioteca.livros.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.entidades.Role;
import br.biblioteca.livros.services.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {

	private static final String TEMPLATE = "roles";

	@Autowired
	private RoleService service;

	@GetMapping("/list")
	public ModelAndView index() {
		List<Role> roles = this.service.listaRoles();
		return new ModelAndView(TEMPLATE + "/index", "listaRoles", roles);
	}

	@GetMapping("/novo")
	public ModelAndView create(@ModelAttribute Role role) {
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
		return modelAndView;
	}

	@PostMapping("/gravar")
	public ModelAndView storage(@ModelAttribute("role") @Valid Role role, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
			return modelAndView;
		}

		this.service.salvaRole(role);
		return new ModelAndView("redirect:/" + TEMPLATE + "/list");
	}

	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Role role = this.service.buscarRole(id);
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
		modelAndView.addObject("role", role);
		return modelAndView;
	}

	@GetMapping("/excluir/{id}")
	public ModelAndView destroy(@PathVariable("id") Long id) {
		this.service.apagaRole(id);
		return new ModelAndView("redirect:/" + TEMPLATE + "/list");
	}

}
