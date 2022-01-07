package com.nttdata.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nttdata.repository.Customer;
import com.nttdata.services.CustomerServiceI;

@Controller
public class CustomerController {

	@Autowired
	CustomerServiceI customerService;
	
	@GetMapping(path = {"/index", "/searchCustomer"})
	public String showCustomerList(Model model, String name) {
		if (name != null && !name.isBlank()) {
			model.addAttribute("customers", customerService.searchByName(name));
		} else {
			model.addAttribute("customers", customerService.searchAll());
		}
		return "index";
	}
	
	@GetMapping("/add")
	public String showAddForm(Customer customer) {
		return "addCustomer";
	}
	
	@PostMapping("/addCustomer")
	public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addCustomer";
		}
		customerService.insertCustomer(customer);
		return "redirect:/index";
	}
	
	@GetMapping("/edit/{ID}")
	public String showUpdateForm(@PathVariable("ID") long ID, Model model) {
		Customer customer = customerService.searchByID(ID);
		model.addAttribute("customer", customer);
		return "updateCustomer";
	}
	
	@PostMapping("/update/{ID}")
	public String updateCustomer(@PathVariable("ID") long ID, @Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			customer.setID(ID);
			return "updateCustomer";
		}
		
		customerService.updateCustomer(customer);
		return "redirect:/index";
	}
	
	@GetMapping("/delete/{ID}")
	public String deleteCustomer(@PathVariable("ID") long ID, Model model) {
		Customer customer = customerService.searchByID(ID);
		customerService.deleteCustomer(customer);
		return "redirect:/index";
	}
}
