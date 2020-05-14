package dev;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.RoleCollegue;
import dev.entites.VehiculeSociete;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.Role;
import dev.entites.utiles.StatutDemandeChauffeur;
import dev.entites.utiles.StatutReservation;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.entites.utiles.Version;
import dev.repository.AnnonceRepository;
import dev.repository.CollegueRepo;
import dev.repository.ReservationRepository;
import dev.repository.VehiculeSocieteRepository;
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
	private VehiculeSocieteRepository vehiculeRepo;
	private ReservationRepository reservationRepo;

	public StartupListener(@Value("${app.version}") String appVersion, VersionRepo versionRepo,
			PasswordEncoder passwordEncoder, CollegueRepo collegueRepo, VehiculeSocieteRepository vehiculeRepo,
			ReservationRepository reservationRepo, AnnonceRepository annonceRepo) {

		this.appVersion = appVersion;
		this.versionRepo = versionRepo;
		this.passwordEncoder = passwordEncoder;
		this.collegueRepo = collegueRepo;

		this.annonceRepo = annonceRepo;

		this.vehiculeRepo = vehiculeRepo;
		this.reservationRepo = reservationRepo;

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

		// Création de 3 véhicule
		VehiculeSociete vehi1 = new VehiculeSociete("AA-000-AA", "Dolorean", "DMC-12", Categorie.CATEGORIE_CP, 2,
				StatutVehiculeSociete.EN_SERVICE,
				"https://images.unsplash.com/photo-1515853191710-4db39aa5fe54?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1376&q=80");
		this.vehiculeRepo.save(vehi1);

		VehiculeSociete vehi2 = new VehiculeSociete("BB-000-BB", "Le Bus magique", "Confinement version",
				Categorie.CATEGORIE_SUV, 20, StatutVehiculeSociete.EN_SERVICE,
				"https://images.unsplash.com/photo-1527058918112-6e17a8213943?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80");
		this.vehiculeRepo.save(vehi2);

		VehiculeSociete vehi3 = new VehiculeSociete("CC-000-CC", "Platypus", "Vroum Vroum", Categorie.CATEGORIE_BTL, 5,
				StatutVehiculeSociete.EN_REPARATION,
				"https://images.unsplash.com/photo-1579571274591-51ace815723a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1367&q=80");
		this.vehiculeRepo.save(vehi3);

		// Reservation Test Planning
		Reservation resSansChauffeur = new Reservation(LocalDateTime.now().withHour(14),
				LocalDateTime.now().withHour(16), col1, null, StatutReservation.STATUT_EN_COURS, vehi1,
				StatutDemandeChauffeur.EN_ATTENTE);
		this.reservationRepo.save(resSansChauffeur);
		Reservation resAvecChauffeur = new Reservation(LocalDateTime.now().withHour(14),
				LocalDateTime.now().withHour(16), col1, col3, StatutReservation.STATUT_EN_COURS, vehi2,
				StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(resAvecChauffeur);

		// Création de 10 réservations pour admin
		Reservation res1 = new Reservation(LocalDateTime.of(2020, 5, 15, 13, 30), LocalDateTime.of(2020, 5, 25, 16, 50),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi1, StatutDemandeChauffeur.EN_ATTENTE);
		this.reservationRepo.save(res1);

		Reservation res2 = new Reservation(LocalDateTime.of(2020, 5, 8, 16, 50), LocalDateTime.of(2020, 5, 28, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi2, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res2);

		Reservation res3 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi2, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res3);

		Reservation res4 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi3, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res4);

		Reservation res5 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, col3, StatutReservation.STATUT_EN_COURS, vehi3, StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(res5);

		Reservation res6 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi2, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res6);

		Reservation res7 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi1, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res7);

		Reservation res8 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi3, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res8);

		Reservation res9 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi1, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res9);

		Reservation res10 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, StatutReservation.STATUT_EN_COURS, vehi2, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res10);

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
		List<Collegue> listPassagersfutur = Arrays.asList(col1, col2, col3);
		annonce8.setListPassagers(listPassagersSansCollab);
		this.annonceRepo.save(annonce8);
	}
}
