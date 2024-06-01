package com.example.hieustore.repository;

import com.example.hieustore.domain.entity.PaymentMethods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, String> {
    @Query(value = "SELECT payment_methods.* FROM payment_methods", nativeQuery = true)
    Page<PaymentMethods> getAll(Pageable pageable);

    @Query(value = "SELECT * FROM payment_methods WHERE status = ?1", nativeQuery = true)
    Page<PaymentMethods> getByStatus(Boolean status, Pageable pageable);

}
