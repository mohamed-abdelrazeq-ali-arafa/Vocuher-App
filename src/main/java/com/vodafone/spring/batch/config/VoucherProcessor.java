package com.vodafone.spring.batch.config;


import com.vodafone.spring.batch.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class VoucherProcessor implements ItemProcessor<Voucher, Voucher> {
    String filePath = "src/main/resources/DataInValid/invalid.csv";
    String headers = "productId,merchant,description,shortDescription,startDate,expireDate,voucherNumber";


    private static final Logger LOGGER = LoggerFactory.getLogger(Voucher.class);


    public void writeInvalid(Voucher voucher) throws IOException {
        LOGGER.info("writing invalid voucher information to the file");
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(filePath), true))) {
            writer.write(headers);
            writer.println();
            writer.println(voucher.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public Voucher process(Voucher voucher) throws Exception {

        if (!validateVoucherLength(voucher)   ) {
            writeInvalid(voucher);
            return null;
        }

        if(!isValidDate(voucher.getStartDate()) || !isValidDate(voucher.getExpireDate())) {
                writeInvalid(voucher);
                return null;
        }
        return voucher;

    }


    public boolean validateVoucherLength(Voucher voucher){

        if (checkExccedMaxLength(voucher.getMerhcant(),20)
                ||checkExccedMaxLength(voucher.getDescription(),30)
                ||checkExccedMaxLength(voucher.getShortDescription(),500)
                ||checkExccedMaxLength(voucher.getVoucherNumber(),20)
        )
            return false;
        return true;

    }


    public boolean checkExccedMaxLength(String str,int maxLnegth){
        return str.length()>maxLnegth;
    }


    public static boolean isValidDate(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        try {
            LocalDate date = LocalDate.parse(inputDate, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
