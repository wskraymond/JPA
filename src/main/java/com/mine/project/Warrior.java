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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="WARRIOR" , schema="HR")
@Inheritance(strategy=InheritanceType.JOINED)
public class Warrior {
	@Id
	@Column(name="S_UID")
	@SequenceGenerator(name="WARRIOR_SEQ_GENERATOR", sequenceName="WARRIOR_SEQ", schema="HR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WARRIOR_SEQ_GENERATOR")
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
		return "Warrior [uid=" + uid + ", name=" + name + "]";
	}
	
}
