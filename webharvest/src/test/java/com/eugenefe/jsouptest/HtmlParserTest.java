package com.eugenefe.jsouptest;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParserTest {

	public static void main(String[] args) {
 
//		googleTest();
//		yahooTest();
//		metaTest();
		faviconTest();
 
  }
	
	private static void googleTest(){
		Document doc;
		try {
	 
			// need http protocol
			doc = Jsoup.connect("http://google.com").get();
	 
			// get page title
			String title = doc.title();
			System.out.println("title : " + title);
	 
			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {
	 
				// get the value from href attribute
				System.out.println("\nlink : " + link.attr("href"));
				System.out.println("text : " + link.text());
	 
			}
			System.out.println("text : 1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void yahooTest(){
		Document doc2;
		try {
	 
			//get all images
			doc2 = Jsoup.connect("http://yahoo.com").get();
			Elements images = doc2.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			for (Element image : images) {
	 
				System.out.println("\nsrc : " + image.attr("src"));
				System.out.println("height : " + image.attr("height"));
				System.out.println("width : " + image.attr("width"));
				System.out.println("alt : " + image.attr("alt"));
	 
			}
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void metaTest(){
		StringBuffer html = new StringBuffer();
		 
		html.append("<!DOCTYPE html>");
		html.append("<html lang=\"en\">");
		html.append("<head>");
		html.append("<meta charset=\"UTF-8\" />");
		html.append("<title>Hollywood Life</title>");
		html.append("<meta name=\"description\" content=\"The latest entertainment news\" />");
		html.append("<meta name=\"keywords\" content=\"hollywood gossip, hollywood news\" />");
		html.append("</head>");
		html.append("<body>");
		html.append("<div id='color'>This is red</div> />");
		html.append("</body>");
		html.append("</html>");
	 
		Document doc = Jsoup.parse(html.toString());
	 
		//get meta description content
		String description = doc.select("meta[name=description]").get(0).attr("content");
		System.out.println("Meta description : " + description);
	 
		//get meta keyword content
		String keywords = doc.select("meta[name=keywords]").first().attr("content");
		System.out.println("Meta keyword : " + keywords);
	 
		String color1 = doc.getElementById("color").text();
		String color2 = doc.select("div#color").get(0).text();
	 
		System.out.println(color1);
		System.out.println(color2);
	 
	}
	
	private static  void faviconTest(){
		StringBuffer html = new StringBuffer();
		 
		html.append("<html lang=\"en\">");
		html.append("<head>");
		html.append("<link rel=\"icon\" href=\"http://example.com/image.ico\" />");		
		//html.append("<meta content=\"/images/google_favicon_128.png\" itemprop=\"image\">");
		html.append("</head>");
		html.append("<body>");
		html.append("something");
		html.append("</body>");
		html.append("</html>");
	 
		Document doc = Jsoup.parse(html.toString());
	 
		String fav = "";
	 
		Element element = doc.head().select("link[href~=.*\\.(ico|png)]").first();
		if(element==null){
	 
			element = doc.head().select("meta[itemprop=image]").first();
			if(element!=null){
				fav = element.attr("content");
			}
		}else{
			fav = element.attr("href");
		}
		System.out.println(fav);
	}
}
