package com.mine.project;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="DEPARTMENTS", schema="HR")
//@OptimisticLocking
public class Department {
	@Id
	@Column(name="DEPARTMENT_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DEPARTMENTS_SEQ_GENERATOR")
	@SequenceGenerator(name="DEPARTMENTS_SEQ_GENERATOR", sequenceName="DEPARTMENTS_SEQ")
	private Long uid;
	
	@Column(name="DEPARTMENT_NAME", length=30,nullable=false)
	private String departmentName;
	
	@Column(name="MANAGER_ID")
	private Long managerUId;	//foreign key for EMPLOYEES
	
	@Column(name="LOCATION_ID")
	private Long locationUid;	//foreign key for LOCATIONS
	
	@OneToMany(mappedBy="department" ,fetch=FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true)
//	@OptimisticLock(excluded=true)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<Employee> employees;

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the managerUId
	 */
	public Long getManagerUId() {
		return managerUId;
	}

	/**
	 * @param managerUId the managerUId to set
	 */
	public void setManagerUId(Long managerUId) {
		this.managerUId = managerUId;
	}

	/**
	 * @return the locationUid
	 */
	public Long getLocationUid() {
		return locationUid;
	}

	/**
	 * @param locationUid the locationUid to set
	 */
	public void setLocationUid(Long locationUid) {
		this.locationUid = locationUid;
	}

	/**
	 * @return the employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Department [uid=" + uid + ", departmentName=" + departmentName
				+ ", managerUId=" + managerUId + ", locationUid=" + locationUid
				+ ", employees=" + employees + "]";
	}

		
}
