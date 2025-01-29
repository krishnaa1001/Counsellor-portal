package in.krrishit.services;

import in.krrishit.dto.DashboardResponse;
import in.krrishit.entity.Counsellor;

public interface CounsellorService {
	
 //most implement we take method  and parameter  to unders...
	
	public Counsellor findByEmail(String email);
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String email,String pwd);
	
	
	
	public DashboardResponse getDashboardResponse(Integer counsellorId);

	public Counsellor getCounsellorById(Integer counsellorId);

	public void updateCounsellor(Integer counsellorId, Counsellor updatedCounsellor);
	

}
