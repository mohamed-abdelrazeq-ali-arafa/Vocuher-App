package com.vodafone.spring.batch.repository;

import com.vodafone.spring.batch.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendorRepository extends JpaRepository<Vendor, Long> {

    Vendor findByName(String name);
}
