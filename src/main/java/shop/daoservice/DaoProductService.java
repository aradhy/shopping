package shop.daoservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.model.Category;
import shop.model.Product;
import shop.model.SubCategory;

@Repository
public interface DaoProductService extends JpaRepository<Product,String> {
    Product findByCode(String code);


	List<Product> findBySubId(String subCategoryId);
	
	@Query(value="select prod from Product prod where prod.name like %:productName%")
	List<Product> findByProductName(@Param("productName") String productName);
	
	@Query(value="select sub from SubCategory sub  where sub.name like %:subCategoryName%")
	SubCategory findSubCategoryDetails( @Param("subCategoryName") String subCategoryName);
	
	@Query(value="select cat from Category cat  where cat.name like %:categoryName%")
	Category findCategoryDetails( @Param("categoryName") String categoryName);

	@Query(value="select prod from Product prod  where prod.brand like %:brandName%")
	List<Product> findProductByBrand(@Param("brandName") String brandName);
	
	
  
}