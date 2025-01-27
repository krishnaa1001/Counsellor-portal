package in.krrishit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnquiryController {
	
	@GetMapping("/add-enquiry")
	public String displayEnquiryForm()
	{
		return "add-enquiry";
	}
	
	@GetMapping("/view-enquiry")
	public String displayViewEnquiry()
	{
		return "view-enquiry";
	}

}
