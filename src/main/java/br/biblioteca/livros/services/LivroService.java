package br.biblioteca.livros.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.biblioteca.livros.entidades.Livro;
import br.biblioteca.livros.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	LivroRepository repository;

	public List<Livro> listaLivros() {
		return this.repository.findAll();
	}

	public void salvaLivro(Livro livro) {
		this.repository.save(livro);
	}

}
