package dev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.utiles.StatutAnnonce;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.AnnonceRepository;
import dev.repository.CollegueRepository;

@SpringJUnitConfig(AnnonceService.class)
class AnnonceServiceTest {

	// Mock
	@Autowired
	AnnonceService annonceService;

	@MockBean
	AnnonceRepository annonceRepository;

	@MockBean
	CollegueRepository collegueRepository;

	// Instqnce pour Mock Jeu de Données
	String emailResponsableTest = "testResponsable@test.fr";
	String emailPassagerTest = "testPassager@test.fr";
	Annonce annonceTest;
	Collegue responcableTest;
	Collegue passagerTest;
	List<Annonce> listAnnonces = new ArrayList<>();
	List<Annonce> listAnnoncesPassager = new ArrayList<>();
	List<Annonce> listAnnoncesTime = new ArrayList<>();
	Annonce annoncePast = new Annonce();
	Annonce annonceFuture = new Annonce();
	Annonce annoncePassagerTest = new Annonce();

	@BeforeEach
	public void init() {

		// Valorisation Collegue

		passagerTest = new Collegue("MonsieurPassagerTest", "test", "testPassager@test.fr", "Mdptest", "00000000");

		responcableTest = new Collegue("MonsieurResponcebleTest", "ResponcableTest", "testResponsable@test.fr",
				"MdpResponcabletest", "00000000");
		passagerTest = new Collegue("MonsieurPassagerTest", "PassagerTest", "testpassager@test.fr", "MdpPassagertest",
				"00000000");

		annonceTest = new Annonce(
				new Itineraire(LocalDateTime.now(), LocalDateTime.now().plusDays(5), "test", "test", 100, 100D),
				responcableTest, "TT-666-TT", "Test", "test", 4, StatutAnnonce.STATUT_EN_COURS);

		annoncePassagerTest = new Annonce(
				new Itineraire(LocalDateTime.now(), LocalDateTime.now().plusDays(5), "test", "test", 100, 100D),
				passagerTest, "TT-666-TT", "Test", "test", 4, StatutAnnonce.STATUT_EN_COURS);

		listAnnonces = new ArrayList<>();
		listAnnonces.add(annonceTest);

		listAnnoncesPassager.add(annoncePassagerTest);
		// Valorosation Itineraire

		Itineraire itinerairePast = new Itineraire(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1),
				"test", "test", 100, 100D);
		Itineraire itineraireProxPast = new Itineraire(LocalDateTime.now().minusHours(2),
				LocalDateTime.now().minusMinutes(4), "test", "test", 100, 100D);
		Itineraire itineraireProxFuture = new Itineraire(LocalDateTime.now().plusMinutes(6),
				LocalDateTime.now().plusMinutes(10), "test", "test", 100, 100D);
		Itineraire itineraireFuture = new Itineraire(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5),
				"test", "test", 100, 100D);

		// Valorosation Annonce
		annoncePast = new Annonce(new Itineraire(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1),
				"test", "test", 100, 100D), passagerTest, "TT-666-TT", "Test", "test", 4, StatutAnnonce.STATUT_EN_COURS);
		annonceFuture = new Annonce(new Itineraire(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1),
				"test", "test", 100, 100D), passagerTest, "TT-666-TT", "Test", "test", 4, StatutAnnonce.STATUT_EN_COURS);

		listAnnoncesTime.add(new Annonce(itinerairePast, passagerTest, "TT-666-TT", "Test", "test", 4,StatutAnnonce.STATUT_EN_COURS));
		listAnnoncesTime.add(new Annonce(itineraireProxPast, passagerTest, "TT-666-TT", "Test", "test", 4,StatutAnnonce.STATUT_EN_COURS));
		listAnnoncesTime.add(new Annonce(itineraireProxFuture, passagerTest, "TT-666-TT", "Test", "test", 4,StatutAnnonce.STATUT_EN_COURS));
		listAnnoncesTime.add(new Annonce(itineraireFuture, passagerTest, "TT-666-TT", "Test", "test", 4,StatutAnnonce.STATUT_EN_COURS));

	}
//
//	@Test
//	void testGetAnnoncesByResponcable() {
//		when(this.collegueRepository.findOneByEmail(emailResponsableTest)).thenReturn(Optional.of(responcableTest));
//		when(this.annonceRepository.findAllByResponsable(responcableTest)).thenReturn(listAnnonces);
//
//		assertThat(this.annonceService.getAnnoncesByResponcable(emailResponsableTest)).isNotEmpty();
//		assertThat(this.annonceService.getAnnoncesByResponcable(emailResponsableTest)).containsOnly(annonceTest);
//	}
//
//	@Test
//	void testGetAnnoncesByResponcableCollegueNonTrouve() {
//		when(this.collegueRepository.findOneByEmail(emailResponsableTest)).thenReturn(Optional.empty());
//		assertThatExceptionOfType(CollegueNonTrouveException.class)
//				.isThrownBy(() -> this.annonceService.getAnnoncesByResponcable(emailResponsableTest))
//				.withMessage("Aucun responcable de covoiturage trouvé avec cet email : testResponsable@test.fr");
//
//	}
//
//	@Test
//	void testGetAnnonceByPassager() {
//		when(this.collegueRepository.findOneByEmail(emailPassagerTest)).thenReturn(Optional.of(passagerTest));
//		when(this.annonceRepository.findAllBylistPassagers(passagerTest)).thenReturn(listAnnonces);
//
//		assertThat(this.annonceService.getAnnonceByPassager(emailPassagerTest)).isNotEmpty();
//		assertThat(this.annonceService.getAnnonceByPassager(emailPassagerTest)).containsOnly(annonceTest);
//	}
//
//	@Test
//	void testGetAnnonceByPassagerCollegueNonTrouve() {
//
//		when(this.collegueRepository.findOneByEmail(emailPassagerTest)).thenReturn(Optional.empty());
//		assertThatExceptionOfType(CollegueNonTrouveException.class)
//				.isThrownBy(() -> this.annonceService.getAnnonceByPassager(emailPassagerTest))
//				.withMessage("Aucun passager trouvé avec cet email : testPassager@test.fr");
//
//	}
//
//	@Test
//	void testGetAnnonceEnCours() {
//
//		assertEquals(2, this.annonceService.getAnnonceEnCours(listAnnoncesTime).size());
//		assertThat(this.annonceService.getAnnonceEnCours(listAnnoncesTime).contains(annoncePast));
//
//	}
//
//
//	@Test
//	void testGetHistoriqueAnnonce() {
//		assertEquals(2, this.annonceService.getHistoriqueAnnonce(listAnnoncesTime).size());
//		assertThat(this.annonceService.getAnnonceEnCours(listAnnoncesTime).contains(annonceFuture));
//	}

//	@Test
//	void testGetallAnnonceEnCours() {
//		when(this.annonceService.getAllAnnoncesEnCours()).thenReturn(listAnnoncesTime);
//		assertThat(this.annonceService.getAllAnnoncesEnCours().contains(annonceFuture));
//		//la methode testé n'est qu'un passe plat
//	}
////TODO

}
