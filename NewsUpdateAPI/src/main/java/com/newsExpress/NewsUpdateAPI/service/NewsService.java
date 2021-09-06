package com.newsExpress.NewsUpdateAPI.service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.newsExpress.NewsUpdateAPI.bean.NewsArticle;

@Service
public class NewsService {
	
	public List<NewsArticle> loadNews(String uri) {
		List<NewsArticle> newsArticleList = new ArrayList<NewsArticle>();
		try {
			StringBuilder response = new StringBuilder();
	        URL url = new URL(uri);
	        URLConnection urlcon = url.openConnection();
	        InputStream stream = urlcon.getInputStream();
	        int j;
	        while ((j = stream.read()) != -1) {
	            response.append((char) j);
	        }
	        JSONObject newsObject = new JSONObject(response.toString());
	        JSONArray newsArray = newsObject.getJSONArray("articles");
	        for(int i=0; i<newsArray.length(); i++) {
	            JSONObject newsObj = newsArray.getJSONObject(i);
	            NewsArticle newsArticle = new NewsArticle();
	            JSONObject newsSource = newsObj.getJSONObject("source");
	            newsArticle.setSourceName(newsSource.get("name").toString());
	            newsArticle.setTitle(newsObj.get("title").toString());
	            newsArticle.setDescription(newsObj.get("description").toString());
	            newsArticle.setUrl(newsObj.get("url").toString());
	            try {
	            	newsArticle.setAuthor(newsObj.get("author").toString());
	            }catch (Exception e) {
	            	newsArticle.setAuthor("null");
				}
	            try {
	            	newsArticle.setUrlToImage(newsObj.get("urlToImage").toString());
	            }catch (Exception e) {
	            	newsArticle.setUrlToImage(newsObj.get("image").toString());
				}
	            newsArticle.setPublishedAt(newsObj.get("publishedAt").toString());
	            newsArticleList.add(newsArticle);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
        return newsArticleList;
	}

}
