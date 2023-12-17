package com.vodafone.spring.batch.controller;


import com.vodafone.spring.batch.dto.UserDto;
import com.vodafone.spring.batch.entity.User;
import com.vodafone.spring.batch.entity.Voucher;
import com.vodafone.spring.batch.repository.IUserRepository;
import com.vodafone.spring.batch.service.IUserService;
import com.vodafone.spring.batch.service.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/adduser")
    public ResponseEntity<UserDto> addUser(@RequestBody User user) {
        return  ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (userService.authenticate(user)) {
            // Authentication successful
            return ResponseEntity.ok("Login successful");
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/commit/{id1}/{id2}")
    public ResponseEntity<Voucher> commit(@PathVariable int id1, @PathVariable Long id2) {
        return voucherService.commitVoucher(id1, id2);
    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
