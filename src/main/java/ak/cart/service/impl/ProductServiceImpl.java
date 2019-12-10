package ak.cart.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ak.cart.dao.ProductRepository;
import ak.cart.dao.entity.Product;
import ak.cart.service.ProductService;
import javassist.NotFoundException;

@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository repository;

	@Override
	public List<Product> getAll() throws Exception {
		LOGGER.debug(">> getAll called");
		try {
			List<Product> findAll = repository.findAll();
			return findAll;
		} catch (Exception e) {
			LOGGER.error("Error while fetching all products");
			LOGGER.error(e.getMessage());
			LOGGER.info("{}", e);
			throw e;
		}
	}

	@Override
	public Product getOne(Long id) throws Exception {
		Optional<Product> findById = repository.findById(id);
		Product product = findById.get();
		if (product != null)
			return product;
		else
			throw new NotFoundException("Product with id was not found");
	}

	@Override
	public Product save(Product t) throws Exception {
		return repository.save(t);
	}

	@Override
	public Product update(Product t) throws Exception {
		Optional<Product> findById = repository.findById(t.getId());
		Product product = findById.get();
		product.setName(t.getName());
		product.setPrice(t.getPrice());
		product.setImgUrl(t.getImgUrl());
		product.setShortDescription(t.getShortDescription());
		return repository.save(product);
	}

	@Override
	public boolean delete(Product t) throws Exception {
		throw new UnsupportedOperationException();
	}

}
