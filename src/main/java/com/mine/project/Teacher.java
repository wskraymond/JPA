package com.mine.project;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="T")
public class Teacher extends Staff {
	@Column(name="QUALIFICATION")
	private String qualification;
	
	@Column(name="SUBJECT")
	private String subject;

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Teacher [qualification=" + qualification + ", subject="
				+ subject + ", toString()=" + super.toString() + "]";
	}
}
