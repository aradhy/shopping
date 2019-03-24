package shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.service.ImageFetchService;

@RestController
public class ImageFetchController {

	@Autowired
	ImageFetchService imageFetchService;

	@RequestMapping("/images")
	public Map<String, String> getImages(@RequestParam("imagesList") List<String> imagesList)  {
		Map<String, String> map= imageFetchService.getImages(imagesList);
		
		return map;
	}
	
	


}
