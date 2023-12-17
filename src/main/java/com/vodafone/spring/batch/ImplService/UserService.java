package com.vodafone.spring.batch.ImplService;



import com.vodafone.spring.batch.dto.UserDto;
import com.vodafone.spring.batch.entity.User;
import com.vodafone.spring.batch.exceptions.ResourceNotFoundException;
import com.vodafone.spring.batch.exceptions.ResourseDuplicateException;
import com.vodafone.spring.batch.repository.IUserRepository;
import com.vodafone.spring.batch.repository.IVoucherRepository;
import com.vodafone.spring.batch.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IVoucherRepository voucherRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDto addUser(User user) {
        LOGGER.info("Starting add User");
        User tempUser = userRepository.findByUsername(user.getUsername());
        if (tempUser!=null)
            throw new ResourseDuplicateException("There is user with this username");
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return this.modelMapper.map(user, UserDto.class);
        }

    }

    @Override
    public UserDto getUser(Long id) {
        LOGGER.info("Starting GET  User");
        Optional<User> tempUser = userRepository.findById(id);
        if (tempUser.isPresent()) {
            UserDto userDto = this.modelMapper.map(tempUser.get(), UserDto.class);
            return userDto;
        }
        else {
            throw new ResourceNotFoundException("Not Found");
        }
    }

    public boolean authenticate(User user) {
        LOGGER.info("Starting Authenticate  User");

        User storedUser = userRepository.findByUsername(user.getUsername());
        if (storedUser != null  && passwordEncoder.matches(user.getPassword(),storedUser.getPassword())) {

            return true;
        } else {
            // Authentication failed
            return false;
        }
    }


}
