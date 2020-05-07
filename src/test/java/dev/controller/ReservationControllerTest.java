package dev.controller;

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
import org.springframework.test.web.servlet.MockMvc;

import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutReservation;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.exceptions.CollegueNonTrouveException;
import dev.security.JWTAuthenticationSuccessHandler;
import dev.service.ReservationService;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

	// Mock
	@Autowired
	MockMvc mockMvc;

	@MockBean
	ReservationService reservationService;

	@MockBean
	JWTAuthenticationSuccessHandler jwt;

	@MockBean
	SecurityContext securityContext;

	@MockBean
	DataSource dtSource;

	// Jeu de données
	String baseUrl = "/reservation";
	String emailTest = "test@test.fr";

	Collegue collegueTest;
	VehiculeSociete vehiculeTest;
	List<Reservation> reservationsEnCoursTest;
	List<Reservation> reservationsHistoTest;

	@BeforeEach
	public void init() {

		collegueTest = new Collegue("test", "test", "test@test.fr", "test", "00000000");
		vehiculeTest = new VehiculeSociete("immatriculationTest", "marqueTest", "modeleTest", Categorie.CATEGORIE_BTL,
				5, StatutVehiculeSociete.EN_SERVICE);
		Reservation reservationEnCoursTest = new Reservation(new Itineraire(LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5), "test", "test", 100, 100D), collegueTest, null,
				StatutReservation.STATUT_EN_COURS, vehiculeTest);
		Reservation reservationHistoTest = new Reservation(new Itineraire(LocalDateTime.now().minusDays(5),
				LocalDateTime.now().minusDays(5), "test", "test", 100, 200D), collegueTest, null,
				StatutReservation.STATUT_EN_COURS, vehiculeTest);

		reservationsEnCoursTest = new ArrayList<>();
		reservationsEnCoursTest.add(reservationEnCoursTest);

		reservationsHistoTest = new ArrayList<>();
		reservationsHistoTest.add(reservationHistoTest);

	}

	@Test
	@WithMockUser(username = "test@test.fr")
	void testGetReservationByEmailEnCours() throws Exception {

		when(this.reservationService.getReservationByEmailEnCours(emailTest)).thenReturn(reservationsEnCoursTest);

		mockMvc.perform(get(baseUrl + "/current/")).andExpect(status().is(200))
				.andExpect(jsonPath("$[0].responsable.nom").value("test"))
				.andExpect(jsonPath("$[0].itineraire.distance").value(100))
				.andExpect(jsonPath("$[0].vehicule.immatriculation").value("immatriculationTest"));

	}

	@Test
	@WithMockUser(username = "test@test.fr")
	void testGetReservationByEmailEnCoursCollegueNonTrouve() throws Exception {

		when(this.reservationService.getReservationByEmailEnCours(emailTest))
				.thenThrow(new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : test@test.fr"));

		mockMvc.perform(get(baseUrl + "/current/")).andExpect(status().is(404))
				.andExpect(jsonPath("$").value("Aucun collègue trouvé avec cet email : test@test.fr"));

	}

	@Test
	@WithMockUser(username = "test@test.fr")
	void testGetReservationByEmailHisto() throws Exception {

		when(this.reservationService.getReservationByEmailHisto(emailTest)).thenReturn(reservationsHistoTest);

		mockMvc.perform(get(baseUrl + "/histo/")).andExpect(status().is(200))
				.andExpect(jsonPath("$[0].responsable.nom").value("test"))
				.andExpect(jsonPath("$[0].itineraire.distance").value(200))
				.andExpect(jsonPath("$[0].vehicule.immatriculation").value("immatriculationTest"));

	}

	@Test
	@WithMockUser(username = "test@test.fr")
	void testGetReservationByEmailHistoCollegueNonTrouve() throws Exception {

		when(this.reservationService.getReservationByEmailHisto(emailTest))
				.thenThrow(new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : test@test.fr"));

		mockMvc.perform(get(baseUrl + "/histo/")).andExpect(status().is(404))
				.andExpect(jsonPath("$").value("Aucun collègue trouvé avec cet email : test@test.fr"));

	}

}
