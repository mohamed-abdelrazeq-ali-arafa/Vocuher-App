package com.vodafone.spring.batch.repository;

import com.vodafone.spring.batch.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoucherRepository extends PagingAndSortingRepository<Voucher, Integer> {


}