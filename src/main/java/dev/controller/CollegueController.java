package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
import dev.entites.dto.CollegueDto;
import dev.exceptions.ApplicationException;
import dev.service.CollegueService;

@RestController
@RequestMapping("collegue")
public class CollegueController {

	private CollegueService collegueService;

	/**
	 * Constructor
	 *
	 * @param collegueService
	 */
	public CollegueController(CollegueService collegueService) {
		this.collegueService = collegueService;
	}
	
	
	/**
	 * Catch l'exception throw par le service si une annumalie s'est produite dans
	 * le service collegue et renvoie une ResponseEntity avec le statut 404 et le
	 * message de lexception
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<String> onCollgueException(ApplicationException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	

	@GetMapping
	public List<Collegue> getAllCollegues() {

		return this.collegueService.getAllCollegues();
	}

	@PostMapping
	public Collegue postCollegue(@RequestBody @Valid CollegueDto collegueDto) {

		return this.collegueService.postCollegue(collegueDto);
	}
	
	@PutMapping("ajouterChauffeur")
	public Collegue postChauffeur(@RequestBody @Valid String email) {
		return this.collegueService.ajouterChauffeur(email);
	}
	
	@GetMapping("allChauffeurs")
	public List<Collegue> getChauffeurs() {
		return this.collegueService.getAllChauffeur();
	}

}
