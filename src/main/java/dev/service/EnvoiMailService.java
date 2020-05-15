package dev.service;

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

@Service
public class EnvoiMailService {

	private AnnonceService annonceservice;
	private ReservationService reservationService;

	/**
	 * Constructeur
	 * 
	 * @param annonceservice
	 * @param reservationService
	 */
	public EnvoiMailService(AnnonceService annonceservice, ReservationService reservationService) {
		super();
		this.annonceservice = annonceservice;
		this.reservationService = reservationService;
	}

	public void mailAnnulationAnnonce(String emailpassager) {
		
		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		// 1er: key, 2éme: secret
		client = new MailjetClient(System.getenv("GDTTransport_KEY"), System.getenv("GDTTransport_SECRET"),
				new ClientOptions("v3.1"));
		request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, new JSONArray().put(new JSONObject()
				.put(Emailv31.Message.FROM,
						new JSONObject().put("Email", "gdttransportentreprise@gmail.com").put("Name", "GDTTransport"))
				.put(Emailv31.Message.TO,
						new JSONArray().put(new JSONObject().put("Email", emailpassager)
								.put("Name", "GDTTransport")))
				.put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
				.put(Emailv31.Message.TEXTPART, "My first Mailjet email")
				.put(Emailv31.Message.HTMLPART,
						"<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")
				.put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
		try {
			response = client.post(request);
			System.out.println(response.getStatus());
			System.out.println(response.getData());
		} catch (MailjetException | MailjetSocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
