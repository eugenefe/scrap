package com.eugenefe.jsouptest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenfe.jsoup.Walton;

public class WaltonPageTest {
	private final static Logger logger = LoggerFactory.getLogger("WaltonPageTest");
	private  static Properties properties = new Properties();
	private static Map<String,String> propMap = new HashMap<String, String>();
	private static List<String> propDetailList = new ArrayList<String>();
	private static List<Walton> rtn = new ArrayList<Walton>();
	
	
	public static void main(String[] args) {
//		waltonMaster();
//		for(Map.Entry<String, String> entry : propMap.entrySet()){
//			waltonDetail(entry.getValue());
//			for( String aa : propDetailList){
//				logger.info("AAA:{},{}", aa);
//				rtn.add(waltonDetailInfo(aa));
//			}
//			
//		}
//		for(Walton zz : rtn){
//			logger.info("AAA:{},{}", zz.getOwnerName(), zz.getMarketValue());
//		}
//		waltonDetailInfo("32-2S-21-42030-000-00J0");
//		waltonDetailInfo("32-2S-21-42030-000-00A0");
			waltonDetail("28-3S-18-1630");
			writeFile(propDetailList);
	}

	private static void waltonMaster(){
		Document doc;
		String url;
		try {
			
			properties.load(KrxBondInfoTest.class.getResourceAsStream("/walton.properties"));
			url =properties.getProperty("condMaster");
			logger.info("AAA: {}", url);
			
			// need http protocol
			doc = Jsoup.connect(url)
					   .timeout(2000000)
					   .get();
//						.data("isu_id", "KR6029881283").post();
//			logger.info("Test:{}", doc);
			
			Elements _rows = doc.select("table[id = myTable]>tbody>tr");
//			Elements _rows = doc.select("table#myTable");
			logger.info("Chile Size : {},{}" , _rows.size(), _rows.get(0).text());
			
			for( Element _row : _rows){
//				logger.info("Child Size : {},{}" , _row.children().size(), _row.text());
				propMap.put(_row.child(0).text(), _row.child(1).text());
			}
				
					
			for(Map.Entry<String, String> entry : propMap.entrySet()){
				logger.info("OutPut: {},{}", entry.getKey(), entry.getValue().substring(0, 13));
			}
			// get page title
			String title = doc.title();
			System.out.println("title : " + title);
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeFile(List<String> propList){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("walton.txt", true));
			for( String aa : propDetailList){
				logger.info("AAA:{},{}", aa);
				out.append(waltonDetailInfo(aa).getOwnerName()+"\t");
				out.append(waltonDetailInfo(aa).getMailAddress()+"\t");
				out.append(waltonDetailInfo(aa).getMailAddress1()+"\t");
				out.append(waltonDetailInfo(aa).getLacationAddress()+"\t");
				out.append(waltonDetailInfo(aa).getParcelNo()+"\t");
				out.append(waltonDetailInfo(aa).getMarketValue()+"\t");
				out.newLine();
			}
			out.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void waltonDetail(String detailUrl){
		Document doc;
		String url;
		propDetailList = new ArrayList<String>();
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("walton.txt", true));
			properties.load(KrxBondInfoTest.class.getResourceAsStream("/walton.properties"));
			url =properties.getProperty("condDetail");
			logger.info("AAA: {}", url);
			
			// need http protocol
			doc = Jsoup.connect(url)
					   .timeout(2000000)
					   .data("INPUT", detailUrl)
					   .get();
//						.data("INPUT", "KR6029881283").post();
//			logger.info("Test:{}", doc);
			
			Elements _rows = doc.select("table[class = table_class]>tbody>tr>td[class =search_value]>a[href*=KEY]");
//			logger.info("Chile Size : {},{}" , _rows.size(), _rows.get(0).text());
			
			for( Element _row : _rows){
//				logger.info("Chile Size00 : {},{}" , _row.text(), _row.select("href"));
				propDetailList.add(_row.text());
			}

			String title = doc.title();
//			logger.info("Doc Title : {},{}" , title);
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String aa: propDetailList){
//			logger.info("Detail List : {},{}" , aa);
		}
	}
	
	
	private static Walton waltonDetailInfo(String detailUrl){
		Document doc;
		String url;
		String oName =null;
		String mAdd=null;
		String mAdd1 =null;
		String mAdd2;
		String lAdd =null;
		String pNo =null;
		String price =null;
		String temp =null;
		Walton walton =null;
		
		int len;
		propDetailList = new ArrayList<String>();
		
		try {
			
			properties.load(KrxBondInfoTest.class.getResourceAsStream("/walton.properties"));
			url =properties.getProperty("condInfo");
//			logger.info("AAA: {}", url);
			
			// need http protocol
			doc = Jsoup.connect(url)
					   .timeout(2000000)
					   .data("KEY", detailUrl)
					   .get();
//			logger.info("Test:{}", doc);
			
			Elements _rows = doc.select("table[class = table_class]>tbody");
//			logger.info("Chile Size : {},{}" , _rows.size(), _rows.get(0).text());
			Element tbl1 = _rows.get(2);
			Element tbl2 = _rows.get(4);

			for( Element _row : tbl1.children()){
//				logger.info("Chile Size1 : {},{}" , _row.text());
				len = _row.children().size();
				for( int i=0 ; i<len-1; i=i+2){
					temp = _row.child(i).text().replace("\u00a0","");
//					logger.info("Chile Size1 : {},{}" ,temp, _row.child(i).text());
//					_row.child(i).text().trim()
					if(temp.equals("Owner Name" )){
						oName = _row.child(i+1).text().replace("\u00a0","");
						
					}
					if(temp.equals("Mailing Address " )){
						mAdd = _row.child(i+1).text().replace("\u00a0","");
//						logger.info("Chile Size1111 : {},{}" ,mAdd, _row.child(i+1).text());
					}
					if(temp.equals("" )){
						mAdd1 = _row.child(i+1).text().replace("\u00a0","");
					}
					if(temp.equals("Location Address" )){
						lAdd = _row.child(i+1).text().replace("\u00a0","");
					}
					if(temp.equals("Parcel Number" )){
						pNo = _row.child(i+1).text().replace("\u00a0","");
					}
				}
				
			}			

			for(Element _row: tbl2.children()){
//				logger.info("Chile Size1 : {},{}" , _row.text());
				len = _row.children().size();
				for( int i=0 ; i<len; i=i+2){
					temp = _row.child(i).text().replace("\u00a0","");
//					logger.info("Chile Size1 : {},{}" ,i, _row.child(i).text());
					if(temp.equals("Just (Market) Value*" )){
						price = _row.child(i+1).text().replace("\u00a0","");
					}
				}
			}
		      
			walton = new Walton(oName, mAdd, mAdd1, lAdd, pNo, price);
			String title = doc.title();
//			logger.info("Doc Title : {},{}" , title);
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
//		logger.info("Info: {},{}", walton.getOwnerName(), walton.getLacationAddress());
//		logger.info("Info: {},{}", walton.getMailAddress(), walton.getMailAddress1());
//		logger.info("Info: {},{}", walton.getParcelNo(), walton.getMarketValue());
		return walton;
	}
}
