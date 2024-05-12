package com.mine.project;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "EMPLOYEES", schema="HR")
public class Employee 
{
	@Id
	@Column(name="EMPLOYEE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPLOYEES_SEQ_GENERATOR")
	@SequenceGenerator(name="EMPLOYEES_SEQ_GENERATOR", sequenceName = "EMPLOYEES_SEQ")
	private Long uid;
	
	@Transient
	private String fullName;
	
	@Column(name="FIRST_NAME" , length=20, nullable=true, unique=false)
	private String firstName; 
	
	@Column(name="LAST_NAME" , length=25, nullable=false, unique=false)
	private String lastName; 
	
	@Column(name="EMAIL")
	private String email; 
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber; 
	
	@Column(name="HIRE_DATE")
	private Date hireDate;  
	
	@Column(name="JOB_ID")			//foreign key for JOBS
	private String jobId;
	
	@Column(name="SALARY")
	private BigDecimal salary;  
	
	@Column(name="COMMISSION_PCT")
	private BigDecimal commissionPct;  
	
/*	@Column(name="MANAGER_ID") 		//foreign key for EMPLOYESS
	private Long managerUid;*/
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="MANAGER_ID")
	private Employee manager;
	
/*	@Column(name="DEPARTMENT_ID")	//foreign key for DEPARTMENTS
	private Long departmentUid;*/
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DEPARTMENT_ID", insertable=false , updatable=false)	//foreign key for DEPARTMENTS
	private Department department;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public BigDecimal getCommissionPct() {
		return commissionPct;
	}

	public void setCommissionPct(BigDecimal commissionPct) {
		this.commissionPct = commissionPct;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [uid=" + uid + ", fullName=" + fullName
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", hireDate=" + hireDate + ", jobId=" + jobId + ", salary="
				+ salary + ", commissionPct=" + commissionPct  + "]";
	}
}