package shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import shop.daoservice.AddressDeliveryDao;
import shop.model.AddressDetails;
import shop.service.AddressDeliveryService;

public class AddressDeliveryServiceImpl implements AddressDeliveryService {

	@Autowired
	AddressDeliveryDao addressDeliveryDao;
	
	
	
	
	@Override
	public void saveAddressDetails(AddressDetails addressDetails)
	{
		addressDeliveryDao.save(addressDetails);
	}
	

}
