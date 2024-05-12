package com.mine.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(schema="HR", name="MOUSE")
public class Mouse extends Creature{
	@Column(name="JUMP")
	@Type(type="yes_no")
	private boolean jump;

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	@Override
	public String toString() {
		return "Mouse [jump=" + jump + ", toString()=" + super.toString() + "]";
	}
}
