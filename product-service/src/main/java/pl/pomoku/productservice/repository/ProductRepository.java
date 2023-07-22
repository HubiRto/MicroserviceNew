package pl.pomoku.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pomoku.productservice.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
