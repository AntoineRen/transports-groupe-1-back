package dev.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import dev.entites.utiles.StatutReservation;
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
	Collegue collegueTest;
	String emailResponsableTest = "testResponsable@test.fr";
	String emailPassagerTest = "testPassager@test.fr";
	Annonce annonceTest;
	Collegue responcableTest;
	Collegue passagerTest;
	List<Annonce> listAnnonces=new ArrayList<>();
	List<Annonce> listAnnoncesTime =new ArrayList<>();
	Annonce annoncePast = new Annonce();
	Annonce annonceFuture = new Annonce();
	
	@BeforeEach
	public void init() {
		
		// Valorisation Collegue

				passagerTest = new Collegue("MonsieurPassagerTest", "test", "testPassager@test.fr", "Mdptest", "00000000");
				
				responcableTest = new Collegue("MonsieurResponcebleTest", "ResponcableTest", "testResponsable@test.fr",
						"MdpResponcabletest", "00000000");
				
				annonceTest = new Annonce(
						new Itineraire(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(6), "test", "test", 100, 100D),
						responcableTest, "TT-666-TT", "Test", "test", 4);

				listAnnonces = new ArrayList<>();
				listAnnonces.add(annonceTest);
				
				// Valorosation Itineraire

				Itineraire itinerairePast = new Itineraire(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1),
						"test", "test", 100, 100D);
				Itineraire itineraireProxPast = new Itineraire(LocalDateTime.now().minusMinutes(5),
						LocalDateTime.now().minusMinutes(5), "test", "test", 100, 100D);
				Itineraire itineraireProxFuture = new Itineraire(LocalDateTime.now().plusMinutes(5),
						LocalDateTime.now().plusMinutes(5), "test", "test", 100, 100D);
				Itineraire itineraireFuture = new Itineraire(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5),
						"test", "test", 100, 100D);

				// Valorosation Annonce
			annoncePast = new Annonce(new Itineraire(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1),
						"test", "test", 100, 100D), passagerTest, "TT-666-TT", "Test", "test", 4);
			annonceFuture = new Annonce(new Itineraire(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1),
					"test", "test", 100, 100D), passagerTest, "TT-666-TT", "Test", "test", 4);
				
				listAnnoncesTime.add(new Annonce(itinerairePast, passagerTest, "TT-666-TT", "Test", "test", 4));
				listAnnoncesTime.add(new Annonce(itineraireProxPast, passagerTest, "TT-666-TT", "Test", "test", 4));
				listAnnoncesTime.add(new Annonce(itineraireProxFuture, passagerTest, "TT-666-TT", "Test", "test", 4));
				listAnnoncesTime.add(new Annonce(itineraireFuture, passagerTest, "TT-666-TT", "Test", "test", 4));

	}

//	@Test
//	@WithMockUser(username = "testAnnonce@test.fr")
//	void testgetAnnoncesByResponsableEmail() throws Exception {
//		when(this.annonceService.getAnnoncesByResponcable(emailTest)).thenReturn(listAnnonces);
//
//		mockMvc.perform(get(baseUrl + "/listAnnonceByResponsable")).andExpect(status().is(200))
//				.andExpect(jsonPath("$[0].responsable.nom").value("test"))
//				.andExpect(jsonPath("$[0].itineraire.distance").value(100))
//				.andExpect(jsonPath("$[0].immatriculation").value("TT-666-TT"));
//	}
}
