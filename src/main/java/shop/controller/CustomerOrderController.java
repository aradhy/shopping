package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.daoservice.CustomerDaoService;
import shop.dto.Response;
import shop.model.CustomerOrder;
import shop.model.OrderStatus;
import shop.service.CustomerOrderService;

@RestController
public class CustomerOrderController {

	@Autowired
	CustomerOrderService customerOrderService;

	@Autowired
	CustomerDaoService customerDaoService;
	
	@RequestMapping(value="/order", method = RequestMethod.POST)
	public ResponseEntity<Response<Object>> createOrder(@RequestBody CustomerOrder customOrder)
	{
		
		customOrder.setOrderStatusId(OrderStatus.OrderStatus1.getId());
		customerOrderService.createCustomerOrder(customOrder);
		return Response.CREATED();
	}
	
	
	
	@RequestMapping(value="/order", method = RequestMethod.GET)
	public ResponseEntity<Response<CustomerOrder>> getOrder(@RequestParam String customerId)
	{
		return Response.OK(customerDaoService.getByCustomerId(customerId));
	}
}
