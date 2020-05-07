package dev;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.RoleCollegue;
import dev.entites.utiles.Role;
import dev.entites.utiles.Version;
import dev.repository.CollegueRepo;
import dev.repository.AnnonceRepository;
import dev.repository.VersionRepo;

/**
 * Code de démarrage de l'application. Insertion de jeux de données.
 */
@Component
public class StartupListener {

	private String appVersion;
	private VersionRepo versionRepo;
	private PasswordEncoder passwordEncoder;
	private CollegueRepo collegueRepo;
	private AnnonceRepository annonceRepo;

	public StartupListener(@Value("${app.version}") String appVersion, VersionRepo versionRepo,
			PasswordEncoder passwordEncoder, CollegueRepo collegueRepo, AnnonceRepository annonceRepo) {
		this.appVersion = appVersion;
		this.versionRepo = versionRepo;
		this.passwordEncoder = passwordEncoder;
		this.collegueRepo = collegueRepo;
		this.annonceRepo = annonceRepo;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void onStart() {
		this.versionRepo.save(new Version(appVersion));

		// Création de trois utilisateurs

		Collegue col1 = new Collegue();
		col1.setNom("Admin");
		col1.setPrenom("DEV");
		col1.setEmail("admin@dev.fr");
		col1.setMotDePasse(passwordEncoder.encode("superpass"));
		col1.setRoles(Arrays.asList(new RoleCollegue(col1, Role.ROLE_ADMINISTRATEUR),
				new RoleCollegue(col1, Role.ROLE_CHAUFFEUR), new RoleCollegue(col1, Role.ROLE_COLLABORATEUR)));
		this.collegueRepo.save(col1);

		Collegue col2 = new Collegue();
		col2.setNom("Collaborateur");
		col2.setPrenom("DEV");
		col2.setEmail("collaborateur@dev.fr");
		col2.setMotDePasse(passwordEncoder.encode("superpass"));
		col2.setRoles(Arrays.asList(new RoleCollegue(col2, Role.ROLE_COLLABORATEUR)));
		this.collegueRepo.save(col2);

		Collegue col3 = new Collegue();
		col3.setNom("Chauffeur");
		col3.setPrenom("DEV");
		col3.setEmail("chauffeur@dev.fr");
		col3.setMotDePasse(passwordEncoder.encode("superpass"));
		col3.setRoles(Arrays.asList(new RoleCollegue(col3, Role.ROLE_COLLABORATEUR),
				new RoleCollegue(col3, Role.ROLE_CHAUFFEUR)));
		this.collegueRepo.save(col3);

		// itineraire
		Itineraire ite1 = new Itineraire();
		LocalDateTime dt = LocalDateTime.now();
		ite1.setDateArrivee(dt);
		ite1.setDateDepart(dt);
		ite1.setLieuDepart("maison");
		ite1.setLieuDestination("boulo");
		ite1.setDureeTrajet(200);
		ite1.setDistance(202D);

		Itineraire ite2 = new Itineraire();
		LocalDateTime dt1 = LocalDateTime.of(1992, 05, 30, 00, 00);
		ite2.setDateArrivee(dt1);
		ite2.setDateDepart(dt1);
		ite2.setLieuDepart("Cul de sac");
		ite2.setLieuDestination("La montagne solitaire");
		ite2.setDureeTrajet(200);
		ite2.setDistance(202D);

		// Annonce
		Annonce annonce1 = new Annonce();
		annonce1.setItineraire(ite2);
		annonce1.setResponsable(col2);
		annonce1.setListPassagers(new ArrayList());
		annonce1.setImmatriculation("JD-666-JD");
		annonce1.setMarque("Dolorean1");
		annonce1.setModele("DMC-12");
		annonce1.setNbPlace(2);
		List<Collegue> listPassagers = Arrays.asList(col1, col2, col3);
		annonce1.setListPassagers(listPassagers);
		this.annonceRepo.save(annonce1);

		Annonce annonce2 = new Annonce();
		annonce2.setItineraire(ite1);
		annonce2.setResponsable(col2);
		annonce2.setListPassagers(new ArrayList());
		annonce2.setImmatriculation("JD-666-JD");
		annonce2.setMarque("Dolorean2");
		annonce2.setModele("DMC-12");
		annonce2.setNbPlace(2);
		this.annonceRepo.save(annonce2);

		Annonce annonce3 = new Annonce();
		annonce3.setItineraire(ite1);
		annonce3.setResponsable(col2);
		annonce3.setListPassagers(new ArrayList());
		annonce3.setImmatriculation("JD-666-JD");
		annonce3.setMarque("Dolorean3");
		annonce3.setModele("DMC-12");
		annonce3.setNbPlace(2);
		this.annonceRepo.save(annonce3);

		Annonce annonce4 = new Annonce();
		annonce4.setItineraire(ite1);
		annonce4.setResponsable(col2);
		annonce4.setListPassagers(new ArrayList());
		annonce4.setImmatriculation("JD-666-JD");
		annonce4.setMarque("Dolorean4");
		annonce4.setModele("DMC-12");
		annonce4.setNbPlace(2);
		this.annonceRepo.save(annonce4);

		Annonce annonce5 = new Annonce();
		annonce5.setItineraire(ite1);
		annonce5.setResponsable(col2);
		annonce5.setListPassagers(new ArrayList());
		annonce5.setImmatriculation("JD-666-JD");
		annonce5.setMarque("Dolorean5");
		annonce5.setModele("DMC-12");
		annonce5.setNbPlace(2);
		this.annonceRepo.save(annonce5);

		Annonce annonce6 = new Annonce();
		annonce6.setItineraire(ite1);
		annonce6.setResponsable(col1);
		annonce6.setListPassagers(new ArrayList());
		annonce6.setImmatriculation("JD-666-JD");
		annonce6.setMarque("Bus Magique");
		annonce6.setModele("Nw");
		annonce6.setNbPlace(2);
		this.annonceRepo.save(annonce6);

		Annonce annonce7 = new Annonce();
		annonce7.setItineraire(ite1);
		annonce7.setResponsable(col1);
		annonce7.setListPassagers(new ArrayList());
		annonce7.setImmatriculation("JD-666-JD");
		annonce7.setMarque("Dragon");
		annonce7.setModele("magnar a pointe");
		annonce7.setNbPlace(2);
		this.annonceRepo.save(annonce7);

		Annonce annonce8 = new Annonce();
		annonce8.setItineraire(new Itineraire(LocalDateTime.of(1966, 05, 30, 00, 00),
				LocalDateTime.of(1967, 05, 30, 00, 00), "mordor", "gondor", 1000, 10000D));
		annonce8.setResponsable(col2);
		annonce8.setImmatriculation("JD-666-JD");
		annonce8.setMarque("Oliphant");
		annonce8.setModele("GreySkin");
		annonce8.setNbPlace(2);
		List<Collegue> listPassagersSansCollab = Arrays.asList(col1, col3);
		annonce8.setListPassagers(listPassagersSansCollab);
		this.annonceRepo.save(annonce8);

		Annonce annonce9 = new Annonce();
		annonce8.setItineraire(new Itineraire(LocalDateTime.of(2021, 05, 30, 00, 00),
				LocalDateTime.of(2021, 05, 31, 00, 00), "tatouin", "Mustafar", 1000, 10000D));
		annonce8.setResponsable(col2);
		annonce8.setImmatriculation("JD-666-JD");
		annonce8.setMarque("faucon millenium");
		annonce8.setModele("1100KK");
		annonce8.setNbPlace(2);
		List<Collegue> listPassagersfutur = Arrays.asList(col1,col2,col3);
		annonce8.setListPassagers(listPassagersSansCollab);
		this.annonceRepo.save(annonce8);
	}
}
