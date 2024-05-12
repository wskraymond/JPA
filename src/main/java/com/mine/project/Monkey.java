package com.mine.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(schema="HR", name="MONKEY")
public class Monkey extends Creature{
	@Column(name="CLIMB")
	@Type(type="yes_no")
	private boolean climb;

	public boolean isClimb() {
		return climb;
	}

	public void setClimb(boolean climb) {
		this.climb = climb;
	}

	@Override
	public String toString() {
		return "Monkey [climb=" + climb + ", toString()=" + super.toString()
				+ "]";
	}
}
