package shop.daoservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.model.AddressDetails;

@Repository
public interface AddressDeliveryDao extends JpaRepository<AddressDetails,Long> {
	
	


}
