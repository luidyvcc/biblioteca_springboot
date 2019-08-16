package br.biblioteca.livros.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.biblioteca.livros.entidades.User;
import br.biblioteca.livros.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {

		return userRepository.findByUsername(username);

	}

	@Override
	public List<User> listaUsers() {
		Iterable<User> iterable = userRepository.findAll();

		if (iterable instanceof List) {
			return (List<User>) iterable;
		}

		ArrayList<User> list = new ArrayList<User>();

		if (iterable != null) {
			for (User e : iterable) {
				list.add(e);
			}
		}

		return list;
	}

	public User buscarUser(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return user.orElse(null);
	}
}