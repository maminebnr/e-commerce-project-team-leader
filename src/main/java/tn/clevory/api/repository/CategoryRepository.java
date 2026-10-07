package tn.clevory.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.clevory.api.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
