package ak.cart.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class PlacedOrderDTO {
	private Long id;
	private String orderId;
	private Date orderDate;
	private Set<ProductDTO> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Set<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductDTO> products) {
		this.products = products;
	}
}
