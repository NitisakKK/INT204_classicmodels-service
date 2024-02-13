package sit.int204.classicmodelsservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sit.int204.classicmodelsservice.entities.Product;
import sit.int204.classicmodelsservice.repositories.ProductRepository;
import sit.int204.classicmodelsservice.services.template.ServiceInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService implements ServiceInterface<Product, String> {
    @Autowired
    ProductRepository repository;

    public List<Product> findAllEntities(String name, Double lower, Double upper, String[] sortBy, String direction) {

        List<Sort.Order> sortProducts = new ArrayList<>();

        if (sortBy != null && sortBy.length != 0) {
            Arrays.stream(sortBy).forEach((String sortVar) -> {
                Sort.Order sortOrder = new Sort.Order(Sort.Direction.fromString(direction), sortVar);
                sortProducts.add(sortOrder);
            });

            if (lower > upper) {
                double temp = upper;
                upper = lower;
                lower = temp;
            }

            if (upper <= 0 && lower <= 0) {
                return repository.findByProductNameContainingIgnoreCase(name, Sort.by(sortProducts));
            } else {
                return repository.findByProductNameContainingIgnoreCaseAndPriceBetween(name, lower, upper, Sort.by(sortProducts));
            }
        } else {
            if (lower > upper) {
                double temp = upper;
                upper = lower;
                lower = temp;
            }

            if (upper <= 0 && lower <= 0) {
                return repository.findByProductNameContainingIgnoreCase(name);
            } else {
                return repository.findByProductNameContainingIgnoreCaseAndPriceBetween(name, lower, upper);
            }
        }
    }

    public List<Product> findProductByProductLine(String productLine) {
        return repository.findByProductLineStartingWith(productLine);
    }

    @Override
    public List<Product> getAllEntities() {
        return repository.findAll();
    }

    @Override
    public Product getEntity(String id) {
        return null;
    }

    @Override
    public Product createNewEntity(Product product) {
        return null;
    }

    @Override
    public Product updateEntity(String id, Product product) {
        return null;
    }

    @Override
    public void removeEntity(String id) {

    }
}
