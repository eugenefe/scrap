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


public class KrxBondMasterTest {
	private final static Logger logger = LoggerFactory.getLogger(KrxBondMasterTest.class);
	private  static Properties properties = new Properties();
	
	
	public static void main(String[] args) {
		
//		bondMasterTest();
//		bondDivCdTest();
		 for ( KrxBondType aa : jsonTest()){
			 logger.info("Bond: {},{}", aa.getType(), aa.getTypeName());
			 logger.info("Bond: {},{}", aa.getDivType(), aa.getDivTypeName());
		 }
//		 getKrxData();
  }
	
	
	

	/*public static String getKrxData(){
		DefaultHttpClient httpclient = new DefaultHttpClient(); 
		  
	    try
	    { 
	        URIBuilder builder = new URIBuilder("http://www.krx.co.kr/m5/m5_1/m5_1_2/JHPKOR05001_02.jsp");
	        
	        builder.setParameter("isu_id", "KR6029881283"); 
	        

	        URI uri = builder.build(); 
	        HttpGet request = new HttpGet(uri); 
	        HttpResponse response = httpclient.execute(request); 
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) { 
	            System.out.println(EntityUtils.toString(entity)); 
	        	return EntityUtils.toString(entity);
	        }
	        else{
	        	return null;
	        }
	    } 
	    catch(Exception e) 
	    { 
	        System.out.println(e.getMessage());
	        return null;
	    } 
	}*/
	
	private static List<KrxBondType> jsonTest(){
		Document doc;
		String type, typeName = null;
		String divType;
		Class klazz = KrxBondType.class;
		KrxBondType entity ;
		List<KrxBondType> entityList = new ArrayList<KrxBondType>();
		try {
			properties.load(KrxBondMasterTest.class.getResourceAsStream("/url.properties"));
			logger.info("Master: {}", properties.getProperty("krxBondMaster"));
			
			doc = Jsoup.connect(properties.getProperty("krxBondMaster")).get();
//							.data("isu_id", "KR6029881283").post();
			
			Elements _types = doc.select("fieldset>table>tbody>tr>td>label, fieldset>table>tbody>tr>td>span");
//			logger.info("JSON: {}", _types);
			
			for( Element aa : _types){
//				logger.info("JSON: {},{}", aa.tagName(), aa.text());
				if(aa.tagName().equals("label")){
					typeName = aa.text();
//					entity.setTypeName(aa.text());
				}
				else if(aa.tagName().equals("span")){
					for(Element bb : aa.children() ){
						if(bb.tagName().equals("select")){
//							logger.info("JSON1111: {},{}", bb.attr("name"));
							type = bb.attr("name");
//							entity.setType(bb.attr("name"));
//							logger.info("JSON11: {},{}", bb.select("option"));
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
	private static void bondDivCdTest(){
		Document doc;
		String bondTypeCd;
		
		
		try {
			properties.load(KrxBondMasterTest.class.getResourceAsStream("/url.properties"));
			logger.info("Master: {}", properties.getProperty("krxBondMaster"));
			logger.info("Detail: {}", properties.getProperty("krxBondInfo"));
			
			// need http protocol
			doc = Jsoup.connect(properties.getProperty("krxBondMaster")).timeout(1000000).get();
//			doc = Jsoup.connect("http://www.krx.co.kr/por_kor/popup/JHPKOR13008_01.jsp").get();
//			logger.info("Html Size : {}", doc);
//			Elements _types = doc.select("fieldset>table>tbody>tr>td>input[type=radio] ");
			Elements _types = doc.select("fieldset>table>tbody>tr>td ");
//			Elements _types = doc.select("fieldset>table td >input[type =radio]") ;
			
//			JSONPObject json = (JSONPObject)new XMLSerializer().read(_types.toString());
			
//			logger.info("JSON: {}", json);
			
			for( Element bondType : _types){
				if(bondType.nodeName().equals("BondGubun")){
					
				}
//				logger.info("Html Size : {}, {}", _types.size(), bondType.toString());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void bondMasterTest(){
		Document doc;
		
		try {
			properties.load(KrxBondMasterTest.class.getResourceAsStream("/url.properties"));
			logger.info("Master: {}", properties.getProperty("krxBondMaster"));
			logger.info("Detail: {}", properties.getProperty("krxBondInfo"));
			
			// need http protocol
//			doc = Jsoup.connect(properties.getProperty("krxBondMaster"))
			doc = Jsoup.connect("http://www.krx.co.kr/por_kor/popup/JHPKOR13008_01.jsp")
//					.get();
						.data("charOrder", "0")
						.data("sqlGubun", "sel")
						.timeout(1000000)
//						.userAgent("Mozilla")
//						.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
						.post();
		    
//			doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
//			doc.outputSettings().escapeMode(Entities.EscapeMode.base);
//			doc.select("th:matchesOwn((?is) )").remove();
//			doc.select("td:matchesOwn((?is) )").remove();
			
			Elements _rows = doc.select("table[id = tbl1]> tbody>tr");
			
			System.out.println("Table : "+ _rows);
//			
			logger.info("Html Size : {}", _rows.size());
			
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
