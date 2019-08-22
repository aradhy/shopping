package shop.daoservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import shop.model.CustomerOrder;

public interface CustomerDaoService  extends JpaRepository<CustomerOrder,Long> {

	/*@Modifying
	public void updateCustomerOrder(CustomerOrder customerOrder);*/
	
	
	public CustomerOrder getByCustomerId(@Param("userId") String customerId);
}
