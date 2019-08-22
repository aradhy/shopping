package shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.CustomerDaoService;
import shop.model.CustomerOrder;


@Service
public class CustomerOrderService {

	@Autowired
	CustomerDaoService customerDaoService;

	public void createCustomerOrder(CustomerOrder customOrder) {

		customerDaoService.save(customOrder);

	}

	
/*	public void updateCustomerOrder(CustomerOrder customOrder) {

		customerDaoService.updateCustomerOrder(customOrder);

	}*/
}
