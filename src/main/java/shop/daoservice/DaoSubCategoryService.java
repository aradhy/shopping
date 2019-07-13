package shop.daoservice;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.model.SubCategory;
@Repository
public interface DaoSubCategoryService extends JpaRepository<SubCategory,String> {
	
	 @Query(value="select sub from SubCategory sub where sub.categoryId=:categoryId")
	List<SubCategory>  findSubCategory(@Param("categoryId") String categoryId);
	 
	 @Query(value="select new SubCategory(sub.id,sub.name,sub.imageId) from SubCategory sub")
	List<SubCategory>  findAllSubCategories();

}
