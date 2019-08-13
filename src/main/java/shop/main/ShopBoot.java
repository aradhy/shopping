package shop.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import shop.model.Product;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "shop.main", "shop.model", "shop.controller", "shop.service", "shop.config",
		"shop.service.impl", "shop.util" })
@EntityScan(basePackageClasses = Product.class)
@EnableJpaRepositories(basePackages = "shop.daoservice")
public class ShopBoot {

	public static void main(String[] args) {
		SpringApplication.run(ShopBoot.class, args);
		
	}


	
}
