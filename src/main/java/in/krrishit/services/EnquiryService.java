package in.krrishit.services;

import java.util.List;

import in.krrishit.dto.ViewEnqFilterRequest;
import in.krrishit.entity.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public List<Enquiry> getEnquiryWithFilter(ViewEnqFilterRequest filterRequest , Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);
	

}
