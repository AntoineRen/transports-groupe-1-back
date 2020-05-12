package dev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Annonce;
import dev.entites.Collegue;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

	List<Annonce> findAllByResponsable(Collegue responcable);

	List<Annonce> findAllBylistPassagers(Collegue collegue);
	


}
