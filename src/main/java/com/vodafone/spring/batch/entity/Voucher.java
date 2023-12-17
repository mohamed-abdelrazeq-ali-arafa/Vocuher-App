package com.vodafone.spring.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VOUCHER_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {



    @Id
    private int productId;
    private String merhcant;
    private String description;
    private String shortDescription;
    private String startDate;
    private String expireDate;
    private String voucherNumber;
    public enum Status {

        DEFAULT,
        RESERVED,
        COMMITED;
    }

    @Enumerated(EnumType.ORDINAL)
    private Status state= Status.DEFAULT;


    @Override
    public String toString(){
        return  this.getProductId()+","+this.getMerhcant()+","+this.getDescription()+","+this.getShortDescription()
                +" "+this.getStartDate()+","+this.getExpireDate()+","+this.voucherNumber;
    }



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    private Long whoReserve;
}
