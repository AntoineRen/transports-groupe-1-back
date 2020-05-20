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
import dev.entites.utiles.StatutAnnonce;
import dev.entites.utiles.StatutDemandeChauffeur;
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
		col1.setNumTelephone("06 62 85 45 36");
		col1.setEmail("admin@dev.fr");
		col1.setMotDePasse(passwordEncoder.encode("superpass"));
		col1.setRoles(Arrays.asList(new RoleCollegue(col1, Role.ROLE_ADMINISTRATEUR),
				new RoleCollegue(col1, Role.ROLE_CHAUFFEUR), new RoleCollegue(col1, Role.ROLE_COLLABORATEUR)));
		col1.setPhotoUrl("https://data.whicdn.com/images/284586665/original.jpg");
		col1.setPermis("B");
		col1.setMatricule("M01");
		this.collegueRepo.save(col1);

		Collegue col2 = new Collegue();
		col2.setNom("Collaborateur");
		col2.setPrenom("DEV");
		col2.setNumTelephone("06 85 36 95 47");
		col2.setEmail("collaborateur@dev.fr");
		col2.setMotDePasse(passwordEncoder.encode("superpass"));
		col2.setRoles(Arrays.asList(new RoleCollegue(col2, Role.ROLE_COLLABORATEUR)));
		col2.setPhotoUrl(
				"https://www.portail-autoentrepreneur.fr/media/knowledgebase/chauffeur_VTC_auto_entrepreneur.jpg");
		col2.setPermis("B");
		col2.setMatricule("M02");
		this.collegueRepo.save(col2);

		Collegue col3 = new Collegue();
		col3.setNom("Chauffeur");
		col3.setPrenom("DEV");
		col3.setEmail("chauffeur@dev.fr");
		col3.setNumTelephone("06 85 82 45 47");
		col3.setMotDePasse(passwordEncoder.encode("superpass"));
		col3.setRoles(Arrays.asList(new RoleCollegue(col3, Role.ROLE_COLLABORATEUR),
				new RoleCollegue(col3, Role.ROLE_CHAUFFEUR)));
		col3.setPhotoUrl("https://static.actu.fr/uploads/2019/06/25130-190605155011064-0.jpg");
		col3.setPermis("B");
		col3.setMatricule("M03");
		this.collegueRepo.save(col3);

		// collegue jules
		Collegue col4 = new Collegue();
		col4.setNom("Dupuis");
		col4.setPrenom("Jules");
		col4.setEmail("jules.dupuis51@gmail.com");
		col4.setNumTelephone("06 63 10 45 22");
		col4.setMotDePasse(passwordEncoder.encode("braacken"));
		col4.setRoles(Arrays.asList(new RoleCollegue(col4, Role.ROLE_COLLABORATEUR),
				new RoleCollegue(col4, Role.ROLE_CHAUFFEUR)));
		col4.setPermis("B");
		col4.setMatricule("M04");
		col4.setPhotoUrl("https://www.dahaboo.com/upload/i/2019-09/chauffeur-experimente-94011.jpg");
		this.collegueRepo.save(col4);

		// AdminGDT-Transport
		Collegue col5 = new Collegue();
		col5.setNom("GDT-Transport");
		col5.setPrenom("DEV");
		col5.setEmail("gdttransportentreprise@gmail.com");
		// mdp mail : GDTTransport_44
		col5.setMotDePasse(passwordEncoder.encode("superpass"));
		col5.setRoles(Arrays.asList(new RoleCollegue(col5, Role.ROLE_ADMINISTRATEUR),
				new RoleCollegue(col5, Role.ROLE_CHAUFFEUR), new RoleCollegue(col5, Role.ROLE_COLLABORATEUR)));
		col5.setPermis("B");
		col5.setMatricule("M05");
		col5.setPhotoUrl("https://www.chauffeur-prive-jadriver.fr/images/stories/chauffeur-vtc-Yvelines.jpg");
		this.collegueRepo.save(col5);

		// collegue tatiana
		Collegue col6 = new Collegue();
		col6.setNom("Dupuis");
		col6.setPrenom("Tatiana");
		col6.setEmail("dupuis.tatiana@dev.fr");
		col6.setNumTelephone("06 45 78 45 75");
		col6.setMotDePasse(passwordEncoder.encode("superpass"));
		col6.setRoles(Arrays.asList(new RoleCollegue(col6, Role.ROLE_COLLABORATEUR),
				new RoleCollegue(col6, Role.ROLE_CHAUFFEUR)));
		col6.setPermis("B");
		col6.setMatricule("M06");
		col6.setPhotoUrl(
				"https://i1.wp.com/icurious.net/wp-content/uploads/2019/03/Teenage-driver-girl-smile-front-cover-blue-2-1.jpg?fit=1461%2C1052&ssl=1");
		this.collegueRepo.save(col6);

		// collegue Eustach
		Collegue col7 = new Collegue();
		col7.setNom("Jenkins");
		col7.setPrenom("Eustach");
		col7.setEmail("eustach.jenkins@dev.fr");
		col7.setNumTelephone("07 78 86 96 86");
		col7.setMotDePasse(passwordEncoder.encode("superpass"));
		col7.setRoles(Arrays.asList(new RoleCollegue(col7, Role.ROLE_COLLABORATEUR),
				new RoleCollegue(col7, Role.ROLE_CHAUFFEUR)));
		col7.setPermis("B");
		col7.setMatricule("M07");
		col7.setPhotoUrl(
				"https://images.unsplash.com/photo-1513316653289-f684caad4aeb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80");
		this.collegueRepo.save(col7);

		// Création de véhicules

		VehiculeSociete audi = new VehiculeSociete("AR-785-99", "Audi", "A4", Categorie.CATEGORIE_BTM, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://images.unsplash.com/photo-1587142648169-bb6565d88f12?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1349&q=80",
				44.304368, 5.856789);
		this.vehiculeRepo.save(audi);

		VehiculeSociete bmwS3 = new VehiculeSociete("DH-832-85", "BMW", "Série 3 G20", Categorie.CATEGORIE_BTM, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://mag.starterre.fr/wp-content/uploads/2016/06/P90220385-highRes.jpg", 43.253368, 5.753269);
		this.vehiculeRepo.save(bmwS3);

		VehiculeSociete bmwS5 = new VehiculeSociete("RN-647-PE", "BMW", "Série 5 G30", Categorie.CATEGORIE_BTL, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://www.usinenouvelle.com/mediatheque/2/8/0/000483082/bmw-serie-5.jpg", 43.386568, 5.935479);
		this.vehiculeRepo.save(bmwS5);

		VehiculeSociete bmwS7 = new VehiculeSociete("FG-587-DZ", "BMW", "Série 7 G11", Categorie.CATEGORIE_BTM, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://images.caradisiac.com/logos-ref/modele/modele--bmw-serie-7-g11/S0-modele--bmw-serie-7-g11.jpg",
				44.785368, 6.345245);
		this.vehiculeRepo.save(bmwS7);

		VehiculeSociete jaguarXF = new VehiculeSociete("VH-743-PM", "Jaguar", "XF", Categorie.CATEGORIE_BTL, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://cdn.carizy.com/carphotos/5112/wide/jaguar-xf-occasion-2012-avant-gauche.jpg", 45.3044568,
				7.3944525);
		this.vehiculeRepo.save(jaguarXF);

		VehiculeSociete miniCooper = new VehiculeSociete("SD-948-HJ", "Mini Cooper S", "R53", Categorie.CATEGORIE_MP, 4,
				StatutVehiculeSociete.EN_SERVICE,
				"https://i.pinimg.com/originals/73/cd/23/73cd23938ef605fe288398d1b8774c9a.jpg", 45.345628, 6.37859);
		this.vehiculeRepo.save(miniCooper);

		VehiculeSociete fiat500 = new VehiculeSociete("RT-125-GY", "Fiat", "500 Abarth", Categorie.CATEGORIE_MU, 4,
				StatutVehiculeSociete.EN_SERVICE,
				"https://upload.wikimedia.org/wikipedia/commons/e/e4/Fiat_500_Abarth_front.JPG", 43.1254668, 6.8654269);
		this.vehiculeRepo.save(fiat500);

		VehiculeSociete combi = new VehiculeSociete("SD-948-HJ", "Volkswagen", "Combi T1", Categorie.CATEGORIE_SUV, 7,
				StatutVehiculeSociete.EN_SERVICE,
				"https://www.fourgonlesite.com/wp-content/uploads/2018/03/VW-Samba-Bus-02.jpg", 48.38456978, 5.3945529);
		this.vehiculeRepo.save(combi);

		VehiculeSociete toyota = new VehiculeSociete("EC-824-KJ", "Toyota", "CHR", Categorie.CATEGORIE_SUV, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://images.caradisiac.com/logos-ref/modele/modele--toyota-c-hr/S0-modele--toyota-c-hr.jpg",
				48.35586568, 5.39785569);
		this.vehiculeRepo.save(toyota);

		VehiculeSociete audiQ7 = new VehiculeSociete("BN-653-QS", "Audi", "Q7", Categorie.CATEGORIE_SUV, 7,
				StatutVehiculeSociete.EN_SERVICE,
				"https://www.audi.fr/content/dam/nemo/models/q7/q7/my-2020/1920x1080-feature-gallery/1920x1080_exterior_AQ7_191010.jpg",
				46.37854854368, 9.39478825);
		this.vehiculeRepo.save(audiQ7);

		VehiculeSociete audiQ5 = new VehiculeSociete("KL-462-WD", "Audi", "Q5", Categorie.CATEGORIE_SUV, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://images.caradisiac.com/images/4/1/0/2/164102/S0-l-audi-q5-debarque-en-seconde-main-le-seigneur-des-anneaux-536313.jpg",
				43.3857287368, 5.3942788669);
		this.vehiculeRepo.save(audiQ5);

		VehiculeSociete renault = new VehiculeSociete("RF-345-GH", "Renault", "Talisman", Categorie.CATEGORIE_BTM, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://cdn.carizy.com/carphotos/8876/wide/renault-talisman-occasion-2016-avant-droit.jpg", 47.8542368,
				8.3956669);
		this.vehiculeRepo.save(renault);

		VehiculeSociete mercedes = new VehiculeSociete("FR-852-SD", "Mercedes", "G63", Categorie.CATEGORIE_SUV, 5,
				StatutVehiculeSociete.EN_SERVICE, "https://i.ytimg.com/vi/MTcgigH-xsI/maxresdefault.jpg", 47.30465786,
				4.39587669);
		this.vehiculeRepo.save(mercedes);

		VehiculeSociete dacia = new VehiculeSociete("GB-452-XC", "Dacia", "Sandero", Categorie.CATEGORIE_CP, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://upload.wikimedia.org/wikipedia/commons/6/6b/Dacia_Sandero_TCe_90_eco%C2%B2_Laur%C3%A9ate_%28II%29_%E2%80%93_Frontansicht%2C_21._April_2013%2C_M%C3%BCnster.jpg",
				48.20878668, -1.39457869);
		this.vehiculeRepo.save(dacia);

		VehiculeSociete renaultClio = new VehiculeSociete("DF-436-VG", "Renault", "Clio", Categorie.CATEGORIE_CP, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://images.caradisiac.com/images/9/6/8/5/179685/S0-essai-renault-clio-dci-85-611005.jpg",
				46.304867688, 2.3948769);
		this.vehiculeRepo.save(renaultClio);

		VehiculeSociete toyotaAygo = new VehiculeSociete("TH-698-UJ", "Toyota", "Aygo", Categorie.CATEGORIES_C, 4,
				StatutVehiculeSociete.EN_SERVICE,
				"https://aws-cf.caradisiac.com/prod/mesimages/659888/cote%20droit.jpg", 47.875857368, 4.87587269);
		this.vehiculeRepo.save(toyotaAygo);

		VehiculeSociete toyotaPrius = new VehiculeSociete("ZE-231-JH", "Toyota", "Prius", Categorie.CATEGORIE_BTM, 5,
				StatutVehiculeSociete.EN_SERVICE,
				"https://img.autoplus.fr/picture/toyota/prius/1496923/Toyota_Prius_2015_83edb-1600-1108.jpg?r",
				43.3058758768, 7.3978269);
		this.vehiculeRepo.save(toyotaPrius);

		VehiculeSociete multipla = new VehiculeSociete("YT-852-SQ", "Fiat", "Multipla", Categorie.CATEGORIE_CP, 6,
				StatutVehiculeSociete.EN_SERVICE,
				"https://fs.opisto.fr/Pictures/4285/2019_2/Vehicule-FIAT-MULTIPLA-PHASE-1-1-6-1999-d2827141fc111823b1dac6dae27f58e1abfa4de384cfef1f7c2c6523aab51752.jpg",
				43.96756786, 6.3942678579);
		this.vehiculeRepo.save(multipla);

		VehiculeSociete sansImage = new VehiculeSociete("EF-423-HF", "Ford", "Mustang", Categorie.CATEGORIE_BTL, 6,
				StatutVehiculeSociete.EN_SERVICE, "", 42.30577858, -1.397854269);
		this.vehiculeRepo.save(sansImage);

		VehiculeSociete vehi1 = new VehiculeSociete("AA-000-AA", "Dolorean", "DMC-12", Categorie.CATEGORIE_CP, 2,
				StatutVehiculeSociete.EN_SERVICE,
				"https://images.unsplash.com/photo-1515853191710-4db39aa5fe54?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1376&q=80",
				45.135785, -1.679587583);
		this.vehiculeRepo.save(vehi1);

		VehiculeSociete vehi2 = new VehiculeSociete("BB-000-BB", "Le Bus magique", "Confinement version",
				Categorie.CATEGORIE_SUV, 20, StatutVehiculeSociete.EN_SERVICE,
				"https://images.unsplash.com/photo-1527058918112-6e17a8213943?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80");
		this.vehiculeRepo.save(vehi2);

		VehiculeSociete vehi3 = new VehiculeSociete("CC-000-CC", "Platypus", "Vroum Vroum", Categorie.CATEGORIE_BTL, 5,
				StatutVehiculeSociete.EN_REPARATION,
				"https://images.unsplash.com/photo-1579571274591-51ace815723a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1367&q=80",
				43.304368, 5.394269);
		this.vehiculeRepo.save(vehi3);

		// Reservation Test Planning
		Reservation resSansChauffeur = new Reservation(LocalDateTime.now().withHour(14),
				LocalDateTime.now().withHour(16), col1, null, audiQ5, StatutDemandeChauffeur.EN_ATTENTE);
		this.reservationRepo.save(resSansChauffeur);
		Reservation resAvecChauffeur = new Reservation(LocalDateTime.now().withHour(8),
				LocalDateTime.now().withHour(10), col1, col3, combi, StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(resAvecChauffeur);

		Reservation resSansChauffeurDemain1 = new Reservation(LocalDateTime.now().plusDays(1).withHour(14),
				LocalDateTime.now().plusDays(1).withHour(16), col1, null, renault, StatutDemandeChauffeur.EN_ATTENTE);
		this.reservationRepo.save(resSansChauffeurDemain1);
		Reservation resSansChauffeurDemain2 = new Reservation(LocalDateTime.now().plusDays(1).withHour(14),
				LocalDateTime.now().plusDays(1).withHour(16), col2, null, jaguarXF, StatutDemandeChauffeur.EN_ATTENTE);
		this.reservationRepo.save(resSansChauffeurDemain2);

		Reservation demainMatin = new Reservation(LocalDateTime.now().plusDays(1).withHour(8),
				LocalDateTime.now().plusDays(1).withHour(12), col2, col3, toyotaAygo,
				StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(demainMatin);

		Reservation apresDemain2Jours = new Reservation(LocalDateTime.now().plusDays(2).withHour(8),
				LocalDateTime.now().plusDays(4).withHour(15), col2, col3, audi, StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(apresDemain2Jours);

		Reservation hierMatin = new Reservation(LocalDateTime.now().minusDays(1).withHour(8),
				LocalDateTime.now().minusDays(1).withHour(10), col2, col3, audi, StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(hierMatin);

		Reservation hierAprem = new Reservation(LocalDateTime.now().minusDays(1).withHour(14),
				LocalDateTime.now().minusDays(1).withHour(18), col2, col3, mercedes,
				StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(hierAprem);

		Reservation double1 = new Reservation(LocalDateTime.now().withHour(2), LocalDateTime.now().withHour(6), col2,
				null, jaguarXF, StatutDemandeChauffeur.EN_ATTENTE);
		this.reservationRepo.save(double1);

		Reservation double2 = new Reservation(LocalDateTime.now().withHour(2), LocalDateTime.now().withHour(6), col2,
				null, miniCooper, StatutDemandeChauffeur.EN_ATTENTE);
		this.reservationRepo.save(double2);

		Reservation plusTard = new Reservation(LocalDateTime.now().plusDays(6).withHour(2),
				LocalDateTime.now().plusDays(8).withHour(6), col2, col3, miniCooper,
				StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(plusTard);

		Reservation avant = new Reservation(LocalDateTime.now().minusDays(8).withHour(2),
				LocalDateTime.now().minusDays(6).withHour(6), col2, col3, miniCooper,
				StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(avant);

		// Création de 10 réservations pour admin
		Reservation res1 = new Reservation(LocalDateTime.of(2020, 5, 15, 13, 30), LocalDateTime.of(2020, 5, 25, 16, 50),
				col1, null, audiQ5, StatutDemandeChauffeur.EN_ATTENTE);

		this.reservationRepo.save(res1);

		Reservation res2 = new Reservation(LocalDateTime.of(2020, 5, 8, 16, 50), LocalDateTime.of(2020, 5, 28, 15, 30),
				col1, null, audiQ7, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res2);

		Reservation res3 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, mercedes, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res3);

		Reservation res4 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, miniCooper, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res4);

		Reservation res5 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, col3, multipla, StatutDemandeChauffeur.AVEC_CHAUFFEUR);
		this.reservationRepo.save(res5);

		Reservation res6 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, renault, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res6);

		Reservation res7 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, renaultClio, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res7);

		Reservation res8 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, fiat500, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res8);

		Reservation res9 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, jaguarXF, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res9);

		Reservation res10 = new Reservation(LocalDateTime.of(2020, 4, 8, 16, 50), LocalDateTime.of(2020, 4, 12, 15, 30),
				col1, null, toyotaPrius, StatutDemandeChauffeur.SANS_CHAUFFEUR);
		this.reservationRepo.save(res10);

		// itineraire
		Itineraire ite1 = new Itineraire();
		LocalDateTime dt = LocalDateTime.now();
		ite1.setDateArrivee(dt);
		ite1.setDateDepart(dt);
		ite1.setLieuDepart("Rue Monge 35000 Rennes");
		ite1.setLieuDestination("Rue de la Chamoiserie 79000 Niort");
		ite1.setDureeTrajet(200);
		ite1.setDistance(202D);

		Itineraire ite2 = new Itineraire();
		LocalDateTime dt1 = LocalDateTime.of(1992, 05, 30, 00, 00);
		ite2.setDateArrivee(dt1);
		ite2.setDateDepart(dt1);
		ite2.setLieuDepart("Rue Molac 44000 Nantes");
		ite2.setLieuDestination("Rue de Nice 31400 Toulouse");
		ite2.setDureeTrajet(200);
		ite2.setDistance(202D);

		Itineraire ite3 = new Itineraire();
		LocalDateTime dt2 = LocalDateTime.of(2022, 05, 30, 00, 00);
		ite3.setDateArrivee(dt2);
		ite3.setDateDepart(dt2);
		ite3.setLieuDepart("Route de Toulouse 33800 Bordeaux");
		ite3.setLieuDestination("Rue Monge 35000 Rennes");
		ite3.setDureeTrajet(200);
		ite3.setDistance(202D);

		// Annonce
		Annonce annonce1 = new Annonce();
		annonce1.setItineraire(ite2);
		annonce1.setResponsable(col2);
		annonce1.setListPassagers(new ArrayList());
		annonce1.setImmatriculation("KL-452-PS");
		annonce1.setMarque("Toyota");
		annonce1.setModele("Prius");
		annonce1.setNbPlace(2);
		List<Collegue> listPassagers = Arrays.asList(col1, col2, col3);
		annonce1.setListPassagers(listPassagers);
		this.annonceRepo.save(annonce1);

		Annonce annonce2 = new Annonce();
		annonce2.setItineraire(ite1);
		annonce2.setResponsable(col2);
		annonce2.setListPassagers(new ArrayList());
		annonce2.setImmatriculation("SQ-563-TY");
		annonce2.setMarque("Toyota");
		annonce2.setModele("CHR");
		annonce2.setNbPlace(2);
		this.annonceRepo.save(annonce2);

		Annonce annonce3 = new Annonce();
		annonce3.setItineraire(ite1);
		annonce3.setResponsable(col2);
		annonce3.setListPassagers(new ArrayList());
		annonce3.setImmatriculation("DF-156-GB");
		annonce3.setMarque("Renault");
		annonce3.setModele("Talisman");
		annonce3.setNbPlace(2);
		this.annonceRepo.save(annonce3);

		Annonce annonce4 = new Annonce();
		annonce4.setItineraire(ite1);
		annonce4.setResponsable(col2);
		annonce4.setListPassagers(new ArrayList());
		annonce4.setImmatriculation("CG-896-ED");
		annonce4.setMarque("Fiat");
		annonce4.setModele("Multipla");
		annonce4.setNbPlace(2);
		this.annonceRepo.save(annonce4);

		Annonce annonce5 = new Annonce();
		annonce5.setItineraire(ite1);
		annonce5.setResponsable(col2);
		annonce5.setListPassagers(new ArrayList());
		annonce5.setImmatriculation("FV-256-RT");
		annonce5.setMarque("Dolorean");
		annonce5.setModele("DMC-12");
		annonce5.setNbPlace(2);
		this.annonceRepo.save(annonce5);

		Annonce annonce6 = new Annonce();
		annonce6.setItineraire(ite1);
		annonce6.setResponsable(col1);
		annonce6.setListPassagers(new ArrayList());
		annonce6.setImmatriculation("TP-645-CF");
		annonce6.setMarque("Dacia");
		annonce6.setModele("Sandero");
		annonce6.setNbPlace(2);
		this.annonceRepo.save(annonce6);

		Annonce annonce7 = new Annonce();
		annonce7.setItineraire(ite1);
		annonce7.setResponsable(col1);
		annonce7.setListPassagers(new ArrayList());
		annonce7.setImmatriculation("SD-452-ER");
		annonce7.setMarque("Fiat");
		annonce7.setModele("500");
		annonce7.setNbPlace(2);
		this.annonceRepo.save(annonce7);

		Annonce annonce8 = new Annonce();
		annonce8.setItineraire(
				new Itineraire(LocalDateTime.of(1966, 05, 30, 00, 00), LocalDateTime.of(1967, 05, 30, 00, 00),
						"Allée Vincent d'Indy 89000 Auxerre", "Route de Toulouse 33800 Bordeaux", 1000, 10000D));
		annonce8.setResponsable(col2);
		annonce8.setImmatriculation("ZD-852-AZ");
		annonce8.setMarque("Renault");
		annonce8.setModele("RX");
		annonce8.setNbPlace(2);
		List<Collegue> listPassagersSansCollab = Arrays.asList(col1, col3);
		annonce8.setListPassagers(listPassagersSansCollab);
		this.annonceRepo.save(annonce8);

		Annonce annonce9 = new Annonce();
		annonce9.setItineraire(
				new Itineraire(LocalDateTime.of(2021, 05, 30, 00, 00), LocalDateTime.of(2021, 05, 31, 00, 00),
						"Rue de Nice 31400 Toulouse", "Rue Saint-michel 35000 Rennes", 1000, 10000D));
		annonce9.setResponsable(col2);
		annonce9.setImmatriculation("JD-785-SD");
		annonce9.setMarque("Renault");
		annonce9.setModele("Captur");
		annonce9.setNbPlace(2);
		annonce9.setStatut(StatutAnnonce.STATUT_EN_COURS);

		List<Collegue> listPassagersfutur = Arrays.asList(col1, col2, col3);
		annonce9.setListPassagers(listPassagersfutur);
		this.annonceRepo.save(annonce9);

		// Annonce
		Annonce annonce10 = new Annonce();
		annonce10.setItineraire(ite2);
		annonce10.setResponsable(col1);
		annonce10.setListPassagers(new ArrayList());
		annonce10.setImmatriculation("JD-452-FG");
		annonce10.setMarque("Audi");
		annonce10.setModele("A4");
		List<Collegue> listPassagers1 = Arrays.asList(col1, col2, col3);
		annonce10.setListPassagers(listPassagers1);
		annonce10.setNbPlace(2);
		this.annonceRepo.save(annonce10);

		Annonce annonce11 = new Annonce();
		annonce11.setItineraire(ite2);
		annonce11.setResponsable(col1);
		annonce11.setListPassagers(new ArrayList());
		annonce11.setImmatriculation("AZ-785-FR");
		annonce11.setMarque("Ford");
		annonce11.setModele("Mustang");
		List<Collegue> listPassagers2 = Arrays.asList(col1, col2, col3);
		annonce11.setListPassagers(listPassagers2);
		annonce11.setNbPlace(2);
		this.annonceRepo.save(annonce11);

		Annonce annonce12 = new Annonce();
		annonce12.setItineraire(ite3);
		annonce12.setResponsable(col1);
		annonce12.setListPassagers(new ArrayList());
		annonce12.setImmatriculation("ER-165-FT");
		annonce12.setMarque("Audi");
		annonce12.setModele("Q7");
		List<Collegue> listPassagers3 = Arrays.asList(col1, col2, col3);
		annonce12.setListPassagers(listPassagers3);
		annonce12.setNbPlace(2);
		this.annonceRepo.save(annonce12);

	}
}
