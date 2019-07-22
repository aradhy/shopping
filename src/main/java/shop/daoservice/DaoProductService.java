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
	
	
	@Query(value="select new shop.dto.ProductDTO(prod_avail.id,prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod left join ProductAvail prod_avail on prod.code=prod_avail.productId  where prod.code=:code")
    List<ProductDTO> findByProductCode(@Param("code") String code);

	//@Query(value="select new shop.dto.ProductDTO(prod_avail.id,prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod left join ProductAvail prod_avail on prod.code=prod_avail.productId join SubCategory subCat on prod.subId=subCat.id where subCat.id=:subCategoryId")
	@Query(value="select prod from Product prod  join  ProductAvail prod_avail on prod.code=prod_avail.productId where prod.subId=:subCategoryId")
	List<Product> findBySubId(@Param("subCategoryId") String subCategoryId);
	
	@Query(value="select prod from shop.model.Product prod join shop.model.SubCategory sub  on prod.subId=sub.id join shop.model.Category cat on sub.categoryId=cat.id join shop.model.ProductAvail prodAvail on prod.code=prodAvail.productId where (soundex(prod.brand)=soundex(:productName) or :productName like  CONCAT('%', prod.brand,'%')) or (soundex(prod.name)=soundex(:productName) or :productName like  CONCAT('%', prod.name,'%')) or (soundex(sub.name)=soundex( :productName)\r\n" + 
			" or :productName like  CONCAT('%', sub.name,'%')) or (soundex(cat.name)=soundex(:productName) or :productName like  CONCAT('%',  cat.name,'%') )  ORDER BY \r\n" + 
			"    (CASE\r\n" + 
			"        WHEN (locate(prod.brand,:productName)>0 and locate(prod.name,:productName)<0)THEN prod.name\r\n" + 
			"  WHEN (locate(prod.brand,:productName)>0 and locate(prod.name,:productName)>0)THEN 1 "
			+ "ELSE  prod.name \r\n" + 
			"    END)")
	List<Product> findByProductName(@Param("productName") String productName);
	@Query(value="select sub from SubCategory sub  where sub.name like %:subCategoryName%")
	SubCategory findSubCategoryDetails( @Param("subCategoryName") String subCategoryName);
	
	@Query(value="select cat from Category cat  where cat.name like %:categoryName%")
	Category findCategoryDetails( @Param("categoryName") String categoryName);

	@Query(value="select prod from Product prod  where prod.brand like %:brandName%")
	List<Product> findProductByBrand(@Param("brandName") String brandName);
	
	@Query(value="select new shop.dto.ProductDTO(prod_avail.id,prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod join ProductAvail prod_avail on prod.code=prod_avail.productId where prod.code in(:ids)")
	List<ProductDTO> findAllById(@Param("ids") List<String> ids);
	
	@Query(value="select new shop.dto.ProductDTO(prod_avail.id,prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod  join ProductAvail prod_avail on prod.code=prod_avail.productId join SubCategory subCat on prod.subId=subCat.id join Category cat on subCat.categoryId=cat.id where cat.id=:categoryId")
	List<ProductDTO> findProductByCategory( @Param("categoryId") String categoryId);

	@Query(value="select new shop.dto.ProductDTO(prod_avail.id,prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod join ProductAvail prod_avail on prod.code=prod_avail.productId where prod_avail.id in(:ids)")
	List<ProductDTO> findAllByProductAvail(@Param("ids") List<String> productAvailList);

	@Query(value="select new shop.dto.ProductDTO(prod_avail.id,prod.code,prod.name,prod.brand,prod.imageId,prod_avail.price,prod_avail.weight,prod_avail.weightUnit) from Product prod left join ProductAvail prod_avail on prod.code=prod_avail.productId  where prod.code=:productCode and prod_avail.id=:prodAvailId")
	ProductDTO findByProductCodeAndAvail(@Param("productCode") String productCode,@Param("prodAvailId") String prodAvailId);


	/*List<Product> findByProductAvailListWeightUnitAndProductAvailListWeightBetween(@Param("startWeight") Integer startWeight,@Param("endWeight") Integer endWeight ,@Param("weightUnit") String weightUnit);
	
	List<Product> findByProductAvailListWeightBetweenAndProductAvailListWeightUnitAndSubId(@Param("startWeight") Integer startWeight,@Param("endWeight") Integer endWeight ,@Param("weightUnit") String weightUnit,@Param("subId") String subId);
	
	List<Product> findByProductAvailListWeightLessThanAndWeightUnitAndSubId(@Param("weight") Integer weight,@Param("weightUnit") String weightUnit,@Param("subId") String subId);

	List<Product> findByProductAvailListWeightGreaterThanAndWeightUnitAndSubId(@Param("weight") Integer weight,@Param("weightUnit") String weightUnit,@Param("subId") String subId);
	
	*/

}
