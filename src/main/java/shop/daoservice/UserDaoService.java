package shop.daoservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.model.User;

@Repository
public interface UserDaoService extends JpaRepository<User,Integer> {
    Optional<User> findById(Integer id);

}
