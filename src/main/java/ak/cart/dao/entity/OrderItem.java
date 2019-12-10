package ak.cart.dao.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderItem {
	@Id
	@GeneratedValue
	private Long id;
	private Long productId;
	private Double basePrice;
	private Integer itemCount;

	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn
	@JsonIgnore
	private PlacedOrder order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public PlacedOrder getOrder() {
		return order;
	}

	public void setOrder(PlacedOrder order) {
		this.order = order;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

}
