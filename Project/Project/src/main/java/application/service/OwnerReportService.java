package application.service;

import java.util.List;

import application.model.OwnerReport;

public interface OwnerReportService {
	OwnerReport create(OwnerReport report);
	void delete(Long reportId);
	void update(OwnerReport report);
	List<OwnerReport> GetAll();
	OwnerReport GetById(Long id);
	List<OwnerReport> getUnapprovedReports();
}
