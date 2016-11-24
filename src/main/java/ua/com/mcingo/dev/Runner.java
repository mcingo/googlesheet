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


    public static void main(String[] args) throws Throwable,IOException {
        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory JSON_FACTORY =                JacksonFactory.getDefaultInstance();

        InputStream resourceAsStream = Runner.class.getClassLoader().getResourceAsStream("ProjectSheets-afcedd1485e6.json");
        GoogleCredential credential = GoogleCredential.fromStream(resourceAsStream).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
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
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s \n", row.get(0));
            }
        }


    }
}
