package com.mine.project;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="STAFF", schema="HR")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING, columnDefinition="TNS")
@DiscriminatorValue(value="S")
public class Staff {
	@Id
	@Column(name="SID")
	@SequenceGenerator(name="STAFF_SEQ_GENERATOR", sequenceName="STAFF_SEQ", schema="HR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STAFF_SEQ_GENERATOR")
	private Long sid;
	
	@Column(name="SNAME")
	private String sname;

	

	public Long getSid() {
		return sid;
	}



	public void setSid(Long sid) {
		this.sid = sid;
	}



	public String getSname() {
		return sname;
	}



	public void setSname(String sname) {
		this.sname = sname;
	}



	@Override
	public String toString() {
		return "Staff [sid=" + sid + ", sname=" + sname + "]";
	}
}
