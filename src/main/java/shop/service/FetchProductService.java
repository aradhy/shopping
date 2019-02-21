package shop.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoService;
import shop.model.Product;
@Service
public class FetchProductService {
	
	
	@Autowired
	DaoService daoProductService;
	
	public Product getProduct(Integer id) throws ParseException
	{
		Optional<Product> prodOptional=daoProductService.findByCode(id);
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
	

	public List<Product> getProductByCategory(Integer categoryId) throws ParseException
	{
		List<Product> prodList=daoProductService.findByCategoryId(categoryId);
		return prodList;
		
	}

}
