package dev;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.RoleCollegue;
import dev.entites.VehiculeSociete;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.Role;
import dev.entites.utiles.StatutReservation;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.entites.utiles.Version;
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
	private VehiculeSocieteRepository vehiculeRepo;
	private ReservationRepository reservationRepo;

	public StartupListener(@Value("${app.version}") String appVersion, VersionRepo versionRepo,
			PasswordEncoder passwordEncoder, CollegueRepo collegueRepo, VehiculeSocieteRepository vehiculeRepo,
			ReservationRepository reservationRepo) {
		this.appVersion = appVersion;
		this.versionRepo = versionRepo;
		this.passwordEncoder = passwordEncoder;
		this.collegueRepo = collegueRepo;
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
				StatutVehiculeSociete.EN_SERVICE);
		this.vehiculeRepo.save(vehi1);

		VehiculeSociete vehi2 = new VehiculeSociete("BB-000-BB", "Le Bus magique", "Confinement version",
				Categorie.CATEGORIE_SUV, 20, StatutVehiculeSociete.EN_SERVICE);
		this.vehiculeRepo.save(vehi2);

		VehiculeSociete vehi3 = new VehiculeSociete("CC-000-CC", "Platypus", "Vroum Vroum", Categorie.CATEGORIE_BTL, 5,
				StatutVehiculeSociete.EN_SERVICE);
		this.vehiculeRepo.save(vehi3);

		// Création de 10 réservations pour admin
		Reservation res1 = new Reservation(new Itineraire(LocalDateTime.of(2020, 5, 4, 13, 30),
				LocalDateTime.of(2020, 5, 8, 16, 50), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi1);
		this.reservationRepo.save(res1);

		Reservation res2 = new Reservation(new Itineraire(LocalDateTime.of(2020, 5, 8, 16, 50),
				LocalDateTime.of(2020, 5, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi2);
		this.reservationRepo.save(res2);

		Reservation res3 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi2);
		this.reservationRepo.save(res3);

		Reservation res4 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi3);
		this.reservationRepo.save(res4);

		Reservation res5 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi3);
		this.reservationRepo.save(res5);

		Reservation res6 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi2);
		this.reservationRepo.save(res6);

		Reservation res7 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi1);
		this.reservationRepo.save(res7);

		Reservation res8 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi3);
		this.reservationRepo.save(res8);

		Reservation res9 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi1);
		this.reservationRepo.save(res9);

		Reservation res10 = new Reservation(new Itineraire(LocalDateTime.of(2020, 4, 8, 16, 50),
				LocalDateTime.of(2020, 4, 12, 15, 30), "Rennes", "Nantes", 200, 120.0), col1, null,
				StatutReservation.STATUT_EN_COURS, vehi2);
		this.reservationRepo.save(res10);
	}

}
