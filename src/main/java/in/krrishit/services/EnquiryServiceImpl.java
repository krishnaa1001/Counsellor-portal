package in.krrishit.services;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.krrishit.dto.ViewEnqFilterRequest;
import in.krrishit.entity.Counsellor;
import in.krrishit.entity.Enquiry;
import in.krrishit.repository.CounsellorRepo;
import in.krrishit.repository.EnquiryRepo;
import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService{

	//consturctor injection
	private EnquiryRepo enquiryRepo;
	private CounsellorRepo counsellorRepo;
	
	
	public EnquiryServiceImpl(EnquiryRepo enquiryRepo, CounsellorRepo counsellorRepo) {
	    this.enquiryRepo = enquiryRepo;  // Assign constructor parameter to instance variable
	    this.counsellorRepo = counsellorRepo;  // Same here
	}

	
	//add new  enquiry....
	@Override
	public boolean addEnquiry(Enquiry enq , Integer counsellorId) throws Exception {
		
		//realtion with counsellor  and identify which counsellor 
		Counsellor counsellor= counsellorRepo.findById(counsellorId).orElse(null);
		
		if(counsellor == null)
		{
			throw new Exception("No Counsellor Found !!!");
		}
		
		//associating counsellor to enquiry
		enq.setCounsellor(counsellor);
		
		Enquiry save= enquiryRepo.save(enq);
		
		if(save.getEnqId() !=null)
		{
			return true;
		}
		return false;
	}

	
	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		return  enquiryRepo.getEnquiriesByCounsellorId(counsellorId);
		
	}



	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		
		return 	enquiryRepo.findById(enqId).orElse(null);
		
		
	}

	
	@Override
	public List<Enquiry> getEnquiryWithFilter(ViewEnqFilterRequest filterRequest, Integer counsellorId) {
		
		//Dynamic query Preparation
		
		Enquiry enq=new Enquiry();
		
		if(StringUtils.isNotEmpty(filterRequest.getClassMode()))
		{
			enq.setClassMode(filterRequest.getClassMode());
		}
		
		if(StringUtils.isNotEmpty(filterRequest.getCourseName()))
		{
			enq.setCourseName(filterRequest.getCourseName());
		}
		
		
		if(StringUtils.isNotEmpty(filterRequest.getEnqStatus()))
		{
			enq.setEnqStatus(filterRequest.getEnqStatus());
		}
		
		Counsellor coun= counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(coun);
		
		Example<Enquiry> of = Example .of(enq); //dynamic query
		List<Enquiry> enqList=enquiryRepo.findAll(of);
		
		
		return enqList;
	}

}
