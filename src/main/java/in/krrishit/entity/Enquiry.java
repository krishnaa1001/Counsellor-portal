package in.krrishit.entity;



import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="enquiry_tbl")
@Data

public class Enquiry {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enqId;

    private String stuName;
    private Long stuPhone;
    private String classMode; //we can take enum also ya variable
    private String courseName;
    private String enqStatus;
    
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	private LocalDateTime updatedDate;
    
    @ManyToOne(fetch = FetchType.LAZY) //Lazy most used and performance is good also big databased used also
    @JoinColumn(name = "counsellorId", nullable = false)
    private Counsellor counsellor;

}
