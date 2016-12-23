/**
 * 
 */
package com.selvz.vr.repository.db.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author casam
 *
 */
@Entity
@Table(name = "scenario")
public class Scenario implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String address;
	private String label;
	private String border;
	private Integer slides;
	private Set<User> users = new HashSet<User>();

	public Scenario() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_scenario", nullable = false, unique = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "address", nullable = true, unique = false, length = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "label", nullable = true, unique = false, length = 32)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "border", nullable = true, unique = false, length = 255)
	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	@Column(name = "slides", nullable = true, unique = false)
	public Integer getSlides() {
		return slides;
	}

	public void setSlides(Integer slides) {
		this.slides = slides;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "scenario")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Scenario)) {
			return false;
		}
		Scenario other = (Scenario) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Scenario [id=" + id + ", address=" + address + ", label=" + label + "]";
	}

}
