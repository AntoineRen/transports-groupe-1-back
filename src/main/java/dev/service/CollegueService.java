package dev.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.entites.dto.CollegueDto;
import dev.repository.CollegueRepository;

@Service
public class CollegueService {

	private CollegueRepository collegueRepository;
	private PasswordEncoder passwordEncoder;

	/**
	 * Constructor
	 *
	 * @param collegueRepository
	 */
	public CollegueService(CollegueRepository collegueRepository, PasswordEncoder passwordEncoder) {
		this.collegueRepository = collegueRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Collegue> getAllCollegues() {

		return this.collegueRepository.findAll();
	}

	@Transactional
	public Collegue postCollegue(CollegueDto collegueDto) {

		Collegue collegue = new Collegue(collegueDto.getNom(), collegueDto.getPrenom(), collegueDto.getEmail(),
				passwordEncoder.encode(collegueDto.getMotDePasse()),
				collegueDto.getNumTelephone());

		this.collegueRepository.save(collegue);

		return collegue;
	}

}
