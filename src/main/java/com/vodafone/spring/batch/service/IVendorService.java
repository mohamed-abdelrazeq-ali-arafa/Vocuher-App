package com.vodafone.spring.batch.service;



import com.vodafone.spring.batch.dto.vendorDto;
import com.vodafone.spring.batch.entity.Vendor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IVendorService {
    vendorDto addVendor(Vendor vendor);
    vendorDto getVendor(Long id);
    List<Vendor> getAllVendor();
    vendorDto deleteVendor(Long id);
    vendorDto updateVendor(Long id, Vendor vendor);

}

