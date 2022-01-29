package com.digitalservaline.clinic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "role_name")
	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
