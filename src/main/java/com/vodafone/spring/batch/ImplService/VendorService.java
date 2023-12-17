package com.vodafone.spring.batch.ImplService;


import com.vodafone.spring.batch.dto.vendorDto;
import com.vodafone.spring.batch.entity.User;
import com.vodafone.spring.batch.entity.Vendor;
import com.vodafone.spring.batch.exceptions.ResourceNotFoundException;
import com.vodafone.spring.batch.exceptions.ResourseDuplicateException;
import com.vodafone.spring.batch.repository.IVendorRepository;
import com.vodafone.spring.batch.service.IVendorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService implements IVendorService {
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    @Autowired
    private IVendorRepository vendorRepository;


    @Override
    public vendorDto addVendor(Vendor vendor) {
        LOGGER.info("Starting add Vendor");
        Vendor tempVendor = vendorRepository.findByName(vendor.getName());
        if (tempVendor!=null)
            throw new ResourseDuplicateException("There is vendor with this username");
        else {
            vendorRepository.save(vendor);
            return this.modelMapper.map(vendor, vendorDto.class);
        }
    }

    @Override
    public vendorDto getVendor(Long id) {
        LOGGER.info("Starting Geting Vendor");
        Optional<Vendor> tempVendor= Optional.ofNullable(vendorRepository.findById(id).orElse(null));
        if(!tempVendor.isPresent())
            throw  new ResourceNotFoundException("There is No Vendor with this id");
        else
            return this.modelMapper.map(tempVendor.get(), vendorDto.class);


    }

    @Override
    public List<Vendor> getAllVendor() {
        LOGGER.info("Starting get All  Vendor");
       return vendorRepository.findAll();

    }

    @Override
    public vendorDto deleteVendor(Long id) {
        LOGGER.info("Starting delete Vendor");
        Optional<Vendor> tempVendor = Optional.ofNullable(vendorRepository.findById(id).orElse(null));
        if (!tempVendor.isPresent())
            throw new ResourceNotFoundException("There is No Vendor with this id");
        else {
            vendorRepository.deleteById(id);
            return this.modelMapper.map(tempVendor.get(), vendorDto.class);
        }
    }

    @Override
    public vendorDto updateVendor(Long id, Vendor newVendor) {
        LOGGER.info("Starting update Vendor");
        Optional<Vendor> tempVendor = Optional.ofNullable(vendorRepository.findById(id).orElse(null));
        if (!tempVendor.isPresent())
            throw new ResourceNotFoundException("There is No Vendor with this id");
        else {
            if(newVendor.getName()!=null)
                tempVendor.get().setName(newVendor.getName());
            if(newVendor.getStatus()!=null)
                tempVendor.get().setStatus(newVendor.getStatus());
            vendorRepository.save(tempVendor.get());
            return this.modelMapper.map(tempVendor.get(), vendorDto.class);
        }

    }
}
