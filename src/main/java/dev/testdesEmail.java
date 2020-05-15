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

public class testdesEmail {
	/**
	   * This call sends a message to the given recipient with vars and custom vars.
	   */
    public static void main(String[] args) throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(System.getenv("GDTTransport_KEY"), System.getenv("GDTTransport_SECRET"), new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
  			.property(Emailv31.MESSAGES, new JSONArray()
                  .put(new JSONObject()
                      .put(Emailv31.Message.FROM, new JSONObject()
                          .put("Email", "gdttransportentreprise@gmail.com")
                          .put("Name", "GDTEntreprise"))
                      .put(Emailv31.Message.TO, new JSONArray()
                          .put(new JSONObject()
                              .put("Email", "jules.dupuis51@gmail.com")
                              .put("Name", "GDTEntreprise")))
                      .put(Emailv31.Message.TEMPLATEID, "1414181")
                      .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                      .put(Emailv31.Message.SUBJECT, "Your email flight plan!")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
      }

}
