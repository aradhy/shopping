package shop.util;


import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
@Service
public class Utility {
	
	
	
	public ResponseEntity<Object> getRequest(String url)
	{
		
		RestTemplate restTemplate=new RestTemplate();
	return	restTemplate.getForEntity(url,Object.class);
	}
	
	
	
	public Map<String, String> getImageLinks(List<String> imageIds)
	{
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/images");
		builder.queryParam("imagesList", String.join(",", imageIds));
		URI uri = builder.build().encode().toUri();
		 ResponseEntity<Object> response=	getRequest(uri.toString());
		 Map<String, String> map= (Map<String, String>)response.getBody();
		 
		 return map;
	}
	
	
	
	

}
