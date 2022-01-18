package application.service;

import java.util.List;

import application.model.Complaint ;

public interface ComplaintService {
	Complaint saveComplaint (Complaint  complaint );
	Complaint findById(Long id);
	Complaint create(Complaint complaint ,Long userId,Long adventureId);
	Complaint answer(Complaint complaint ,Long adminId,Long complaintId);
	List<Complaint> findAll();
	void delete(Long id);
}
