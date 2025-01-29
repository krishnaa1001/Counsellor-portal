package in.krrishit.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


import in.krrishit.dto.ViewEnqFilterRequest;
import in.krrishit.entity.Enquiry;
import in.krrishit.services.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	private EnquiryService enquiryService;
	
	public EnquiryController(EnquiryService enquiryService)
	{
		this.enquiryService=enquiryService;
	}
	
	//add-enquiry 
	@GetMapping("/add-enquiry")
	public String displayEnquiryForm(Model model)
	{
		Enquiry enqobj=new Enquiry();
		model.addAttribute("enq",enqobj);
		return "add-enquiry";
	}
	
	
	//Handle add-enquiry 
	
	@PostMapping("/add-enquiry")
	public String handleEnquiryForm(@ModelAttribute("enq") Enquiry  enq,  HttpServletRequest req, Model model) throws Exception
	{
		//get existing session obj
		
		HttpSession session =req.getSession(false);
		Integer counsellSes=(Integer) session.getAttribute("counsellorId");
		boolean isSaved= enquiryService.addEnquiry(enq, counsellSes);
		
		if(isSaved)
		{
			model.addAttribute("smsg","Enquiry Addded..");
			
		}
		else
		{
			model.addAttribute("smsg","Enquiry Addded Failed..");
		}
		
		return "add-enquiry";
	}
	
	
	//Get All enquiry in view enquiry page
	@GetMapping("/view-enquiry")
	public String getEnquiry(HttpServletRequest request,Model model)
	{
		HttpSession session =request.getSession(false);
		Integer counsellSes=(Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqList= enquiryService.getAllEnquiries(counsellSes);
		
		model.addAttribute("enquiries",enqList);
		
		ViewEnqFilterRequest  filterReq =new ViewEnqFilterRequest();
		model.addAttribute("viewEnqFilterRequest",filterReq);
		
		return "view-enquiry";
	}
	
	//filter.............
	@PostMapping("/filter-enqs")
	public String filterEnquries(ViewEnqFilterRequest viewEnqFilterRequest , HttpServletRequest request, Model model)
	{
		
		HttpSession session =request.getSession(false);
		Integer counsellSes=(Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqList=enquiryService.getEnquiryWithFilter(viewEnqFilterRequest, counsellSes);
		
		model.addAttribute("enquiries",enqList);
		
		return "view-enquiry";
		
		
		
	}
	
	//edit.....
	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enqId") Integer enqId , Model model)
	{
		Enquiry enquiry =enquiryService.getEnquiryById(enqId);
		model.addAttribute("enq",enquiry);
		
		return "add-enquiry";
	}
	
	
	

}
