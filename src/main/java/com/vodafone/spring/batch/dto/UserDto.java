package com.vodafone.spring.batch.dto;

import com.vodafone.spring.batch.entity.Voucher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private List<Voucher> vouchers;
}
