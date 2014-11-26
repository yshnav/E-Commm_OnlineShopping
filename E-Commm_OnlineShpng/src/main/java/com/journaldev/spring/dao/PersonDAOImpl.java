package com.journaldev.spring.dao;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.Person;


@Repository
public class PersonDAOImpl implements PersonDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf)
	{
		this.sessionFactory = sf;
	}

	@Override
	public void addPerson(Person p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Person saved successfully, Person Details="+p);
	}

	@Override
	public void updatePerson(Person p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Person updated successfully, Person Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> listPersons() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Person> personsList = session.createQuery("from Person").list();
		for(Person p : personsList){
			logger.info("Person List::"+p);
		}
		return personsList;
	}

	@Override
	public Person getPersonById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Person p = (Person) session.load(Person.class, new Integer(id));
		logger.info("Person loaded successfully, Person details="+p);
		return p;
	}

	@Override
	public void removePerson(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Person p = (Person) session.load(Person.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("Person deleted successfully, person details="+p);
	}

	 

	@Override
	public String getPerson(String username,String password) {
		System.out.println("");
		
		Session session = this.sessionFactory.getCurrentSession();
		 List users = session.createQuery("FROM Person").list(); 
		 for (Iterator iterator = users.iterator(); iterator.hasNext();)
		 {
			 Person p =  (Person) iterator.next(); 
			 
			 if(p.getUsername().equals(username) && (p.getPwd().equals(password)))
			 {
				 System.out.println("logged in successfully");
				 String s="Login successfully";
/*
			       ((HttpSession) session).setAttribute("uname",username );
			       ((HttpSession) session).setAttribute("lpwd", password);*/
			       /*
			       HttpSession session=request.getSession(false);
					 String name=(String) session.getAttribute("user");

*/				 return "success";
				 
			 }  
			 else
				 return "failure";
	        	/*if((p.getUsername().equals(g))&&(p.getPwd().equals()))*/  
		 }
		 System.out.println("Invalid credentials");
		return "Invalid credentials";
		
		
		 
		/* Query query = session.createQuery("from Person p where p.username = :username"); 
		 query.setParameter("username", username); 
		 System.out.println("usermmmmmmmmmmmmm"+username);
		 userList = query.list(); 
		 
		 if (userList.size() > 0) 
			 return (Person) userList.get(0); 
		 else
			 return null;
*/		
	}

}
