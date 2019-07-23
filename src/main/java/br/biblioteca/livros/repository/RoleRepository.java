package br.biblioteca.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.biblioteca.livros.entidades.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
