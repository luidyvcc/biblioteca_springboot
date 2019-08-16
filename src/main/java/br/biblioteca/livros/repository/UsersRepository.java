package br.biblioteca.livros.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.biblioteca.livros.entidades.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);

}
