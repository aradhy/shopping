package shop.daoservice;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.model.SubCategory;
@Repository
public interface DaoSubCategoryService extends JpaRepository<SubCategory,Long> {
	
	 @Query(value="select sub from SubCategory sub where sub.category_id=:categoryId")
	List<SubCategory>  findSubCategory(@Param("categoryId") String categoryId);
	 
	 @Query(value="select new SubCategory(sub.id,sub.name,sub.image_id) from SubCategory sub")
	List<SubCategory>  findAllSubCategories();

}
