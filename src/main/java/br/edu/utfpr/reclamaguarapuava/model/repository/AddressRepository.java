package br.edu.utfpr.reclamaguarapuava.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
