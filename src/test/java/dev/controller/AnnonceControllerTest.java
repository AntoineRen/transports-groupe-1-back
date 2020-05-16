package dev.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutAnnonce;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.security.JWTAuthenticationSuccessHandler;
import dev.service.AnnonceService;
import dev.service.ReservationService;

@WebMvcTest(AnnonceController.class)
class AnnonceControllerTest {

//Mock
	@Autowired
	MockMvc mockMvc;

	@MockBean
	AnnonceService annonceService;

	@MockBean
	JWTAuthenticationSuccessHandler jwt;

	@MockBean
	SecurityContext securityContext;

	@MockBean
	DataSource dtSource;

	// Instance necessaire, jeu de donnée
	String baseUrl = "/annonce";
	String emailTest = "testAnnonce@test.fr";

	String emailResponsableTest = "testResponsable@test.fr";
	String emailPassagerTest = "testPassager@test.fr";
	Annonce annonceTestResponcable;
	Annonce annonceTestPassager;
	Collegue responcableTest;
	Collegue passagerTest;
	List<Annonce> listAnnoncesResponsable = new ArrayList<>();
	List<Annonce> listAnnoncesPassager = new ArrayList<>();
	List<Annonce> listAnnonceEnCours = new ArrayList<>();


	@BeforeEach
	public void init() {

	

		responcableTest = new Collegue("MonsieurResponcebleTest", "ResponcableTest", "testResponsable@test.fr",
				"MdpResponcabletest", "00000000");

		annonceTestResponcable = new Annonce(new Itineraire(LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(6), "test", "test", 100, 100D), responcableTest, "TT-666-TT", "Test",
				"test", 4, StatutAnnonce.STATUT_EN_COURS);

		listAnnoncesResponsable = new ArrayList<>();
		listAnnoncesResponsable.add(annonceTestResponcable);

		passagerTest = new Collegue("MonsieurPassagerTest", "test", "testPassager@test.fr", "Mdptest", "00000000");

		annonceTestPassager = new Annonce(new Itineraire(LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(6), "test", "test", 100, 100D), passagerTest, "TT-666-TT", "Test", "test",
				4, StatutAnnonce.STATUT_EN_COURS);
		listAnnoncesPassager = new ArrayList<>();
		listAnnoncesPassager.add(annonceTestPassager);


	
		listAnnonceEnCours.add(new Annonce(new Itineraire(LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(6), "test", "test", 100, 100D), responcableTest, "TT-666-TT", "Test",
				"test", 4,StatutAnnonce.STATUT_EN_COURS));
		
	}

//	@Test
//	@WithMockUser(username = "testResponsable@test.fr")
//	void testgetAnnoncesByResponsableEmail() throws Exception {
//
//		when(this.annonceService.getAnnonceEnCours(listAnnoncesResponsable)).thenReturn(listAnnoncesResponsable);
//		when(this.annonceService.getAnnoncesByResponcable(emailResponsableTest)).thenReturn(listAnnoncesResponsable);
//
//		mockMvc.perform(get(baseUrl + "/listAnnonceByResponsable")).andExpect(status().is(200))
//				.andExpect(jsonPath("$[0].responsable.nom").value("MonsieurResponcebleTest"))
//				.andExpect(jsonPath("$[0].itineraire.distance").value(100))
//				.andExpect(jsonPath("$[0].immatriculation").value("TT-666-TT"));
//	}
//
//	@Test
//	@WithMockUser(username = "testPassager@test.fr")
//	void testgetAnnoncesByPassagerEmail() throws Exception {
//		when(this.annonceService.getAnnonceByPassager(emailPassagerTest)).thenReturn(listAnnoncesPassager);
//		when(this.annonceService.getAnnonceEnCours(listAnnoncesPassager)).thenReturn(listAnnoncesPassager);
//
//		mockMvc.perform(get(baseUrl + "/listAnnonceByPassager")).andExpect(status().is(200))
//				.andExpect(jsonPath("$[0].responsable.nom").value("MonsieurPassagerTest"))
//				.andExpect(jsonPath("$[0].itineraire.distance").value(100))
//				.andExpect(jsonPath("$[0].immatriculation").value("TT-666-TT"));
//	}
//
//	@Test
//	@WithMockUser(username = "testPassager@test.fr")
//	void testgetFutureAnnoncesByCollegue() throws Exception {
//		when(this.annonceService.getAnnonceEnCours(listAnnoncesPassager)).thenReturn(listAnnoncesPassager);
//		when(this.annonceService.getAllAnnoncesByCollegue(emailPassagerTest)).thenReturn(listAnnoncesPassager);
//
//		mockMvc.perform(get(baseUrl + "/listAnnonceEnCours")).andExpect(status().is(200));
////				.andExpect(LocalDate.parse(jsonPath("$[0].itineraire.dateDepart").toString()).isAfter(LocalDate.now())); 
//
//	}
//
//	@Test
//	@WithMockUser(username = "testPassager@test.fr")
//	void testgetHistoriqueAnnoncesByCollegue() throws Exception {
//		when(this.annonceService.getHistoriqueAnnonce(listAnnoncesPassager)).thenReturn(listAnnoncesPassager);
//		when(this.annonceService.getAllAnnoncesByCollegue(emailPassagerTest)).thenReturn(listAnnoncesPassager);
//
//		mockMvc.perform(get(baseUrl + "/listAnnonceHistorique")).andExpect(status().is(200))
//				.andExpect(jsonPath("$[0].responsable.nom").value("MonsieurPassagerTest"))
//				.andExpect(jsonPath("$[0].itineraire.distance").value(100))
//				.andExpect(jsonPath("$[0].immatriculation").value("TT-666-TT"));
//	}
//	@Test
//	@WithMockUser(username = "testPassager@test.fr")
//	void testgetAllAnnoncesEnCours() throws Exception {
//		when(this.annonceService.getAllAnnoncesEnCours()).thenReturn(listAnnonceEnCours);
//		
//
//		mockMvc.perform(get(baseUrl + "/listAllAnnonce")).andExpect(status().is(200));
////				.andExpect(jsonPath("$[0].itineraire.da").value(100));
////				.andExpect(jsonPath("$[0].immatriculation").value("TT-666-TT"));
//	}

}
