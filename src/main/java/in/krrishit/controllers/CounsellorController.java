package in.krrishit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.krrishit.dto.DashboardResponse;
import in.krrishit.entity.Counsellor;
import in.krrishit.services.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	private CounsellorService counsellorService;
	
	public CounsellorController(CounsellorService counsellorService)
	{
		this.counsellorService=counsellorService;
	}
	
	@GetMapping("/")
	public String displayIndex()
	{
		
		//returning view name
		return "index";
		
	}
	
	@GetMapping("/login")
	public String displayLogin(Model model)
	{
		Counsellor cobj=new Counsellor();
		
		//sending data from controller to ui
		model.addAttribute("counsellor", cobj);
		return "login";
		
	}
	
	 @PostMapping("/login")
	    public String handleLogin(Counsellor counsellor, HttpServletRequest request, Model model) {
	        Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
	        
	        if (c == null) {
	            model.addAttribute("emsg", "Invredentalid cials!");
	            return "login";
	        } else {
	            // Fixed session attribute key name
	            HttpSession session = request.getSession(true);
	            session.setAttribute("counsellorId", c.getCounsellorId()); // Corrected key
	            
	           
	            return "redirect:/dashboard";
	        }
	    }
	

	
	@GetMapping("/register")
	public String displayRegister( Model model)
	{
		Counsellor cobj=new Counsellor();
		
		//sending data from controller to ui
		model.addAttribute("counsellor", cobj);
		return "register";
		
	}
	
	@PostMapping("/register")
	public String handleRegister(Counsellor counsellor,Model model)
	{
		Counsellor byEmail= counsellorService.findByEmail(counsellor.getEmail());
		
		if(byEmail !=null)
		{
			model.addAttribute("emsg","Duplicate Email Try again......");
			return "register";
		}
		
		boolean isRegistered= counsellorService.register(counsellor);
		
		if(isRegistered)
		{
			model.addAttribute("smsg" , "Registration sucess.... now Login !!");
		}
		else
		{
			model.addAttribute("emsg","Registration Faield!!");
			
		}
		return "register";
		
		
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req){
		
		//get existing session and invalidate it..
		HttpSession session=req.getSession(false);
		session.invalidate();
		return "redirect:/"; // click logout then goto index page  
		
	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model)
	{
		
		HttpSession session =req.getSession(false);
		Integer counsellSes=(Integer) session.getAttribute("counsellorId");
		
	    DashboardResponse dbResponse = counsellorService.getDashboardResponse(counsellSes);
        model.addAttribute("dashboardInfo", dbResponse);
		
		return "dashboard";
	}
	

	@GetMapping("/profile")
	public String showProfile(HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);
	    if(session == null) return "redirect:/login";
	    
	    Integer counsellorId = (Integer) session.getAttribute("counsellorId");
	    Counsellor counsellor = counsellorService.getCounsellorById(counsellorId);
	    model.addAttribute("counsellor", counsellor);
	    return "profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute Counsellor updatedCounsellor, 
	                          HttpServletRequest request,
	                          RedirectAttributes redirectAttributes) {
	    try {
	        HttpSession session = request.getSession(false);
	        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
	        counsellorService.updateCounsellor(counsellorId, updatedCounsellor);
	        redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error updating profile");
	    }
	    return "redirect:/profile";
	}

}
