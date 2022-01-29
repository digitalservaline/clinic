package com.digitalservaline.clinic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
 
    @Column(name = "update_date")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifieDate;
    
    @Column(name = "create_by")
    @CreatedBy
    private String createBy;
 
    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifieDate() {
		return modifieDate;
	}

	public void setModifieDate(Date modifieDate) {
		this.modifieDate = modifieDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
