package com.middleware.erply.repositories;

import com.middleware.erply.model.product.ProductResponse;
import com.middleware.erply.model.view.EntryIdView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductResponse, Integer> {
     List<EntryIdView> findAllProjectionBy();

}
