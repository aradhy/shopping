package shop.daoservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.model.Product;

import java.util.Optional;

@Repository
public interface DaoService extends JpaRepository<Product,Integer> {
    Optional<Product> findByCode(Integer code);

}
