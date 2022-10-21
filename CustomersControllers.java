package com.greatLearning.customerManagement.controller;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatLearning.customerManagement.entity.Customer;
import com.greatLearning.customerManagement.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomersControllers {

	@Autowired
	private CustomerService customerService;

	// add mapping for "/list"

	@RequestMapping("/list")
	public String listCustomers(Model theModel) {

		// get Books from db
		List<Customer> theCustomers = customerService.findAll();

		// add to the spring model
		theModel.addAttribute("Customers", theCustomers);

		return "list-Customers";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Customer theCustomer = new Customer();

		theModel.addAttribute("Customer", theCustomer);

		return "Customer-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

		// get the Book from the service
		Customer theCustomer = customerService.findById(theId);

		// set Book as a model attribute to pre-populate the form
		theModel.addAttribute("Customer", theCustomer);

		// send over to our form
		return "Customer-form";
	}

	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("FirstName") String FirstName,
			@RequestParam("LastName") String LastName, @RequestParam("Email") String Email) {

		System.out.println(id);
		Customer theCustomer;
		if (id != 0) {
			theCustomer = customerService.findById(id);
			theCustomer.setFirstName(FirstName);
			theCustomer.setLastName(LastName);
			theCustomer.setEmail(Email);
		} else
			theCustomer = new Customer(FirstName, LastName, Email);
		// save the Book
		customerService.save(theCustomer);

		// use a redirect to prevent duplicate submissions
		return "redirect:/customers/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {

		// delete the Book
		customerService.deleteById(theId);

		// redirect to /Books/list
		return "redirect:/customers/list";

	}

	@RequestMapping("/search")
	public String search(@RequestParam("FirstName") String FirstName, @RequestParam("LastName") String LastName, Model theModel) {

		// check names, if both are empty then just give list of all Books

		if (FirstName.trim().isEmpty() && LastName.trim().isEmpty()) {
			return "redirect:/customers/list";
		} else {
			// else, search by first name and last name
			List<Customer> theCustomers = customerService.searchBy(FirstName, LastName);

			// add to the spring model
			theModel.addAttribute("Customers", theCustomers);

			// send to list-Books
			return "list-Customers";
		}

	}
}

