package net.mia.phonenumber.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import net.mia.phonenumber.model.PhoneNumberResults;
import net.mia.phonenumber.service.CombinationsGeneratorService;


@Controller
public class Main {

	@Autowired
	CombinationsGeneratorService combinationGenerator;
	
	@RequestMapping("/")
	public String index(){
		return "home";
	}
	

	@RequestMapping("/phonenumber")
	public ModelAndView phoneNumber(HttpServletRequest request){
		
		UserService userService = UserServiceFactory.getUserService();
	    User usr = userService.getCurrentUser();
	    
	    ModelAndView modelAndView = null;
	    if (usr != null) {
	    	modelAndView = new ModelAndView("phonenumber","logout",userService.createLogoutURL(request.getRequestURI()));
	    }else{
	    	modelAndView = new ModelAndView("redirect:"+userService.createLoginURL(request.getRequestURI()),"user",usr);
	    }
		return modelAndView;
	}
	
	@RequestMapping("/getResult")
	public @ResponseBody PhoneNumberResults getAllResult(@RequestParam(value="numbers") String numbers, HttpServletRequest request){
		PhoneNumberResults allResults = combinationGenerator.getResultsPerPage(1,numbers);
		request.getSession().setAttribute("currentPage", allResults.getCurrentPage());
		request.getSession().setAttribute("totalNumber", allResults.getTotal());
		request.getSession().setAttribute("rowPerPage", allResults.getRowPerPage());
		request.getSession().setAttribute("numbers", allResults.getNumbers());
		return allResults;
	}
	@RequestMapping("/getPrePageResult")
	public @ResponseBody PhoneNumberResults goToPreviousPage(HttpServletRequest request){
		String numbers = (String)request.getSession().getAttribute("numbers");
		int currentPage = (Integer)request.getSession().getAttribute("currentPage");
		request.getSession().setAttribute("currentPage", currentPage-1);
		return combinationGenerator.getResultsPerPage(currentPage-1, numbers);
	}
	@RequestMapping("/getNextPageResult")
	public @ResponseBody PhoneNumberResults goToNextPage(HttpServletRequest request){
		String numbers = (String)request.getSession().getAttribute("numbers");
		int currentPage = (Integer)request.getSession().getAttribute("currentPage");
		request.getSession().setAttribute("currentPage", currentPage + 1);
		return combinationGenerator.getResultsPerPage(currentPage+1, numbers);
	}
}
