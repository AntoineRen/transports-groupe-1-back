package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
import dev.entites.dto.CollegueDto;
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

	@GetMapping
	public List<Collegue> getAllCollegues() {

		return this.collegueService.getAllCollegues();
	}

	@PostMapping
	public Collegue postCollegue(@RequestBody @Valid CollegueDto collegueDto) {

		return this.collegueService.postCollegue(collegueDto);
	}

}
