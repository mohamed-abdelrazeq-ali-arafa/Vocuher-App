package com.vodafone.spring.batch.service;


import com.vodafone.spring.batch.entity.Voucher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IVoucherService {

    ResponseEntity<Voucher> reserveVoucher(int id,Long userId);
    ResponseEntity<Voucher> commitVoucher(int voucherId,Long userId);
    ResponseEntity<Voucher> getVoucher(int id);
    ResponseEntity<Voucher> rollBackVoucher(int id,Long userId);
    ResponseEntity<List<Voucher>> getAllVoucher();
    List<Voucher> getVouchersByPagination(int pageNo, int pageSize);

}
