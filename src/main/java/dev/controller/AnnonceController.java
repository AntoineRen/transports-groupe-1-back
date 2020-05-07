package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Annonce;
import dev.entites.dto.AnnonceDto;
import dev.service.AnnonceService;

@RestController
@RequestMapping("annonce")
public class AnnonceController {

	private AnnonceService annonceService;

	public AnnonceController(AnnonceService annonceService) {
		this.annonceService = annonceService;
	}

	@GetMapping("listAnnonceByResponsable")
	public List<Annonce> getAnnoncesByResponsableEmail() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Annonce> listAnnonceCollegue = annonceService
				.getAnnonceEnCours(annonceService.getAnnoncesByResponcable(email));
		return listAnnonceCollegue;
	}

	@GetMapping("listAnnonceByPassager")
	public List<Annonce> getAnnoncesByPassagerEmail() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Annonce> listAnnonceCollegue = annonceService
				.getAnnonceEnCours(annonceService.getAnnonceByPassager(email));
		return listAnnonceCollegue;
	}

	@GetMapping("listAnnonceEnCours")
	public List<Annonce> getFutureAnnoncesByCollegue() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Annonce> listAnnonceCollegue = annonceService
				.getAnnonceEnCours(annonceService.getAllAnnoncesByCollegue(email));
		return listAnnonceCollegue;
	}

	@GetMapping("listAnnonceHistorique")
	public List<Annonce> getHistoriqueAnnoncesByCollegue() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Annonce> listAnnonceCollegue = annonceService
				.getHistoriqueAnnonce(annonceService.getAllAnnoncesByCollegue(email));
		return listAnnonceCollegue;
	}

	@PostMapping
	public Annonce postAnnonce(@RequestBody @Valid AnnonceDto annonceDto) {

		return this.annonceService.postAnnonce(annonceDto);
	}
}
