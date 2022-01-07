package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.repository.Customer;
import com.nttdata.services.CustomerServiceI;

@RestController
@RequestMapping("/api")
public class CustomerControllerAPI {

	@Autowired
	private CustomerServiceI customerService;
	
	@GetMapping("/customers")
	public List<Customer> findAll() {
		
		return customerService.searchAll();
	}
	
	@GetMapping("/customers/find/{ID}")
	public Customer getCustomer(@PathVariable Long ID) {
		
		Customer result = customerService.searchByID(ID);
		if (result == null) {
			throw new RuntimeException("Customer with ID " + ID + "not found.");
		}
		
		return result;
	}
	
	@PostMapping("/add")
	public void addCustomer(@RequestBody Customer customer) {
		
		customerService.insertCustomer(customer);
	}
	
	@DeleteMapping("/delete/{ID}")
	public void deleteCustomer(@PathVariable Long ID) {
		
		Customer customer = customerService.searchByID(ID);
		if (customer != null) {
			customerService.deleteCustomer(customer);
		}
	}
}
