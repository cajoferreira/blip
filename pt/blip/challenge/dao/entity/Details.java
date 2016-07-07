package pt.blip.challenge.dao.entity;

import java.io.Serializable;

/**
 * 
 * @author carlos.t.ferreira
 *
 */
public class Details extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -4846746161824511962L;

	// Primary Key
	private Long id;
	private String description;
	private Integer quantity;
	private Double value;
	
	public Details(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		Details other = (Details) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Details [id=" + id + ", description=" + description
				+ ", quantity=" + quantity + ", value=" + value + "]";
	}
}
