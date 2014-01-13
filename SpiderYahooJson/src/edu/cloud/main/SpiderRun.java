package edu.cloud.main;

import edu.cloud.run.HtmlParserId;


public class SpiderRun {
    public static void main(String argsp[]){
    	
    	//��ȡyahoopipes�ϵ����� pipeid�Ͷ�Ӧ��json�ļ�
        System.setProperty("http.proxySet", "true"); 
        System.setProperty("http.proxyHost", "127.0.0.1"); 
        System.setProperty("http.proxyPort", "8087"); //goagent�˿� ,�������       
        HtmlParserId html = new HtmlParserId();
        
        //popular
   	 	System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/pipes.popular","utf-8"));
   	 	int i = 2;
       	while(html.getHtmlContent("http://pipes.yahoo.com/pipes/pipes.popular?page="+i,"utf-8").size()!=0){
       		System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/pipes.popular?page="+ i,"utf-8"));
       		System.out.println("����ҳ���ǣ�popular"+"��"+i+"ҳ");
       		if(i == 50){
       			try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
       		}
       		i++;
       	}
       	
       	//Formats
       	String []idFormats = new String[]{"csv","georss","media","media-application","media-audio","media-image","mediaapplication","mediaaudio","mediaimage","mediavideo"};
      	for(int j = 0; j<idFormats.length; j++){
      		System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?r=format:"+idFormats[j],"utf-8"));
      		int k = 2;
      		while(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=format%3A"+idFormats[j],"utf-8").size()!=0){
      			System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=format%3A"+idFormats[j],"utf-8"));
      			System.out.println("����ҳ���ǣ�formats��"+idFormats[j]+"��"+k+"ҳ");
           		if(k == 50){
           			try {
    					Thread.sleep(300000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
           		}
      			k++;
      		}
      	}     	
      	
      	//Tags
      	String []idTags = new String[]{"blog","business","feed","flickr","google","music","news","rss","search","twitter"};
      	for(int j = 0; j<idTags.length; j++){
      		System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?r=tag:"+idTags[j],"utf-8"));
      		int k = 2;
      		while(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=tag%3A"+idTags[j],"utf-8").size()!=0){
      			System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=tag%3A"+idTags[j],"utf-8"));
      			System.out.println("����ҳ���ǣ�tags��"+idTags[j]+"��"+k+"ҳ");
           		if(k == 50){
           			try {
    					Thread.sleep(300000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
           		}
      			k++;
      		}
      	}  	
      	
      	//Sources
      	String []idSources = new String[]{"api.flickr.com","blogspot.com","feedburner.com","feeds.feedburner.com","flickr.com","google.com","news.google.com","search.yahoo.com","twitter.com","yahoo.com"};
      	for(int j = 0; j<idSources.length; j++){
      		System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?r=source:"+idSources[j],"utf-8"));
      		int k = 2;
      		while(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=source%3A"+idSources[j],"utf-8").size()!=0){
      			html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=source%3A"+idSources[j],"utf-8");
      			System.out.println("����ҳ���ǣ�sources��"+idSources[j]+"��"+k+"ҳ");
           		if(k == 50){
           			try {
    					Thread.sleep(300000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
           		}
      			k++;
      		}
      	}
      	
      	//Modules
      	String []idModules = new String[]{"fetch","filter","loop","regex","rename","sort","textinput","truncate","union","uniq"};
      	for(int j = 0; j<idModules.length; j++){
      		System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?r=module:"+idModules[j],"utf-8"));
      		int k = 2;
      		while(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=module%3A"+idModules[j],"utf-8").size()!=0){
   	  		System.out.println(html.getHtmlContent("http://pipes.yahoo.com/pipes/search?page="+ k +"&r=module%3A"+idModules[j],"utf-8"));
   	  		System.out.println("����ҳ���ǣ�modules��"+idModules[j]+"��"+k+"ҳ");
       		if(k == 50){
       			try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
       		}
   	  		k++;
      		}
      	}

    }
}
