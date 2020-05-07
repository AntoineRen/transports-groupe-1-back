package dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.Collegue;

@Repository
public interface CollegueRepository extends JpaRepository<Collegue, Long> {
	Optional<Collegue> findOneByEmail(String email);

}
