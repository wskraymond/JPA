package com.mine.project;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value="N")
public class Expert extends Staff {
	@Column(name="EXPERTICE")
	private String expertice;

	public String getExpertice() {
		return expertice;
	}

	public void setExpertice(String expertice) {
		this.expertice = expertice;
	}

	@Override
	public String toString() {
		return "Expert [expertice=" + expertice + ", toString()="
				+ super.toString() + "]";
	}
}
