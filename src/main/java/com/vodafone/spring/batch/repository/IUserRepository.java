package com.vodafone.spring.batch.repository;


import com.vodafone.spring.batch.dto.UserDto;
import com.vodafone.spring.batch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
