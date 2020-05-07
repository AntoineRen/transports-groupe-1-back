package dev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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

import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutReservation;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.CollegueRepository;
import dev.repository.ReservationRepository;
import dev.repository.VehiculeSocieteRepository;

@SpringJUnitConfig(ReservationService.class)
class ReservationServiceTest {

	// Mock
	@Autowired
	ReservationService reservationService;

	@MockBean
	ReservationRepository reservationRepository;

	@MockBean
	CollegueRepository collegueRepository;

	@MockBean
	VehiculeSocieteRepository vehiculeRepository;

	// Jeu de données
	String baseUrl = "/reservation";
	String emailTest = "test@test.fr";

	Collegue collegueTest;
	VehiculeSociete vehiculeTest;
	Reservation reservationEnCoursTest;
	Reservation reservationHistoTest;
	List<Reservation> reservations;

	@BeforeEach
	public void init() {

		collegueTest = new Collegue("test", "test", "test@test.fr", "test", "00000000");
		vehiculeTest = new VehiculeSociete("immatriculationTest", "marqueTest", "modeleTest", Categorie.CATEGORIE_BTL,
				5, StatutVehiculeSociete.EN_SERVICE);
		reservationEnCoursTest = new Reservation(new Itineraire(LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5), "test", "test", 100, 100D), collegueTest, null,
				StatutReservation.STATUT_EN_COURS, vehiculeTest);
		reservationHistoTest = new Reservation(new Itineraire(LocalDateTime.now().minusDays(5),
				LocalDateTime.now().minusDays(5), "test", "test", 100, 200D), collegueTest, null,
				StatutReservation.STATUT_EN_COURS, vehiculeTest);

		reservations = new ArrayList<>();
		reservations.add(reservationEnCoursTest);
		reservations.add(reservationHistoTest);

	}

	@Test
	void testGetReservationByEmailEnCours() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.of(collegueTest));
		when(this.reservationRepository.findAllByResponsable(collegueTest)).thenReturn(reservations);

		assertThat(this.reservationService.getReservationByEmailEnCours(emailTest)).isNotEmpty();
		assertThat(this.reservationService.getReservationByEmailEnCours(emailTest))
				.containsOnly(reservationEnCoursTest);
	}

	@Test
	void testGetReservationByEmailEnCoursCollegueNonTrouve() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.empty());

		assertThatExceptionOfType(CollegueNonTrouveException.class)
				.isThrownBy(() -> this.reservationService.getReservationByEmailEnCours(emailTest))
				.withMessage("Aucun collègue trouvé avec cet email : test@test.fr");
	}

	@Test
	void testGetReservationByEmailHisto() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.of(collegueTest));
		when(this.reservationRepository.findAllByResponsable(collegueTest)).thenReturn(reservations);

		assertThat(this.reservationService.getReservationByEmailHisto(emailTest)).isNotEmpty();
		assertThat(this.reservationService.getReservationByEmailHisto(emailTest)).containsOnly(reservationHistoTest);
	}

	@Test
	void testGetReservationByEmailHistoCollegueNonTrouve() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.empty());

		assertThatExceptionOfType(CollegueNonTrouveException.class)
				.isThrownBy(() -> this.reservationService.getReservationByEmailHisto(emailTest))
				.withMessage("Aucun collègue trouvé avec cet email : test@test.fr");
	}
}
