package shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:image_config.properties")
public class ShopConfiguration {
	
	@Value( "${images.pack}" )
	private String imagesPack;

	public String getImagesPack() {
		return imagesPack;
	}

	public void setImagesPack(String imagesPack) {
		this.imagesPack = imagesPack;
	}

}
