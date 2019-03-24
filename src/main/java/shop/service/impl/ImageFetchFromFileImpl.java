package shop.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;

import shop.config.ShopConfiguration;
import shop.service.ImageFetchService;

@Service
public class ImageFetchFromFileImpl implements ImageFetchService {

	@Autowired
	ShopConfiguration shopConfiguration;

	@Override
	public Map<String, String> getImages(List<String> imageIds) {

		Map<String, String> map = new HashMap<String, String>();
		File folder = new File(shopConfiguration.getImagesPack());

		List<File> fileList = new ArrayList<>(Arrays.asList(folder.listFiles()));

		List<String> fileIds = fileList.stream().map(file -> getImageId(file)).filter(fileId -> imageIds.contains(fileId))
				.collect(Collectors.toList());

		

		for (File file : fileList) {
			if (imageIds.contains(getImageId(file))) {
				map.put(getImageId(file), file.getName());
			}
		}
		return map;
	}

	String getImageId(File file) {

		return Files.getNameWithoutExtension(file.getName());

	}

}
