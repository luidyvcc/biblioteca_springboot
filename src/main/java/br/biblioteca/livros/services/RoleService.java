package br.biblioteca.livros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.biblioteca.livros.entidades.Role;
import br.biblioteca.livros.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository repository;

	public List<Role> listaRoles() {
		return repository.findAll();
	}

	public void salvaRole(Role role) {
		this.repository.save(role);
	}

	public void apagaRole(Long id) {
		Optional<Role> role = this.repository.findById(id);
		if (role.isPresent()) {
			this.repository.delete(role.get());
		}
	}

	public Role buscarRole(Long id) {
		Optional<Role> role = this.repository.findById(id);
		return role.orElse(null);
	}

}
