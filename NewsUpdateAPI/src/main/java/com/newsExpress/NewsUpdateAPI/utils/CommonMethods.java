package com.newsExpress.NewsUpdateAPI.utils;

public interface CommonMethods {
	
	static final String API_KEY_NEWSAPI = "13bc65939b12402da7e12f54e6d9bf65";
	static final String TOKEN_GNEWS_API = "b59f49462c99cd9933b403ac3db03239";
	
	public static String getNewsAPIUrl(String category, String country) {
		String url = "";
		if(country.equalsIgnoreCase("in")) {
			if(category.equalsIgnoreCase("topHeadlines")) {
				url = "https://newsapi.org/v2/top-headlines?country="+country.toLowerCase()+"&apiKey="+API_KEY_NEWSAPI;
			}else {
				url = "https://newsapi.org/v2/everything?q=" + category +
		                "&sources=the-hindu,news24,google-news-in,the-times-of-india" +
		                "&sortBy=publishedAt" +
		                "&language=en" +
		                "&apiKey="+API_KEY_NEWSAPI;
			}
		}
		return url;
	}
	
	public static String getGNewsAPIUrl(String category, String country) {
		String url = "";
		if(category.equalsIgnoreCase("topHeadlines")) {
			url = "https://gnews.io/api/v4/top-headlines?"
					+ "topic=breaking-news"
					+ "&country="+country.toLowerCase()+"&lang=en"
					+ "&token="+TOKEN_GNEWS_API;
		}else {
			url = "https://gnews.io/api/v4/search?q="+ category
					+ "&lang=en"
					+ "&country=" + country.toLowerCase()
					+ "&token="+TOKEN_GNEWS_API;
		}
		return url;
	}

}
