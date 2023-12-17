package com.vodafone.spring.batch.config;

import com.vodafone.spring.batch.ImplService.DirectoryListenerService;
import com.vodafone.spring.batch.entity.Voucher;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class MyItemWriteListener implements ItemWriteListener<Voucher> {
    private DirectoryListenerService directoryListenerService;

    @Autowired
    public void setDirectoryListenerService(DirectoryListenerService directoryListenerService) {
        this.directoryListenerService = directoryListenerService;
    }


    @Override
    public void beforeWrite(List<? extends Voucher> list) {

    }

    @Override
    public void afterWrite(List<? extends Voucher> list) {
        directoryListenerService.renameLastDetectedFile();
    }

    @Override
    public void onWriteError(Exception e, List<? extends Voucher> list) {

    }


}
