package com.example.demo.repository;

import com.example.demo.entity.ShippingInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingInformationRepository extends JpaRepository<ShippingInformation, Integer> {

    List<ShippingInformation> findAll();

    Optional<ShippingInformation> findById(Integer id);

    Optional<ShippingInformation> findByCustomerId(String customerId);

    boolean existsById(Integer id);

    boolean existsByCustomerId(String customerId);

    ShippingInformation save(ShippingInformation entity);

    void delete(ShippingInformation entity);

}
