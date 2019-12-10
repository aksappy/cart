package ak.cart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ak.cart.dao.entity.PlacedOrder;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Long> {
	List<PlacedOrder> findByOrderId(String orderId);
}
