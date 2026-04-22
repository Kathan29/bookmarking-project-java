package entities;

import constants.KidFriendlyStatus;
import partners.Sharable;

public class Weblink extends Bookmark implements Sharable{
	private String imageUrl;
	private String url;
	private String host;
	
	private Weblink() {}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	
	public static Weblink newInstance(long id, String title,String imageUrl, String url, String host, KidFriendlyStatus kidFriendlyStatus) {
		
		Weblink weblink = new Weblink();
		
		weblink.setId(id);
		weblink.setTitle(title);
		weblink.setImageUrl(imageUrl);
		weblink.setUrl(url);
		weblink.setHost(host);
		weblink.setKidFriendlyStatus(kidFriendlyStatus);
		
		return weblink;
	}
	
	

	@Override
	public String toString() {
		return "Weblink [url=" + url + ", host=" + host + "]";
	}

	@Override
	public boolean isKidFriendly() {
		if(url.contains("porn") || getTitle().contains("porn") || host.contains("adult"))
			return false;
		return true;
	}

	@Override
	public String getItemData() {
		
		StringBuilder xmlData = new StringBuilder();
		
		xmlData.append("<item>");
			xmlData.append("<type>Weblink</type>");
			xmlData.append("<title>"+getTitle()+"</title>");
			xmlData.append("<url>"+url+"</url>");
			xmlData.append("<host>"+host+"</host>");
		xmlData.append("</item>");	
			
		return xmlData.toString();	
	}

}
