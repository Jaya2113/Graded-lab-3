package com.greatLearning.customerManagement.service;


//package com.greatLearning.customerManagement.service;



import java.util.List;



import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.greatLearning.customerManagement.entity.Customer;


@Repository
public class CustomerServiceimpl implements CustomerService {

	private SessionFactory sessionFactory;

	// create session
	private Session session;

	@Autowired
	CustomerServiceimpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

	}

	@Transactional
	public List<Customer> findAll() {
		
		Transaction tx = session.beginTransaction();

		// find all the records from the database table
		List<Customer> customers = session.createQuery("from Customer").list();

		tx.commit();

		return customers;
	}

	@Transactional
	public Customer findById(int id) {

		Customer customer = new Customer();

		
		Transaction tx = session.beginTransaction();

		// find record with Id from the database table
		customer = session.get(Customer.class, id);

		tx.commit();

		return customer;
	}

	@Transactional
	public void save(Customer thecustomer) {

		
		Transaction tx = session.beginTransaction();

		// save transaction
		session.saveOrUpdate(thecustomer);

		tx.commit();

	}

	@Transactional
	public void deleteById(int id) {

	
		Transaction tx = session.beginTransaction();

		// get transaction
		Customer customer = session.get(Customer.class, id);

		// delete record
		session.delete(customer);

		tx.commit();

	}

	@Transactional
	public List<Customer> searchBy(String FirstName, String LastName) {

		
		Transaction tx = session.beginTransaction();
		String query = "";
		if (FirstName.length() != 0 && LastName.length() != 0)
			query = "from Customer where name like '%" + FirstName + "%' or LastName like '%" + LastName + "%'";
		else if (FirstName.length() != 0)
			query = "from customer where FirstName like '%" + FirstName + "%'";
		else if (LastName.length() != 0)
			query = "from Customer where customer like '%" + LastName + "%'";
		else
			System.out.println("Cannot search without input data");

		List<Customer> customer = session.createQuery(query).list();

		tx.commit();

		return customer;
	}

	// print the loop
	@Transactional
	public void print(List<Customer> customer) {

		for (Customer b : customer) {
			System.out.println(b);
		}
	}

}