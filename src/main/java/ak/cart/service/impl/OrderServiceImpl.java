package ak.cart.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ak.cart.dao.PlacedOrderRepository;
import ak.cart.dao.ProductRepository;
import ak.cart.dao.entity.OrderItem;
import ak.cart.dao.entity.PlacedOrder;
import ak.cart.dao.entity.Product;
import ak.cart.dto.PlacedOrderDTO;
import ak.cart.dto.ProductDTO;
import ak.cart.service.OrderService;
import ak.cart.utils.StringUtils;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private PlacedOrderRepository repository;

	@Autowired
	private ProductRepository productRepo;

	@Override
	public PlacedOrderDTO submitOrder(List<ProductDTO> products) {
		PlacedOrder order = new PlacedOrder();
		order.setOrderId(StringUtils.getUniqueIdentifier());
		order.setOrderDate(Calendar.getInstance().getTime());
		Set<OrderItem> items = new HashSet<>();
		for (ProductDTO product : products) {
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setItemCount(product.getCount());
			item.setBasePrice(product.getPrice());
			item.setProductId(product.getId());
			items.add(item);
		}
		order.setItems(items);
		PlacedOrder save = repository.save(order);
		PlacedOrderDTO dto = new PlacedOrderDTO();
		dto.setId(save.getId());
		dto.setOrderId(save.getOrderId());
		return dto;
	}

	@Override
	public List<PlacedOrderDTO> getAll() {
		List<PlacedOrder> findAll = repository.findAll();
		List<PlacedOrderDTO> list = new ArrayList<>();
		for (PlacedOrder order : findAll) {
			PlacedOrderDTO dto = new PlacedOrderDTO();
			dto.setId(order.getId());
			dto.setOrderDate(order.getOrderDate());
			dto.setOrderId(order.getOrderId());
			list.add(dto);
		}
		return list;
	}

	//TODO refactor
	@Override
	public PlacedOrderDTO getOrderDetails(String orderId) {
		List<PlacedOrder> findByOrderId = repository.findByOrderId(orderId);
		PlacedOrderDTO dto = new PlacedOrderDTO();
		if (!findByOrderId.isEmpty()) {
			PlacedOrder order = findByOrderId.get(0);
			Set<ProductDTO> prodSet = new HashSet<>();
			dto.setId(order.getId());
			dto.setOrderDate(order.getOrderDate());
			dto.setOrderId(orderId);
			for (OrderItem item : order.getItems()) {
				Long productId = item.getProductId();
				Optional<Product> findById = productRepo.findById(productId);
				Product product = findById.get();
				if (product != null) {
					ProductDTO _dto = new ProductDTO();
					_dto.setId(product.getId());
					_dto.setName(product.getName());
					_dto.setCount(item.getItemCount());
					_dto.setPrice(item.getBasePrice());
					_dto.setShortDescription(product.getShortDescription());
					prodSet.add(_dto);
				}
				dto.setProducts(prodSet);
			}
		}
		return dto;
	}

}