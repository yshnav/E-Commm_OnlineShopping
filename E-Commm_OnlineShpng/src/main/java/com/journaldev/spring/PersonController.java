package com.journaldev.spring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonService;

@Controller
public class PersonController {
	
	private static final Logger logger=LoggerFactory.getLogger(PersonController.class);
	private PersonService personService;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexpg()
	{
		return "index";
	}
	
	@RequestMapping(value = "persons", method = RequestMethod.GET)
	public String listPersons(Model model) {
		System.out.println("index..................");
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "login";
	}
	
	/*@RequestMapping(value = "signin", method = RequestMethod.GET)
	public String signin(Model model) {
		
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "redirect:/persons";
	}*/
	
	//For add and update person both
	@RequestMapping(value= "person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Person p){
		
		System.out.println("added..................");
			this.personService.addPerson(p);
		
		
		return "redirect:/persons";
		
	}
	
	 @RequestMapping("index")  
	 public ModelAndView getForm(@ModelAttribute Person p) {  
	  return new ModelAndView("index");  
	 }  
	 
	 
	 @RequestMapping(value = "signin",method = RequestMethod.GET)
	 public String processRegistration(@RequestParam("username") String name,@RequestParam("Pwd") String pass/*@ModelAttribute("SignIn") Person p,Map<String, String> model,Model model1*/) 
	 {
		 String s=this.personService.getPerson(name,pass);
		 
		 System.out.println("logged In");
		 if(s.equals("success"))
		 {
			 return "index";
		 }
		/* System.out.println("username: "+p.getUsername());
		 System.out.println("password:" +p.getPwd());
*/
	     /*   model1.addAttribute("SIGNIN", this.personService.getPerson(p.getUsername()));*/
		 else  
		return "login";
	 
	 }
	 
	 
	
	
	@RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id){
		
        this.personService.removePerson(id);
        return "redirect:/persons";
    }
 
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "index";
    }
	
}
