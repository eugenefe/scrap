package com.eugenefe.jsouptest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



import org.codehaus.jackson.map.util.JSONPObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.MatchesOwn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenfe.jsoup.KrxBondType;


public class KrxBondTypeTest {
	private final static Logger logger = LoggerFactory.getLogger(KrxBondTypeTest.class);
	private  static Properties properties = new Properties();
	
	
	public static void main(String[] args) {
		 for ( KrxBondType aa : loadData()){
			 logger.info("Bond: {},{}", aa.getType(), aa.getTypeName());
			 logger.info("Bond: {},{}", aa.getDivType(), aa.getDivTypeName());
		 }
  }
	
	private static List<KrxBondType> loadData(){
		Document doc;
		String type, typeName = null;
		String divType;
		KrxBondType entity ;
		List<KrxBondType> entityList = new ArrayList<KrxBondType>();
		
		try {
			properties.load(KrxBondTypeTest.class.getResourceAsStream("/url.properties"));
//			logger.info("Master: {}", properties.getProperty("krxBondMaster"));
			
			doc = Jsoup.connect(properties.getProperty("krxBondMaster")).get();
			
			Elements _types = doc.select("fieldset>table>tbody>tr>td>label, fieldset>table>tbody>tr>td>span");
//			logger.info("JSON: {}", _types);
			
			for( Element aa : _types){
//				logger.info("JSON: {},{}", aa.tagName(), aa.text());
				if(aa.tagName().equals("label")){
					typeName = aa.text();
				}
				else if(aa.tagName().equals("span")){
					for(Element bb : aa.children() ){
						if(bb.tagName().equals("select")){
//							logger.info("JSON1111: {},{}", bb.attr("name"));
							type = bb.attr("name");
							for( Element cc : bb.select("option")){
								divType =cc.attr("value");
								if(divType != ""){
//									logger.info("JSON111: {},{}", cc.attr("value"), cc.text());
									entity = new KrxBondType(type, typeName, divType, cc.text());	
									entityList.add(entity);
								}
							}
						}

					}
				}	
			}
			return entityList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entityList;
	}
}
