package com.eugenefe.jsouptest;

import java.io.IOException;
import java.util.Properties;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.MatchesOwn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KrxBondInfoTest {
	private final static Logger logger = LoggerFactory.getLogger(KrxBondInfoTest.class);
	private  static Properties properties = new Properties();
	
	
	public static void main(String[] args) {
		
//		googleTest();
//		yahooTest();
//		metaTest();
		bondInfoTest();
		 
  }
//	private static void loadProperties(){
//		try {
//		      properties.load(new FileInputStream("C:/AAwork/workspace/jsoup/src/main/resources/url.properties"));
//		} catch (IOException e) {
//		}
//	}
	
	private static void bondInfoTest(){
		Document doc;
		
		try {
			properties.load(KrxBondInfoTest.class.getResourceAsStream("/url.properties"));
			logger.info("AAA: {}", properties.getProperty("krxBondInfo"));
			
			// need http protocol
			doc = Jsoup.connect(properties.getProperty("krxBondInfo"))
//			doc = Jsoup.connect("http://www.krx.co.kr/m5/m5_1/m5_1_2/JHPKOR05001_02.jsp")
						.data("isu_id", "KR6029881283").post();
//			doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
//			doc.outputSettings().escapeMode(Entities.EscapeMode.base);
//			doc.select("th:matchesOwn((?is) )").remove();
//			doc.select("td:matchesOwn((?is) )").remove();
			
			Elements _rows = doc.select("table[id = tbl1]> tbody>tr");
//			System.out.println("Table : "+ _rows);
			
			for( Element _row : _rows){
//				System.out.println("Row11: "+ _row.childNodeSize());
//				for(Node  _node : _row.childNodes()){
//					System.out.println("Row : "+ _node.nodeName());
//				}
				
//				logger.info("Row1: {}, {}", _row, _row.children().size());
				if( _row.children().size()== 4){
					for(int i=0; i<_row.children().size(); i=i+2){
//						_row.children().get(i).select("th:matchesOwn(?is)").remove();
//						_row.children().get(i+1).select("td:matchesOwn(?is)").remove();
						if( !_row.children().get(i).text().contains("\u00a0")){;
//						if( _row.children().get(i).text().replace("&nbsp;","")!=""){;
							logger.info("Row1: {}: {}", _row.children().get(i).text(), _row.children().get(i+1).text());
						}
					}
					for( Element _rowCont : _row.children()){
//						logger.info("Row1: {}, {}", _rowCont.text());
					}	
				}
					
				
//					System.out.println("Row : "+ _rowCont.nodeName());
			}
				
					
//					System.out.println("Row : "+ aa.children() );
//				Elements colName = aa.select("th[class != tac]");
//				if( colName.size()>0){
//					System.out.println("Row : "+ colName+ colValue );
//				}
//				Elements colValue = aa.select("td:eq(0)");
//				System.out.println("Row : "+  colValue);
//				System.out.println("Row : "+ colName +"_" + colValue);
				
//			}
			// get page title
			String title = doc.title();
			System.out.println("title : " + title);
	 
			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {
	 
				// get the value from href attribute
//				System.out.println("\nlink : " + link.attr("href"));
//				System.out.println("text : " + link.text());
//	 
			}
			System.out.println("text : 1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
