package br.biblioteca.livros.services;

import java.util.List;

import br.biblioteca.livros.entidades.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

	List<User> findAll();

}