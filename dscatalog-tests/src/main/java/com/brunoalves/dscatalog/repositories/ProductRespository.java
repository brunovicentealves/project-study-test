package com.brunoalves.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.brunoalves.dscatalog.entities.Product;

@Repository
public interface ProductRespository extends JpaRepository<Product, Long> {

}
