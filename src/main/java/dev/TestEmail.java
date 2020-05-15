package dev;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class TestEmail {
	/**
	 * Run:
	 */

	static String emailpassager = "jules.dupuis51@gmail.com";

	static String nomPassager = "Jules";

	static String prenomPassager="Dupuis";

	static String destinationAnnonce= "Niort";

	static String dateAnnonce;

	static String lieuDestination;

	static String mailTemplate = "<h4>Bonjour " + nomPassager + prenomPassager+ ",</h4>"
			+""
			+ "Votre Covoiturage pour "+ destinationAnnonce+", prévue pour "+lieuDestination+" à "+dateAnnonce+", à été annulé. "
			+"<br><br><br>"
			+ "<img src=\"https://cdn.discordapp.com/attachments/705412725098020906/707149807038496778/logo.png\" alt=\"logo\"style=\"width:100px;height:100px;\">"
			+ "   L'equipe de DTA-Transport";

	public static void main(String[] args) throws MailjetException, MailjetSocketTimeoutException {

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
								new JSONArray()
										.put(new JSONObject().put("Email", emailpassager).put("Name", "GDTTransport")))
						.put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
						.put(Emailv31.Message.TEXTPART, "My first Mailjet email")
						.put(Emailv31.Message.HTMLPART, mailTemplate)));
		response = client.post(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
	}

}
