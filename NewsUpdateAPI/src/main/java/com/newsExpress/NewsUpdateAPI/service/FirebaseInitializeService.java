package com.newsExpress.NewsUpdateAPI.service;

import java.io.FileInputStream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class FirebaseInitializeService {
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initialize(){
		try {
			FileInputStream serviceAccount =
					  new FileInputStream("./serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .build();

					FirebaseApp.initializeApp(options);
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
