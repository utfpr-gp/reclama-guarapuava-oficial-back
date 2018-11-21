package br.edu.utfpr.reclamaguarapuava.occurrences.repositories;

import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
