package com.mine.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema="HR",name="CREATURE")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Creature {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CREATURE_SEQ_GENERATOR")
	@SequenceGenerator(name="CREATURE_SEQ_GENERATOR", sequenceName = "CREATURE_SEQ")
	@Column(name="C_UID")
	private Long uid;
	
	@Column(name="NAME")
	private String name;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Creature [uid=" + uid + ", name=" + name + "]";
	}
}
