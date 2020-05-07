package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Annonce;
import dev.entites.dto.AnnonceDto;
import dev.repository.CollegueRepo;
import dev.service.AnnonceService;

@RestController
@RequestMapping("annonce")
public class AnnonceController {

	private AnnonceService annonceService;
	private CollegueRepo collegueRepo;
	

	public AnnonceController(AnnonceService annonceService, CollegueRepo collegueRepo) {
		this.annonceService = annonceService;
		this.collegueRepo = collegueRepo;
	}

	@GetMapping
	public List<Annonce> getAllAnnonces() {

		return this.annonceService.getAllAnnonces();
	}

	@PostMapping
	public Annonce postAnnonce(@RequestBody @Valid AnnonceDto annonceDto) {
		return this.annonceService.postAnnonce(annonceDto);
	}
}
