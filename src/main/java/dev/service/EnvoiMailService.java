package dev.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.utiles.StatutDemandeChauffeur;
import dev.exceptions.ApplicationException;

/**
 * @author jules
 *
 */
@Service
public class EnvoiMailService {



	/**
	 * Constructeur
	 * 
	 * @param annonceservice
	 * @param reservationService
	 */
	public EnvoiMailService() {
	

		
	}

	/**
	 * Permet d'envoyer un mail au responsable et au chanffeur s'il y en a un. Pour
	 * prévenir de l'annulation de la reservation du vehicule de sociéte
	 * 
	 * @param reservation sujet de l'annulation
	 * @throws Exception
	 */
	public void envoyerMailAnnulationReservationVehiculeSociete(Reservation reservation){
		
		//reccuperation inf pour template
		String statutResaChauffeur = reservation.getStatutDemandeChauffeur().name();
		Collegue chauffeur = reservation.getChauffeur();
		String vehiculeString = "" + reservation.getVehicule().getMarque() + " - "
				+ reservation.getVehicule().getModele();
		String sujet = "Annulation Reservation " + vehiculeString;
		Collegue passager = reservation.getResponsable();
		String templateMailresponcable = this.getMailTemplateAnnulationReservationVehiculePassager(reservation);

		
		switch (statutResaChauffeur) {
		case "EN_ATTENTE":

			this.sendMail(passager.getEmail(), templateMailresponcable, sujet);
			break;
		case "AVEC_CHAUFFEUR":

			String templateMailChauffeur = getMailTemplateAnnulationReservationVehiculeChauffeur(reservation);
			this.sendMail(passager.getEmail(), templateMailresponcable, sujet);
			this.sendMail(chauffeur.getEmail(), templateMailChauffeur, sujet);

			break;
		case "SANS_CHAUFFEUR":

			this.sendMail(passager.getEmail(), templateMailresponcable, sujet);
			break;

		default:
			throw new ApplicationException(
					statutResaChauffeur + "n'est pas un statut correcte pour un chauffeur d'une réservation ");

		}
	}

	/**
	 * @param responsable   de l'annonce
	 * @param listPassagers des reservations de l'annonce
	 * 
	 *                      Permet d'envoyer un mail prévenant de l'annulation de
	 *                      'annonce a tout les passagers de l'annonce ainsi qu'un
	 *                      mail de comfirmation d'annulation au responcable de
	 *                      l'annonce
	 */
	public void envoyerMailAnnulationAnnonce(Annonce annonce) {
		List<Collegue> listPassagers = annonce.getListPassagers();
		Itineraire itineraire = annonce.getItineraire();
		Collegue responsable = annonce.getResponsable();
		String sujet = "Annulation Covoiturage du " + itineraire.getDateDepart();

		// envoi d'un mail a tout les passager avec le template adequat
		for (Collegue passager : listPassagers) {
			String templateMailPassager = this.getMailTemplateAnnulationAnnonceForPassager(passager, itineraire);
			this.sendMail(passager.getEmail(), templateMailPassager, sujet);
		}

		String templateMailConducteur = this.getMailTemplateAnnulationAnnonceForResponsable(responsable, itineraire);
		this.sendMail(responsable.getEmail(), templateMailConducteur, sujet);
	}

	/**
	 * @param annonce  sujet de l'annulation de reservation
	 * @param passager sujet de l'annulation de reservation permet d'envoyer un mail
	 *                 de comfirmation de lannulation de la reservation du
	 *                 covoiturage au passager. Prévient également au conducteur de
	 *                 l'annulation d'un réservation
	 */
	public void envoyerMailAnnulationReservation(Annonce annonce, Collegue passager) {
		Itineraire itineraire = annonce.getItineraire();
		Collegue responsable = annonce.getResponsable();
		String sujet = "Annulation Reservation du " + itineraire.getDateDepart();

		// envoi du mail d'annulation d'une réservation au responsable de l'annonce
		String templateMailResponcable = this.getMailTemplateAnnulationReservationForResponsable(responsable, passager,
				itineraire);
		this.sendMail(responsable.getEmail(), templateMailResponcable, sujet);
		// envois du mail de comfirmation d'annulation de la reservation au passager
		// concerné
		String templateMailPassager = this.getMailTemplateAnnulationReservationForPassager(passager, itineraire);
		this.sendMail(passager.getEmail(), templateMailPassager, sujet);
	}

