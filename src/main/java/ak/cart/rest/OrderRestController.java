package ak.cart.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ak.cart.dto.PlacedOrderDTO;
import ak.cart.dto.ProductDTO;
import ak.cart.service.OrderService;

/**
 * @author ak
 * @since 2019-12-09
 *
 *        All the orders related rest end points are managed in this controller
 */
//TODO implement @ExceptionHandler and ResponseEntity
@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestController.class);

	@Autowired
	private OrderService service;

	/**
	 * @param products
	 * @return {@link PlacedOrderDTO}
	 * 
	 *         Order submitted with a list of products are saved into placed_order
	 *         table
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public PlacedOrderDTO submitOrder(@RequestBody List<ProductDTO> products) {
		LOGGER.info(">> submit order called");
		return service.submitOrder(products);
	}

	/**
	 * @return {@link List} {@link PlacedOrderDTO}
	 * 
	 *         Get all orders persisted in placed_order table
	 */
	@RequestMapping("")
	public List<PlacedOrderDTO> listOrders() {
		LOGGER.info(">> listOrders called");
		return service.getAll();
	}

	/**
	 * @param orderId
	 * @return {@link PlacedOrderDTO}
	 * 
	 *         Get the details / products associated with an order from placed_order
	 *         and order_item table
	 */
	@RequestMapping("/{orderId}")
	public PlacedOrderDTO getOrderDetails(@PathVariable("orderId") String orderId) {
		LOGGER.info(">> getOrderDetails called with {} orderId", orderId);
		return service.getOrderDetails(orderId);
	}
}
