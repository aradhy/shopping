package shop.daoservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.dto.ProductDTO;
import shop.model.Category;
import shop.model.Product;
import shop.model.SubCategory;

@Repository
public interface DaoProductService extends JpaRepository<Product,String> {
	
	
	@Query(value="select new shop.dto.ProductDTO(prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod left join ProductAvail prod_avail on prod.code=prod_avail.productId  where prod.code=:code")
    ProductDTO findByCode(@Param("code") String code);


	List<Product> findBySubId(String subCategoryId);
	
	@Query(value="select prod from Product prod where prod.name like %:productName%")
	List<Product> findByProductName(@Param("productName") String productName);
	
	@Query(value="select sub from SubCategory sub  where sub.name like %:subCategoryName%")
	SubCategory findSubCategoryDetails( @Param("subCategoryName") String subCategoryName);
	
	@Query(value="select cat from Category cat  where cat.name like %:categoryName%")
	Category findCategoryDetails( @Param("categoryName") String categoryName);

	@Query(value="select prod from Product prod  where prod.brand like %:brandName%")
	List<Product> findProductByBrand(@Param("brandName") String brandName);
	
	@Query(value="select new shop.dto.ProductDTO(prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod join ProductAvail prod_avail on prod.code=prod_avail.productId where prod.code in(:ids)")
	List<ProductDTO> findAllById(@Param("ids") List<String> ids);
	
	@Query(value="select new shop.dto.ProductDTO(prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod left join ProductAvail prod_avail on prod.code=prod_avail.productId join SubCategory subCat on prod.subId=subCat.id join Category cat on subCat.category_id=cat.id where cat.id=:categoryId")
	List<Product> findProductByCategory( @Param("categoryId") String categoryId);
	
	
  
}
