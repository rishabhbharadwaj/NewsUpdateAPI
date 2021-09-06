package com.newsExpress.NewsUpdateAPI.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.newsExpress.NewsUpdateAPI.bean.NewsArticle;

@Service
public class FirebaseService {
	
	public void saveNewsArticles(NewsArticle newsArticle, String category, String country) {
		try {
			Firestore dbFirestore = FirestoreClient.getFirestore();
			@SuppressWarnings("unused")
			ApiFuture<WriteResult> colleApiFuture = dbFirestore.collection(category).
					document(category+country.toUpperCase()).
					collection("News"+country.toUpperCase()).document(newsArticle.getTitle()).
					set(newsArticle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteNewsArticles(String category, String country) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-2);
		Date date = cal.getTime();
		String tillDate = simpleDateFormat.format(date);
		try {
			Firestore dbFirestore = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> future = dbFirestore.collection(category).
					document(category+country.toUpperCase()).
					collection("News"+country.toUpperCase()).whereLessThan("publishedAt", tillDate).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for(DocumentSnapshot document : documents) {
				document.getReference().delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
