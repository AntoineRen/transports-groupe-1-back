package dev.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import dev.entites.Collegue;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.dto.ReservationDto;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutDemandeChauffeur;
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

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	// Jeu de données
	String baseUrl = "/reservation";
	String emailTest = "test@test.fr";

	Collegue collegueTest;
	VehiculeSociete vehiculeTest1;
	VehiculeSociete vehiculeTest2;
	List<Reservation> reservationsEnCoursTest;
	List<Reservation> reservationsHistoTest;
	Reservation reservationEnCoursTest;
	ReservationDto reservationDto;
	String jsonResaDto;

	@BeforeEach
	public void init() {

		collegueTest = new Collegue("test", "test", "test@test.fr", "test", "00000000");
		vehiculeTest1 = new VehiculeSociete("immatriculationTest1", "marqueTest", "modeleTest", Categorie.CATEGORIE_BTL,
				5, StatutVehiculeSociete.EN_SERVICE, null);
		vehiculeTest2 = new VehiculeSociete("immatriculationTest2", "marqueTest", "modeleTest", Categorie.CATEGORIE_BTL,
				5, StatutVehiculeSociete.EN_SERVICE, null);
		reservationEnCoursTest = new Reservation(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5),
				collegueTest, null, vehiculeTest1,
				StatutDemandeChauffeur.SANS_CHAUFFEUR);
		Reservation reservationHistoTest = new Reservation(LocalDateTime.now().minusDays(5),
				LocalDateTime.now().minusDays(5), collegueTest, null, vehiculeTest2,
				StatutDemandeChauffeur.SANS_CHAUFFEUR);

		reservationsEnCoursTest = new ArrayList<>();
		reservationsEnCoursTest.add(reservationEnCoursTest);

		reservationsHistoTest = new ArrayList<>();
		reservationsHistoTest.add(reservationHistoTest);

		reservationDto = new ReservationDto(LocalDateTime.of(2020, 5, 12, 12, 0), LocalDateTime.of(2020, 5, 12, 15, 0),
				1L, false);
		jsonResaDto = "{\"dateDepart\":\"2020-05-12T12:00\",\"dateArrivee\":\"2020-05-12T12:00\",\"vehiculeId\":1,\"avecChauffeur\":false}";
	}

	@Test
	@WithMockUser(username = "test@test.fr")
	void testGetReservationByEmailEnCours() throws Exception {

		when(this.reservationService.getReservationByEmailEnCours(emailTest)).thenReturn(reservationsEnCoursTest);

		mockMvc.perform(get(baseUrl + "/current/")).andExpect(status().is(200))
				.andExpect(jsonPath("$[0].responsable.nom").value("test"))
				.andExpect(jsonPath("$[0].vehicule.immatriculation").value("immatriculationTest1"));

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
				.andExpect(jsonPath("$[0].vehicule.immatriculation").value("immatriculationTest2"));

	}

	@Test
	@WithMockUser(username = "test@test.fr")
	void testGetReservationByEmailHistoCollegueNonTrouve() throws Exception {

		when(this.reservationService.getReservationByEmailHisto(emailTest))
				.thenThrow(new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : test@test.fr"));

		mockMvc.perform(get(baseUrl + "/histo/")).andExpect(status().is(404))
				.andExpect(jsonPath("$").value("Aucun collègue trouvé avec cet email : test@test.fr"));

	}

	@Test
	@WithMockUser(username = "test@test.fr")
	void testPostReservation() throws Exception {

		when(this.reservationService.postReservation(anyString(), any())).thenReturn(reservationEnCoursTest);

		mockMvc.perform(post(baseUrl).contentType(APPLICATION_JSON_UTF8).content(jsonResaDto))
				.andExpect(status().is(200)).andExpect(jsonPath("$.responsable.nom").value("test"))
				.andExpect(jsonPath("$.vehicule.immatriculation").value("immatriculationTest1"));

	}

}
