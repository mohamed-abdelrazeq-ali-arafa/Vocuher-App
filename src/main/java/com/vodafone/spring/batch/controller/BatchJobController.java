package com.vodafone.spring.batch.controller;

import com.vodafone.spring.batch.ImplService.VoucherService;
import com.vodafone.spring.batch.entity.Voucher;
import com.vodafone.spring.batch.repository.IVoucherRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class BatchJobController {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private IVoucherRepository voucherRepository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private JobRepository jobRepository;



    @GetMapping("/Vouchers")
    public ResponseEntity<List<Voucher>> getAll() {
        return voucherService.getAllVoucher();
    }



    @GetMapping("/paginatevoucher")
    public List<Voucher> getUserWithPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize){

        return voucherService.getVouchersByPagination(pageNo,pageSize);

    }


    @GetMapping("getvoucher/{id}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable int id) {
        return  voucherService.getVoucher(id);
    }

    @GetMapping("/getallvoucher")
    public ResponseEntity<List<Voucher>> getAllVoucher() {
        return voucherService.getAllVoucher();
    }

    //cannot make voucher commited unless its reserved
    @PutMapping ("commitvoucher/{id}/{userId}")
    public ResponseEntity<Voucher> commitVoucher(@PathVariable int id,@PathVariable Long userId) {
        return voucherService.commitVoucher(id,userId);
    }


    @PutMapping ("rollbackvoucher/{id}/{userId}")
    public ResponseEntity<Voucher> rollBackVoucher(@PathVariable int id, @PathVariable Long userId) {
        return voucherService.rollBackVoucher(id, userId);
    }


    @PutMapping ("reservevoucher/{id}/{userid}")
    public ResponseEntity<Voucher> reserveVoucher(@PathVariable int id,@PathVariable Long userid) {
        return voucherService.reserveVoucher(id,userid);
    }














}
