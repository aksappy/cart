package ak.cart.service;

import java.util.List;

import ak.cart.dto.PlacedOrderDTO;
import ak.cart.dto.ProductDTO;

public interface OrderService {
	public PlacedOrderDTO submitOrder(List<ProductDTO> products);

	public List<PlacedOrderDTO> getAll();

	public PlacedOrderDTO getOrderDetails(String orderId);
}
