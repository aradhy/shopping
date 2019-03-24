package shop.service;

import java.util.List;
import java.util.Map;

public interface  ImageFetchService {
	
	Map<String,String> getImages(List<String> imageIds);


}
