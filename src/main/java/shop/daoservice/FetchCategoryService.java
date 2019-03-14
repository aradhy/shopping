package shop.daoservice;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.model.Category;

@Repository
public interface FetchCategoryService extends JpaRepository<Category,Long> {
	
	@Query(value="select new Category(c.id,c.name) from Category c")
	List<Category>  findAllCategory();

}
