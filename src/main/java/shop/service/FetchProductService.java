package shop.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoService;
import shop.model.Product;
@Service
public class FetchProductService {
	
	
	@Autowired
	DaoService daoProductService;
	
	public Product getProduct(String id) throws ParseException
	{
		Optional<Product> prodOptional=daoProductService.findById(id);
		if(prodOptional.isPresent())
		{
			Product prod=prodOptional.get();
			 return prod;
		}
		return null;
		
	}

	public Product createProduct(Product prod) throws ParseException {
		return daoProductService.save(prod);
	}

}
