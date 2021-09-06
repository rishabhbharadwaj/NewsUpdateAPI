package com.newsExpress.NewsUpdateAPI.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.newsExpress.NewsUpdateAPI.bean.NewsArticle;
import com.newsExpress.NewsUpdateAPI.service.FirebaseService;
import com.newsExpress.NewsUpdateAPI.service.NewsService;
import com.newsExpress.NewsUpdateAPI.utils.CommonMethods;

@RestController
public class NewsController {
	
	@Autowired
	private FirebaseService firebaseService;

	@Autowired
	private NewsService newsService;
	
	public Logger logger = Logger.getLogger("ServiceLogs");
	
	private Map<String, String> dataSet = new HashMap<String, String>();
	
	@GetMapping("/updateNews")
	public void updateNews() {
		init_dataSet();
		try {
			for(Map.Entry<String, String> data : dataSet.entrySet()) {
				String category = data.getKey();
				String country = data.getValue();
				List<NewsArticle> newsArticleList1 = newsService.loadNews(CommonMethods.getNewsAPIUrl(category,country));
				List<NewsArticle> newsArticleList2 = new ArrayList<NewsArticle>();
				if(!category.equals("topHeadlines")) {
					newsArticleList2 = newsService.loadNews(CommonMethods.getGNewsAPIUrl(category, country));
				}
				for(NewsArticle article : newsArticleList1) { 
					firebaseService.saveNewsArticles(article, category, country); 
					logger.log(Level.INFO, "News is added from NewsAPI at : "+ 
							(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()).toString());
				}
				for(NewsArticle article : newsArticleList2) {
					firebaseService.saveNewsArticles(article, category, country);
					logger.log(Level.INFO, "News is added from GnewsAPI at : "+ 
							(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()).toString());
				}
				firebaseService.deleteNewsArticles(category, country);
				logger.log(Level.INFO, "News Deleted from "+category+" category at : "+ 
						(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()).toString());
				logger.log(Level.INFO, "Process completed successfully at : "+ 
						(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()).toString());
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error message is :" + e.getCause());
			logger.log(Level.WARNING, "Some error occured at"+ 
					(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()).toString());
		}
	}		
	
	@GetMapping("/search/{searchItem}")
	public List<NewsArticle> getNews(@PathVariable("searchItem") String searchItem) {
		return newsService.loadNews(CommonMethods.getNewsAPIUrl(searchItem, "in"));
	}
	
	public void init_dataSet() {
		dataSet.put("topHeadlines", "in");
		dataSet.put("trending", "in");
		dataSet.put("coronavirus", "in");
		dataSet.put("business", "in");
		dataSet.put("politics", "in");
		dataSet.put("sports", "in");
		dataSet.put("technology", "in");
		dataSet.put("movies", "in");
		dataSet.put("science", "in");
		dataSet.put("travel", "in");
		dataSet.put("economy", "in");
		dataSet.put("fashion", "in");
		dataSet.put("fitness", "in");
	}
}
