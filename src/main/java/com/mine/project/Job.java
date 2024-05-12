package com.mine.project;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


@Entity
/*usage (required) specifies the caching strategy: transactional, read-write, nonstrict-read-write or read-only
region (optional: defaults to the class or collection role name): specifies the name of the second level cache region
include (optional: defaults to all) non-lazy: specifies that properties of the entity mapped with lazy="true" cannot be cached when attribute-level lazy fetching is enabled*/
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Immutable
@Table(name="JOBS", schema="HR")
@NamedQuery(name="findJobByJobTitle", query="select po from Job po where po.jobTitle = :jobTitle", lockMode=LockModeType.PESSIMISTIC_READ)
//@DynamicUpdate
public class Job {
	@Id
	@Column(name="JOB_ID")
	private String uid;
	
	@Column(name="JOB_TITLE")
	private String jobTitle;
	
	@Column(name="MIN_SALARY")
	private BigDecimal minSalary;
	
	@Column(name="MAX_SALARY")
	private BigDecimal maxSalary;
	
	@Version
	@Column(name="VERSION")
	private Long version;

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the minSalary
	 */
	public BigDecimal getMinSalary() {
		return minSalary;
	}

	/**
	 * @param minSalary the minSalary to set
	 */
	public void setMinSalary(BigDecimal minSalary) {
		this.minSalary = minSalary;
	}

	/**
	 * @return the maxSalary
	 */
	public BigDecimal getMaxSalary() {
		return maxSalary;
	}

	/**
	 * @param maxSalary the maxSalary to set
	 */
	public void setMaxSalary(BigDecimal maxSalary) {
		this.maxSalary = maxSalary;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Job [uid=" + uid + ", jobTitle=" + jobTitle + ", minSalary="
				+ minSalary + ", maxSalary=" + maxSalary + ", version="
				+ version + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Job)) {
			return false;
		}
		Job other = (Job) obj;
		if (uid == null) {
			if (other.uid != null) {
				return false;
			}
		} else if (!uid.equals(other.uid)) {
			return false;
		}
		return true;
	}
	
	
}
