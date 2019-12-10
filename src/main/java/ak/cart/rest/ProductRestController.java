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

import ak.cart.dao.entity.Product;
import ak.cart.service.ProductService;

//TODO implement @ExceptionHandler and ResponseEntity

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

	@Autowired
	private ProductService service;

	/**
	 * @return {@link List} {@link Product} Get all products in the product table
	 */
	@RequestMapping("")
	public List<Product> getProducts() {
		LOGGER.info(">> getProducts called");
		try {
			return service.getAll();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.info("{}", e);
		}
		return null;
	}

	/**
	 * @param product
	 * @return {@link Product} Save a product
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Product saveProduct(@RequestBody Product product) {
		try {
			return service.save(product);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.info("{}", e);
		}
		return product;
	}

	/**
	 * @param product
	 * @param id
	 * @return {@link Product} Return a product based on the id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Product updateProduct(@RequestBody Product product, @PathVariable("id") Long id) {
		try {
			return service.update(product);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.info("{}", e);
		}
		return product;
	}
}
