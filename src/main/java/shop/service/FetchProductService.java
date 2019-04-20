package shop.service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoProductService;
import shop.dto.ProductDTO;
import shop.model.Category;
import shop.model.Product;
import shop.model.SubCategory;


@Service
public class FetchProductService {

	@Autowired
	DaoProductService daoProductService;

	public Product getProduct(String id) throws ParseException {
		Product prod = daoProductService.findByCode(id);
		return prod;

	}

	public Product createProduct(Product prod) throws ParseException {
		return daoProductService.save(prod);
	}

	public List<Product> getProductBySubCategory(String categoryId) throws ParseException {
		List<Product> prodList = daoProductService.findBySubId(categoryId);

		return prodList;

	}

	public List<Product> getProductByName(String productName) throws ParseException {
		List<Product> prodList = daoProductService.findByProductName(productName);
		return prodList;

	}

	public List<Product> getProductBySubCategoryName(String subCategoryName) throws ParseException {
		SubCategory subCategory = daoProductService.findSubCategoryDetails(subCategoryName);
		List<Product> productList = null;
		if (subCategory != null) {
			productList = subCategory.getProductList();
		}
		return productList;

	}

	public Set<Product> getProductByCategoryName(String categoryName) throws ParseException {
		Category category = daoProductService.findCategoryDetails(categoryName);
		Set<Product> productList = new HashSet<Product>();
		if (category != null) {

			category.getSubCategory().forEach(s -> productList.addAll(s.getProductList()));
		}
		return productList;

	}

	public List<Product> getProductByBrand(String brandName) {
		List<Product> productList = daoProductService.findProductByBrand(brandName);
		return productList;

	}
	
	public List<ProductDTO> getProduct(List<String> productIdList)  {
		List<ProductDTO> prodList = daoProductService.findAllById(productIdList);
		return prodList;

	}

	public List<ProductDTO> getProductByCategory(String categoryId)
	{
		return daoProductService.findProductByCategory(categoryId);
	}
	
}
