package in.krrishit.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import in.krrishit.entity.Counsellor;

public interface CounsellorRepo  extends JpaRepository<Counsellor, Integer>{


	public Counsellor findByemail(String Email);
	
	public 	Counsellor findByEmailAndPwd(String email, String pwd);

}
