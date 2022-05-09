package edu.leicester.co2103.repo;

import org.springframework.data.repository.CrudRepository;

import edu.leicester.co2103.domain.Convenor;

import java.util.Optional;

public interface ConvenorRepository extends CrudRepository<Convenor, Long> {

}
