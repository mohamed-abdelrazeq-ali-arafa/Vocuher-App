package com.vodafone.spring.batch.service;



import com.vodafone.spring.batch.dto.UserDto;
import com.vodafone.spring.batch.entity.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    UserDto addUser(User user);
    UserDto getUser(Long id);
    boolean authenticate(User user);
//User commitVoucherForUser(Long userId, int voucherId);

}
