package shop.daoservice;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.dto.CategoryOnProduct;
import shop.model.Category;

@Repository
public interface DaoCategoryService extends JpaRepository<Category,String> {
	
	@Query(value="select new Category(c.id,c.name) from Category c")
	List<Category>  findCategorySelectedFields(); 

	List<Category>  findAll();
	
	
	Optional<Category>  findById(@Param("catId") String catId);


	
	
	@Query(value="select distinct new shop.dto.CategoryOnProduct(cat.id,cat.name,sub.id,sub.name) from shop.model.Product prod join shop.model.SubCategory sub  on prod.subId=sub.id join shop.model.Category cat on sub.categoryId=cat.id join shop.model.ProductAvail prodAvail on prod.code=prodAvail.productId where (soundex(prod.brand)=soundex(:productName) or  prod.brand like  CONCAT('%', :productName,'%')) or (soundex(prod.name)=soundex(:productName) or prod.name like  CONCAT('%', :productName,'%')) or (soundex(sub.name)=soundex( :productName)\r\n" + 
			" or sub.name like  CONCAT('%', :productName,'%')) or (soundex(cat.name)=soundex(:productName) or cat.name like  CONCAT('%',  :productName,'%') )  ORDER BY \r\n" + 
			"    (CASE\r\n" + 
			"        WHEN (locate(prod.brand,:productName)>0 and locate(prod.name,:productName)<0)THEN prod.name\r\n" + 
			"  WHEN (locate(prod.brand,:productName)>0 and locate(prod.name,:productName)>0)THEN 1 "
			+ "ELSE  prod.name \r\n" + 
			"    END)")
	List<CategoryOnProduct> findCategorySubCategoryByProductName(@Param("productName") String productName);
	
}
