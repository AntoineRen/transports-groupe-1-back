package dev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.entites.Collegue;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.dto.ReservationDto;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutReservation;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.exceptions.CollegueNonTrouveException;
import dev.exceptions.ReservationHoraireIncompatibleException;
import dev.exceptions.VehiculeNonTrouveException;
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
	ReservationDto reservationDto;

	@BeforeEach
	public void init() {

		collegueTest = new Collegue("test", "test", "test@test.fr", "test", "00000000");
		vehiculeTest = new VehiculeSociete("immatriculationTest", "marqueTest", "modeleTest", Categorie.CATEGORIE_BTL,
				5, StatutVehiculeSociete.EN_SERVICE, null);
		reservationEnCoursTest = new Reservation(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5),
				collegueTest, null, StatutReservation.STATUT_EN_COURS, vehiculeTest);
		reservationHistoTest = new Reservation(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5),
				collegueTest, null, StatutReservation.STATUT_EN_COURS, vehiculeTest);

		reservations = new ArrayList<>();
		reservations.add(reservationEnCoursTest);
		reservations.add(reservationHistoTest);

		reservationDto = new ReservationDto(LocalDateTime.now(), LocalDateTime.now(), 1L);

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

	@Test
	void testPostReservation() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.of(collegueTest));
		when(this.vehiculeRepository.findById(reservationDto.getVehicule_id())).thenReturn(Optional.of(vehiculeTest));
		when(this.reservationRepository.findAllByVehicule(vehiculeTest)).thenReturn(reservations);

		assertThat(this.reservationService.postReservation(emailTest, reservationDto).getVehicule())
				.isEqualTo(vehiculeTest);
		assertThat(this.reservationService.postReservation(emailTest, reservationDto).getResponsable())
				.isEqualTo(collegueTest);
	}

	@Test
	void testPostReservationCollegueNonTrouve() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.empty());

		assertThatExceptionOfType(CollegueNonTrouveException.class)
				.isThrownBy(() -> this.reservationService.postReservation(emailTest, reservationDto))
				.withMessage("Aucun collègue trouvé avec cet email : test@test.fr");
	}

	@Test
	void testPostReservationVehiculeNonTrouve() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.of(collegueTest));
		when(this.vehiculeRepository.findById(reservationDto.getVehicule_id())).thenReturn(Optional.empty());

		assertThatExceptionOfType(VehiculeNonTrouveException.class)
				.isThrownBy(() -> this.reservationService.postReservation(emailTest, reservationDto))
				.withMessage("Aucun véhicule trouvé avec cet id : 1");
	}

	@Test
	void testPostReservationReservationHoraireImcompatible() {

		when(this.collegueRepository.findOneByEmail(emailTest)).thenReturn(Optional.of(collegueTest));
		when(this.vehiculeRepository.findById(reservationDto.getVehicule_id())).thenReturn(Optional.of(vehiculeTest));
		when(this.reservationRepository.findAllByVehicule(vehiculeTest))
				.thenReturn(Arrays.asList(new Reservation(reservationDto.getDateDepart(),
						reservationDto.getDateArrivee(), null, null, null, null)));

		assertThatExceptionOfType(ReservationHoraireIncompatibleException.class)
				.isThrownBy(() -> this.reservationService.postReservation(emailTest, reservationDto))
				.withMessage("Impossible de créer la réservation du fait d'horraires incompatibles.");
	}
}
