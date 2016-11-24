package ua.com.mcingo.dev;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.*;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by it_mb on 23.11.2016.
 */
public class Runner {
    private static HttpTransport HTTP_TRANSPORT;
    private static JsonFactory JSON_FACTORY;
    private static GoogleCredential credential;

    public static void main(String[] args) throws Throwable,IOException {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JSON_FACTORY = JacksonFactory.getDefaultInstance();
        credential = getCredential();
        Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("SpreadSheet").build();
        
        String spreadsheetId = "1ahZG47v1rfKDPX7ef26vwttlMRVp0OSbYkM1tikzt3w";
        String range = "A1:A2";
        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {

            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s \n", row.get(0));
            }
        }


    }
    private static GoogleCredential getCredential() throws Throwable{
        InputStream resourceAsStream = Runner.class.getClassLoader().getResourceAsStream("bitirix-project-cab4f917a7b8.json");
        return GoogleCredential.fromStream(resourceAsStream).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
    }
}
