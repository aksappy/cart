package ak.cart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ak.cart.dao.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
