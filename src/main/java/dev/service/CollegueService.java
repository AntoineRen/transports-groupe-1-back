package dev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.entites.RoleCollegue;
import dev.entites.dto.CollegueDto;
import dev.entites.utiles.Role;
import dev.exceptions.CollegueNonTrouveException;
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
	
	//ajouter un chauffeur
	@Transactional
	public Collegue ajouterChauffeur(String mat) {
		Optional<Collegue> collegue = this.collegueRepository.findByMatricule(mat);
		
		Collegue col = new Collegue();
		if(collegue.isPresent()) {
			col = collegue.get();
			List<RoleCollegue> listRole = col.getRoles();
			listRole.add( new RoleCollegue(col, Role.ROLE_CHAUFFEUR));
			collegue.get().setRoles(listRole);
			return col;
			
		}else {
			throw new CollegueNonTrouveException();		
		}

	}
	
	//récupérer les chauffeurs
	public List<Collegue> getAllChauffeur(){
		
		List<Collegue> listChauffeurs = this.getAllCollegues();
		List<Collegue> chauffeurs = new ArrayList<>();
		for( Collegue col : listChauffeurs ) {
			List<RoleCollegue> listRole = col.getRoles();
			for( RoleCollegue role : listRole ) {
				if(role.getRole().equals(Role.ROLE_CHAUFFEUR)) {
					chauffeurs.add(col);
	            }
			}		
		 }	 
		return chauffeurs;
	}

}