	/**
	 * @param dateAnnonce
	 * @return la date et l'heure formatter pour les templates de mail
	 */
	private String getDateFormater(LocalDateTime dateAnnonce) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY, à HH:mm");

		return formatter.format(dateAnnonce);
	}

	/**
	 * @param passager de l'annonce concernée
	 * @param itinr    de l'annonce concernée
	 * @return template du mail d'annulation d'un annonce pour prévenir les
	 *         passagers
	 */
	private String getMailTemplateAnnulationAnnonceForPassager(Collegue passager, Itineraire itinr) {
		return "<h4>Bonjour " + passager.getNom() + " " + passager.getPrenom() + ",</h4>" + ""
				+ "Votre Covoiturage au depart de " + itinr.getLieuDepart() + ", à destination de "
				+ itinr.getLieuDestination() + " prévu le " + this.getDateFormater(itinr.getDateDepart())
				+ ", à été annulé. " + "<br><br><br>"
				+ "<img src=\"https://cdn.discordapp.com/attachments/705412725098020906/707149807038496778/logo.png\" alt=\"logo\"style=\"width:100px;height:100px;\">"
				+ "   L'equipe de GDT-Transport";

	}

	/**
	 * @param responsable de l'annonce
	 * @param itinr       de l'annonce
	 * @return template du mail d'annulation d'annonce pour comfirmer cette
	 *         annulation au prés du responcable
	 */
	private String getMailTemplateAnnulationAnnonceForResponsable(Collegue responsable, Itineraire itinr) {
		return "<h4>Bonjour " + responsable.getNom() + " " + responsable.getPrenom() + ",</h4>"
				+ "Votre Covoiturage au depart de " + itinr.getLieuDepart() + ", à destination de "
				+ itinr.getLieuDestination() + " prévu le " + this.getDateFormater(itinr.getDateDepart())
				+ " à bien été annulé. <br>" + "Vos passagers ont été prévenus" + "<br><br><br>"
				+ "<img src=\"https://cdn.discordapp.com/attachments/705412725098020906/707149807038496778/logo.png\" alt=\"logo\"style=\"width:100px;height:100px;\">"
				+ "   L'equipe de GDT-Transport";
	}

	/**
	 * @param passager de la reservation
	 * @param itinr    de l'annonce
	 * @return template du mail de comfirmation d'annulation de reservation pour
	 *         comfirmé cette annulation au prés du passager
	 */
	private String getMailTemplateAnnulationReservationForPassager(Collegue passager, Itineraire itinr) {
		return "<h4>Bonjour " + passager.getNom() + " " + passager.getPrenom() + ",</h4>"
				+ "Votre Covoiturage au depart de " + itinr.getLieuDepart() + ", à destination de "
				+ itinr.getLieuDestination() + " prévu le " + this.getDateFormater(itinr.getDateDepart())
				+ ", à bien été annulé. <br>" + "Le conducteur a bien été prévenu" + "<br><br><br>"
				+ "<img src=\"https://cdn.discordapp.com/attachments/705412725098020906/707149807038496778/logo.png\" alt=\"logo\"style=\"width:100px;height:100px;\">"
				+ "   L'equipe de GDT-Transport";
	}

	/**
	 * @param responsable
	 * @param passager
	 * @param itinr
	 * @return template du mail prévenant de l'annulation d'une réservation d'un
	 *         vehicule d'entreprise pour le responcable
	 */
	private String getMailTemplateAnnulationReservationVehiculePassager(Reservation reservation) {
		Collegue responsable = reservation.getResponsable();
		VehiculeSociete vehicule = reservation.getVehicule();

		return "<h4>Bonjour " + responsable.getNom() + " " + responsable.getPrenom() + ",</h4>"
				+ "Votre réservation à la date du " + reservation.getDateDepart() + " au "
				+ reservation.getDateArrivee() + " pour le vehicule : " + vehicule.getMarque() + " - "
				+ vehicule.getModele() + " immatriculé " + vehicule.getImmatriculation()
				+ " est malheureusment annuler. Ce vehicule est hors service." + "<br><br><br>"
				+ "<img src=\"https://cdn.discordapp.com/attachments/705412725098020906/707149807038496778/logo.png\" alt=\"logo\"style=\"width:100px;height:100px;\">"
				+ "   L'equipe de GDT-Transport";
	}

	/**
	 * @param responsable
	 * @param passager
	 * @param itinr
	 * @return template du mail prévenant de l'annulation d'une réservation
	 */
	private String getMailTemplateAnnulationReservationForResponsable(Collegue responsable, Collegue passager,
			Itineraire itinr) {

		return "<h4>Bonjour " + responsable.getNom() + " " + responsable.getPrenom() + ",</h4>" + passager.getNom()
				+ " " + passager.getPrenom() + " vient d'annuler sa reservation au depart de "
				+ itinr.getLieuDestination() + ", prévu le " + this.getDateFormater(itinr.getDateDepart())
				+ " à destination de " + itinr.getLieuDestination()
				+ "vous disposez d'une place supplémentaire dans votre annonce de covoiturage" + "<br><br><br>"
				+ "<img src=\"https://cdn.discordapp.com/attachments/705412725098020906/707149807038496778/logo.png\" alt=\"logo\"style=\"width:100px;height:100px;\">"
				+ "   L'equipe de GDT-Transport";
	}

	/**
	 * @param responsable
	 * @param passager
	 * @param itinr
	 * @return template du mail prévenant de l'annulation d'une réservation d'un
	 *         vehicule d'entreprise pour le responcable
	 */
	private String getMailTemplateAnnulationReservationVehiculeChauffeur(Reservation reservation) {
		Collegue responsable = reservation.getResponsable();
		Collegue chauffeur = reservation.getChauffeur();
		VehiculeSociete vehicule = reservation.getVehicule();

		return "<h4>Bonjour " + chauffeur.getNom() + " " + chauffeur.getPrenom() + ",</h4>" + "Votre trajet avec "
				+ responsable.getNom() + " " + responsable.getPrenom() + " à la date du " + reservation.getDateDepart()
				+ " au " + reservation.getDateArrivee() + " pour le vehicule : " + vehicule.getMarque() + " - "
				+ vehicule.getModele() + " immatriculé " + vehicule.getImmatriculation()
				+ " est malheureusment annuler. Ce vehicule est hors service. Votre Passager à été prévenu."
				+ "<br><br><br>"
				+ "<img src=\"https://cdn.discordapp.com/attachments/705412725098020906/707149807038496778/logo.png\" alt=\"logo\"style=\"width:100px;height:100px;\">"
				+ "   L'equipe de GDT-Transport";

	}

	/**
	 * Methode d'envoi de mail utilise l'api mailJet
	 * 
	 * @param emaildestinataire du mail
	 * @param templateMail      template du mail à envoyer
	 * @param sujet             du mail à envoyé
	 */
	private void sendMail(String emaildestinataire, String templateMail, String sujet) {

		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		// 1er: key, 2éme: secret
		client = new MailjetClient(System.getenv("GDTTransport_KEY"), System.getenv("GDTTransport_SECRET"),
				new ClientOptions("v3.1"));
		request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
				new JSONArray().put(new JSONObject()
						.put(Emailv31.Message.FROM,
								new JSONObject().put("Email", "gdttransportentreprise@gmail.com").put("Name",
										"GDTTransport"))
						.put(Emailv31.Message.TO,
								new JSONArray().put(
										new JSONObject().put("Email", emaildestinataire).put("Name", "GDTTransport")))
						.put(Emailv31.Message.SUBJECT, sujet).put(Emailv31.Message.TEXTPART, "My first Mailjet email")
						.put(Emailv31.Message.HTMLPART, templateMail)
						.put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
		try {
			response = client.post(request);
			System.out.println(sujet);

			System.out.println(response.getStatus());
			System.out.println(response.getData());
		} catch (MailjetException | MailjetSocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
