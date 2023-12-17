package com.vodafone.spring.batch.ImplService;


import com.vodafone.spring.batch.entity.User;
import com.vodafone.spring.batch.entity.Voucher;
import com.vodafone.spring.batch.repository.IUserRepository;
import com.vodafone.spring.batch.repository.IVoucherRepository;
import com.vodafone.spring.batch.service.IVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService implements IVoucherService {

    @Autowired
    private IVoucherRepository voucherRepository;

    @Autowired
    private IUserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(Voucher.class);


    @Override
    public ResponseEntity<Voucher> reserveVoucher(int id,Long userId) {
        LOGGER.info("Starting reserve voucher");
        Voucher tempVoucher = voucherRepository.findById(id).orElse(null);
        User tempUser = userRepository.findById(userId).orElse(null);
        if (tempVoucher == null || tempUser==null || tempVoucher.getState().equals(Voucher.Status.RESERVED)
        ) {
            return new ResponseEntity<Voucher>(tempVoucher, HttpStatus.NOT_FOUND);
        } else {
            tempVoucher.setState(Voucher.Status.RESERVED);
            tempVoucher.setWhoReserve(userId);
            return new ResponseEntity<Voucher>(voucherRepository.save(tempVoucher), HttpStatus.CREATED);

        }
    }

    @Override
    public ResponseEntity<Voucher> commitVoucher(int id,Long userId) {
        LOGGER.info("Starting commiting voucher");
        Voucher tempVoucher = voucherRepository.findById(id).orElse(null);
        User tempUser= userRepository.findById(userId).orElse(null);
        if (tempVoucher != null && tempVoucher.getState()== Voucher.Status.RESERVED && tempVoucher.getWhoReserve()==userId ) {
            tempVoucher.setState(Voucher.Status.COMMITED);
            tempVoucher.setUser(tempUser);
            return new ResponseEntity<Voucher>(voucherRepository.save(tempVoucher), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<Voucher>(voucherRepository.save(tempVoucher), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Voucher> getVoucher(int id) {
        LOGGER.info("Starting get voucher");
        Voucher tempVoucher = voucherRepository.findById(id).orElse(null);
        if (tempVoucher == null) {
            return new ResponseEntity<Voucher>(tempVoucher, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Voucher>(tempVoucher, HttpStatus.ACCEPTED);

        }
    }

    @Override
    public ResponseEntity<Voucher> rollBackVoucher(int id,Long userId) {
        LOGGER.info("Starting rollback voucher");
        Voucher tempVoucher = voucherRepository.findById(id).orElse(null);
        if (tempVoucher == null || userId!= tempVoucher.getWhoReserve() || tempVoucher.getState().equals(Voucher.Status.COMMITED))
            return new ResponseEntity<Voucher>(tempVoucher, HttpStatus.NOT_FOUND);
        tempVoucher.setState(Voucher.Status.DEFAULT);
        return new ResponseEntity<Voucher>(voucherRepository.save(tempVoucher), HttpStatus.ACCEPTED);

    }

    @Override
    @Cacheable("vouchers")
    public ResponseEntity<List<Voucher>> getAllVoucher() {
        LOGGER.info("Starting get all voucher");
        return new ResponseEntity<List<Voucher>>((List<Voucher>) voucherRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @Override
    public List<Voucher> getVouchersByPagination(int pageNo, int pageSize) {

        //create pagerequest object
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Voucher> pagingVocuher = voucherRepository.findAll(pageRequest);
        return pagingVocuher.getContent();
    }

}

