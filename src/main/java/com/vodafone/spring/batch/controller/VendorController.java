package com.vodafone.spring.batch.controller;


import com.vodafone.spring.batch.dto.vendorDto;
import com.vodafone.spring.batch.entity.Vendor;
import com.vodafone.spring.batch.service.IVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class VendorController {

    @Autowired
    private IVendorService vendorService;


    @PostMapping("/addvendor")
    public ResponseEntity<vendorDto> addVendor (@RequestBody Vendor vendor){
        return ResponseEntity.ok(vendorService.addVendor(vendor));
    }


    @GetMapping("getvendor/{id}")
    public ResponseEntity<vendorDto> getVendor (@PathVariable Long id){
        return ResponseEntity.ok(vendorService.getVendor(id));
    }

    @GetMapping("/getallvendor")
    public ResponseEntity<List<Vendor>> getAllVendor() {
        return ResponseEntity.ok(vendorService.getAllVendor());

    }

    @DeleteMapping("deletevendor/{id}")
    public ResponseEntity<vendorDto> deleteVendor(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.deleteVendor(id));

    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PatchMapping("updatevendor/{id}")
    public ResponseEntity<vendorDto> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.updateVendor(id,vendor));

    }
}
