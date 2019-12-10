package ak.cart.service;

import java.util.List;

import ak.cart.dao.entity.Product;

public interface ProductService {
	/**
	 * @return {@link List} {@link Product}
	 * @throws Exception
	 * Return all products in the database
	 * TODO Paging to be implemented
	 */
	public List<Product> getAll() throws Exception;

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * Get one product based on id
	 */
	public Product getOne(Long id) throws Exception;

	public Product save(Product t) throws Exception;

	public Product update(Product t) throws Exception;

	public boolean delete(Product t) throws Exception;
}
