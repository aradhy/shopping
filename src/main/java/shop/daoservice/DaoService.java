package shop.daoservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.model.Product;

@Repository
public interface DaoService extends JpaRepository<Product,String> {
    Optional<Product> findByCode(Integer code);


	List<Product> findByCategoryId(Integer categoryId);

}
