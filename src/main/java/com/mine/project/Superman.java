package com.mine.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="SUPERMAN",schema="HR")
@PrimaryKeyJoinColumn(referencedColumnName="S_UID")
public class Superman extends Warrior {
	@Column(name="POWER")
	private Long power;
	
	@Column(name="SKILL")
	private String skill;

	public Long getPower() {
		return power;
	}

	public void setPower(Long power) {
		this.power = power;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "Superman [power=" + power + ", skill=" + skill
				+ ", toString()=" + super.toString() + "]";
	}
}
