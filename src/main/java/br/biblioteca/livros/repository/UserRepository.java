package br.biblioteca.livros.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import br.biblioteca.livros.entidades.Role;
import br.biblioteca.livros.entidades.User;

@Repository
public class UserRepository {

	List<User> users = new ArrayList<>();

	UserRepository() {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User admin = new User("admin", passwordEncoder.encode("123456"));
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		admin.setRole(role);
		users.add(admin);

	}

	public User findByUsername(String username) {

		User user = null;

		for (User u : users) {

			if (u.getUsername().equals(username)) {
				user = u;
			}

		}

		System.out.println("lido " + user);

		return user;
	}

	public void save(User user) {
		users.add(user);
		System.out.println("adicionado " + user);
	}

	public List<User> findAll() {
		return users;
	}

}