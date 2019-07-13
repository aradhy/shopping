package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.dto.Response;
import shop.model.AddressDetails;
import shop.service.AddressDeliveryService;

@RestController
public class AddressDeliveryController {

	@Autowired
	AddressDeliveryService addressDeliveryService;

	@RequestMapping()
	public Response<String> saveAddressController(AddressDetails addressDetails) {
		 addressDeliveryService.saveAddressDetails(addressDetails);
		return new Response<>(null,"Address Saved Successfully",201);

	}

}
