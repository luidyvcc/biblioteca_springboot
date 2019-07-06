package br.biblioteca.livros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.biblioteca.livros.entidades.Autor;
import br.biblioteca.livros.repository.AutorRepository;

@Service
public class AutorService {

	@Autowired
	AutorRepository repository;

	public List<Autor> listaAutores() {
		return repository.findAll();
	}

	public void salvaAutor(Autor autor) {
		this.repository.save(autor);
	}

	public void apagaAutor(Long id) {
		Optional<Autor> autor = this.repository.findById(id);
		if (autor.isPresent()) {
			this.repository.delete(autor.get());
		}
	}

	public Autor buscarAutor(Long id) {
		Optional<Autor> autor = this.repository.findById(id);
		return autor.orElse(null);
	}

}
