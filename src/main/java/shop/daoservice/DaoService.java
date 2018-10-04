package shop.daoservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.model.Product;
@Repository
public interface DaoService extends JpaRepository<Product,String> {
	

}
