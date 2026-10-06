package tn.clevory.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.clevory.api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
