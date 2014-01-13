package edu.cloud.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.cloud.dao.ServiceDao;
import edu.cloud.service.Service;

public class HtmlParser {
	@SuppressWarnings({ })
	public String getHtmlContent(URL url, String encode){
		StringBuffer contentBuffer = new StringBuffer();
		int responseCode = -1;
		HttpURLConnection con = null;
		String result = null;
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
			while ((str = buffStr.readLine()) != null)
				contentBuffer.append(str);
			inStr.close();
		}catch (IOException e) {
             e.printStackTrace();  
             contentBuffer = null;  
             System.out.println("error: " + url.toString());
		}finally{
			con.disconnect();
		}
        @SuppressWarnings("unused")
		HttpClient httpclient = new DefaultHttpClient();
        Pattern regLi = Pattern.compile("(?s)<li class='pipelistli'>(.*?)<div class='pipelistli_ft'></div></li>");
        Pattern regId = Pattern.compile("href='/pipes/pipe.info\\?_id=(\\w+)'");
        Pattern regUrl = Pattern.compile("href='(/pipes/person.info\\?guid=\\w+)'");
        Pattern regDate = Pattern.compile("<span class='date''>(\\d{2}/\\d{2}/\\d{2})");
        Pattern regNum = Pattern.compile("<span class=\"number\">(\\d*)</span>");
        Matcher liMatcher = regLi.matcher("");
        Matcher idMatcher = regId.matcher("");
        Matcher urlMatcher = regUrl.matcher("");
        Matcher dateMatcher = regDate.matcher("");
        Matcher numMatcher = regNum.matcher("");  
        
        liMatcher.reset(contentBuffer.toString());
        int i = 0;
        while(liMatcher.find()) {
        	result = "successful";
        	String liNode = liMatcher.group(1);
        	idMatcher.reset(liNode);
        	urlMatcher.reset(liNode);
        	dateMatcher.reset(liNode);
        	numMatcher.reset(liNode);
        	Service service = new Service();
        	if(idMatcher.find()) {
        		service.setServiceId(idMatcher.group(1));
        		System.out.println("id = " + idMatcher.group(1));
        	}
        	if(urlMatcher.find()) {
        		service.setServiceAuthor(urlMatcher.group(1));
        		System.out.println("url = " + urlMatcher.group(1));
        	}else{
        		service.setServiceAuthor("");
        	}
        	if(dateMatcher.find()) {
        		service.setServiceDate(dateMatcher.group(1));
        		System.out.println("date = " + dateMatcher.group(1));
        	}
        	if(numMatcher.find()) {
        		service.setServiceNum(numMatcher.group(1));
        		System.out.println("num = " + numMatcher.group(1));
        	}
        	ServiceDao serviceDao = new ServiceDao();
        	serviceDao.addService(service);
        	System.out.println("-------------------------------");
        	i++;
        }
    	 return result;		
	}
	
	public String getHtmlContent(String url, String encode){
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
