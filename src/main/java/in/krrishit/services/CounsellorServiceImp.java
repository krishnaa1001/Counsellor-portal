package in.krrishit.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import in.krrishit.dto.DashboardResponse;
import in.krrishit.entity.Counsellor;
import in.krrishit.entity.Enquiry;
import in.krrishit.repository.CounsellorRepo;
import in.krrishit.repository.EnquiryRepo;
import jakarta.transaction.Transactional;

@Service
public class CounsellorServiceImp implements CounsellorService {

    private CounsellorRepo counsellorRepo;
    private EnquiryRepo enquiryRepo;

    //@Autowired option all
    public CounsellorServiceImp(CounsellorRepo counsellorRepo, EnquiryRepo enquiryRepo) {
        this.counsellorRepo = counsellorRepo;
        this.enquiryRepo = enquiryRepo;
    }

    // Register a counsellor
    @Override
    public boolean register(Counsellor counsellor) {
        if (counsellor == null || counsellor.getEmail() == null) {
            return false; // Invalid input
        }

        Counsellor savedCounsellor = counsellorRepo.save(counsellor);
        return savedCounsellor != null && savedCounsellor.getCounsellorId() != null;
    }

    // Check for duplicate email
    @Override
    public Counsellor findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        return counsellorRepo.findByemail(email);
    }

    // Login functionality
    @Override
    public Counsellor login(String email, String pwd) {
        if (email == null || email.isEmpty() || pwd == null || pwd.isEmpty()) {
            return null;
        }

        return counsellorRepo.findByEmailAndPwd(email, pwd);
    }

    // Get dashboard statistics
    @Override
    public DashboardResponse getDashboardResponse(Integer counsellorId) {
        DashboardResponse response = new DashboardResponse();
        
        // Always return empty list instead of null
        List<Enquiry> enqList = enquiryRepo.getEnquiriesByCounsellorId(counsellorId) != null 
                              ? enquiryRepo.getEnquiriesByCounsellorId(counsellorId) 
                              : Collections.emptyList();

        // Case-insensitive status check
        response.setTotalEnqs(enqList.size());
        response.setEnrolledEnqs((int) enqList.stream()
                              .filter(e -> "enrolled".equalsIgnoreCase(e.getEnqStatus()))
                              .count());
        response.setOpenEnqs((int) enqList.stream()
                              .filter(e -> "open".equalsIgnoreCase(e.getEnqStatus()))
                              .count());
        response.setLostEnqs((int) enqList.stream()
                              .filter(e -> "lost".equalsIgnoreCase(e.getEnqStatus()))
                              .count());

        return response;
    }
    
    public void updateCounsellor(Integer counsellorId, Counsellor updatedData) {
        Counsellor existing = getCounsellorById(counsellorId);
        if (existing == null) {
            throw new IllegalArgumentException("Counsellor not found");
        }
        
        existing.setName(updatedData.getName());
        existing.setEmail(updatedData.getEmail());
        existing.setPhno(updatedData.getPhno());

        if (updatedData.getPwd() != null && !updatedData.getPwd().isEmpty()) {
            existing.setPwd(updatedData.getPwd()); // Remove encoding if not needed
        }

        counsellorRepo.save(existing);
    }

    @Override
    public Counsellor getCounsellorById(Integer counsellorId) {
        return counsellorRepo.findById(counsellorId)
                             .orElseThrow(() -> new IllegalArgumentException("Counsellor not found with ID: " + counsellorId));
    }


}
