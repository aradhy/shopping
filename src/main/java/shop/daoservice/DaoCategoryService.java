package shop.daoservice;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.model.Category;

@Repository
public interface DaoCategoryService extends JpaRepository<Category,String> {
	
	@Query(value="select new Category(c.id,c.name,c.image_id) from Category c")
	List<Category>  findCategorySelectedFields();

	List<Category>  findAll();


}
