package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.entites.Annonce;
import dev.entites.Collegue;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

	List<Annonce> findAllByResponsable(Collegue responcable);

	List<Annonce> findAllBylistPassagers(Collegue collegue);

	@Query("select a from Annonce a where date_Depart > current_date()")
	List<Annonce> findAllWithDateDepartAfter();

	int countBylistPassagers(Collegue collegue);

	int countByResponsable(Collegue collegue);
	
	List<Annonce> findAllByListPassagers(Collegue collegue); 

}
