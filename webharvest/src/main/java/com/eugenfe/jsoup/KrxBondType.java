package com.eugenfe.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.eugenefe.jsouptest.KrxBondTypeTest;

public class KrxBondType {

	private String type;
	private String typeName;
	private String divType;
	private String divTypeName;
	
	public KrxBondType() {
	}

	public KrxBondType(String type, String typeName, String divType, String divTypeName) {
		this.type = type;
		this.typeName = typeName;
		this.divType = divType;
		this.divTypeName = divTypeName;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDivType() {
		return divType;
	}
	public void setDivType(String divType) {
		this.divType = divType;
	}
	public String getDivTypeName() {
		return divTypeName;
	}
	public void setDivTypeName(String divTypeName) {
		this.divTypeName = divTypeName;
	}
	
	
	
	
	public static String getResourceUrl(){
		Properties properties = new Properties();
		try {
			properties.load(KrxBondTypeTest.class.getResourceAsStream("/url.properties"));
		}catch(IOException e) {
			e.printStackTrace();
			
		}
		return properties.getProperty("krxBondMaster");
	}
	
	
	public static List<KrxBondType> loadDataFromUrl(){
		Document doc;
		String type, typeName = null;
		String divType;
		KrxBondType entity ;
		List<KrxBondType> entityList = new ArrayList<KrxBondType>();
		Properties properties = new Properties();
		
		try {
//			TODO : Change to message injection
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
