package edu.leicester.co2103.repo;

import org.springframework.data.repository.CrudRepository;

import edu.leicester.co2103.domain.Convenor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConvenorRepository extends CrudRepository<Convenor, Long> {

}