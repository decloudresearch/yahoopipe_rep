package edu.cloud.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cloud.dao.ServiceDao;
import edu.cloud.service.Service;

public class HtmlParserId {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getHtmlContent(URL url, String encode){
		StringBuffer contentBuffer = new StringBuffer();
		int responseCode = -1;
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");// IE代理进行下载
			con.setConnectTimeout(60000);
			con.setReadTimeout(60000);
			// 获得网页返回信息码
			responseCode = con.getResponseCode();
			if (responseCode == -1){
				System.out.println(url.toString() + " : connection is failure...");
				con.disconnect();
				return null;
			}
			// 请求失败
			if (responseCode >= 400){
				System.out.println("请求网页失败:get response code: " + responseCode);
				con.disconnect();
				return null;
			}
			InputStream inStr = con.getInputStream();
			InputStreamReader istreamReader = new InputStreamReader(inStr, encode);
			BufferedReader buffStr = new BufferedReader(istreamReader);
			String str = null;
			while ((str = buffStr.readLine()) != null)contentBuffer.append(str);
			inStr.close();
		}catch (IOException e) {
             e.printStackTrace();  
             contentBuffer = null;  
             System.out.println("error: " + url.toString());
		}finally{
			con.disconnect();
		}
         //id通过compile（）方法创建Pattern实例,参数为规则； 
         Pattern patternId = Pattern.compile("href='/pipes/pipe.info\\?_id=(\\w+)'");     
         //通过match（）创建Matcher实例
         Matcher matcherId = patternId.matcher(contentBuffer.toString());
         ArrayList idList = new ArrayList();
         while(matcherId.find()){
        	 idList.add(matcherId.group(1));
         }       
         //存入数据库
    	 for(int j=0; j<idList.size();j++){
    		 Service service = new Service();
    		 service.setServiceId((String)idList.get(j));
    		 ServiceDao serviceDao = new ServiceDao();
    		 if(serviceDao.findService((String)idList.get(j))!=1){
    			 if(serviceDao.findService1((String)idList.get(j))!=1){
        			 System.out.println("no find it,so build json!!!!");
        			 HtmlParserJson htmlJson = new HtmlParserJson();
            		 service.setServiceContent(htmlJson.getHtmlContent("http://pipes.yahoo.com/pipes/pipe.info?_out=json&_id="+idList.get(j),"utf-8"));
        			 serviceDao.addServiceIdContent(service); 
    			 }
    		 }
    	 }
    	 return idList;		
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList getHtmlContent(String url, String encode){
		if (!url.toLowerCase().startsWith("http://")){
			url = "http://" + url;
		}
		try {
			URL rUrl = new URL(url);
			return getHtmlContent(rUrl, encode);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
