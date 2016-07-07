package pt.blip.challenge.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author carlos.t.ferreira
 *
 */
public class Purchase extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 5833308990399600768L;
	
	// Primary Key
	private Long id;
	private String productType;
	private Date expires;
	private Details purchaseDetails;

	public Purchase(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public Date getExpires() {
		return expires;
	}
	
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	public Details getPurchaseDetails() {
		return purchaseDetails;
	}
	
	public void setPurchaseDetails(Details purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", productType=" + productType
				+ ", expires=" + expires + ", purchaseDetails="
				+ purchaseDetails + "]";
	}
}
