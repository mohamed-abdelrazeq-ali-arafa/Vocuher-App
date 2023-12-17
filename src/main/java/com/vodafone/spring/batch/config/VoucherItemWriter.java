package com.vodafone.spring.batch.config;

import com.vodafone.spring.batch.ImplService.DirectoryListenerService;
import com.vodafone.spring.batch.entity.Voucher;
import com.vodafone.spring.batch.repository.IVoucherRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class VoucherItemWriter implements ItemWriter<Voucher> {

    @Autowired
    private IVoucherRepository repository;

    @Override
    public void write(List<? extends Voucher> list) throws Exception {
        repository.saveAll(list);
    }
}
