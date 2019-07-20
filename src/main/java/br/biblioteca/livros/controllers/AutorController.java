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

import br.biblioteca.livros.entidades.Autor;
import br.biblioteca.livros.services.AutorService;

@Controller
@RequestMapping("/autores")
public class AutorController {

	private static final String TEMPLATE = "autores";

	@Autowired
	private AutorService service;

	@GetMapping("/list")
	public ModelAndView index() {
		List<Autor> autores = this.service.listaAutores();
		return new ModelAndView(TEMPLATE + "/index", "listaAutores", autores);
	}

	@GetMapping("/novo")
	public ModelAndView create(@ModelAttribute Autor autor) {
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
		return modelAndView;
	}

	@PostMapping("/gravar")
	public ModelAndView storage(@ModelAttribute("autor") @Valid Autor autor, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
			return modelAndView;
		}

		this.service.salvaAutor(autor);
		return new ModelAndView("redirect:" + TEMPLATE + "/list");
	}

	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Autor autor = this.service.buscarAutor(id);
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
		modelAndView.addObject("autor", autor);
		return modelAndView;
	}

	@GetMapping("/excluir/{id}")
	public ModelAndView destroy(@PathVariable("id") Long id) {
		this.service.apagaAutor(id);
		return new ModelAndView("redirect:" + TEMPLATE + "/list");
	}

}
