package shop.daoservice;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.model.SubCategory;
@Repository
public interface FetchSubCategoryService extends JpaRepository<SubCategory,Long> {
	
	 @Query(value="select new SubCategory(c.id,c.name) from SubCategory c where c.category_id=:categoryId")
	List<SubCategory>  findSubCategory(@Param("categoryId") Long categoryId);

}
