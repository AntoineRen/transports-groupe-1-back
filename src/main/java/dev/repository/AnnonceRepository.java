package dev.repository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.entites.Annonce;
import dev.entites.Collegue;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

	List<Annonce> findAllByResponsable(Collegue responcable);

	List<Annonce> findAllBylistPassagers(Collegue collegue);
	
	@Query("select a from Annonce a where date_Depart > current_date()")
	List <Annonce> findAllWithDateDepartAfter();





}
