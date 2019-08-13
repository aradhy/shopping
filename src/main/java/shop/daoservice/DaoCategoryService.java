package shop.daoservice;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.model.Category;

@Repository
public interface DaoCategoryService extends JpaRepository<Category,String> {
	
	@Query(value="select new Category(c.id,c.name) from Category c")
	List<Category>  findCategorySelectedFields();

	List<Category>  findAll();
	
	
	Optional<Category>  findById(@Param("catId") String catId);


}
