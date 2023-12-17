package com.vodafone.spring.batch.config;

import com.vodafone.spring.batch.ImplService.DirectoryListenerService;
import com.vodafone.spring.batch.entity.Voucher;
import com.vodafone.spring.batch.exceptions.NoFileToProcessException;
import com.vodafone.spring.batch.listener.StepSkipListener;
import com.vodafone.spring.batch.repository.IVoucherRepository;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

@Configuration
@EnableBatchProcessing
//@AllArgsConstructor
@RestController
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private IVoucherRepository IVoucherRepository;
    @Autowired
    private VoucherItemWriter VoucherItemWriter;


@Bean
@StepScope
public FlatFileItemReader<Voucher> itemReader(DirectoryListenerService directoryListenerService) {
    FlatFileItemReader<Voucher> flatFileItemReader = new FlatFileItemReader<>();

    try {
        Path latestDetectedFile = directoryListenerService.getLatestDetectedFile();

        if (latestDetectedFile != null) {
            flatFileItemReader.setResource(new FileSystemResource(latestDetectedFile.toFile()));
            System.out.println("I am now reading in batch " + latestDetectedFile.toFile());
            flatFileItemReader.setName("CSV-Reader");
            flatFileItemReader.setLinesToSkip(1);
            flatFileItemReader.setLineMapper(lineMapper());
        } else {
            throw new NoFileToProcessException("No file to be processed in the specified directory.");
        }
    } catch (IllegalArgumentException e) {
        System.err.println("Error creating FlatFileItemReader: " );
        throw new IllegalStateException("Failed to initialize FlatFileItemReader");
    }

    return flatFileItemReader;
}




    private LineMapper<Voucher> lineMapper() {
        DefaultLineMapper<Voucher> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("productId","merchant","description","shortDescription"
                ,"startDate","expireDate","voucherNumber");

        BeanWrapperFieldSetMapper<Voucher> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Voucher.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public VoucherProcessor processor() {
        return new VoucherProcessor();
    }

    @Bean
    public RepositoryItemWriter<Voucher> writer() {
        RepositoryItemWriter<Voucher> writer = new RepositoryItemWriter<>();
        writer.setRepository(IVoucherRepository);
        writer.setMethodName("save");
        return writer;
    }




    @Bean
    public ItemWriteListener<Voucher> myItemWriteListener() {
        return new MyItemWriteListener();
    }
        @Bean
    public Step step1(FlatFileItemReader<Voucher> itemReader) {
        return stepBuilderFactory.get("slaveStep").<Voucher, Voucher>chunk(10)
                .reader(itemReader)
                .processor(processor())
                .writer(VoucherItemWriter)
                .faultTolerant()
                .listener(skipListener())
                .skipPolicy(skipPolicy())
                .listener(myItemWriteListener())
                .build();
    }


    @Bean
    public Job runJob(FlatFileItemReader<Voucher> itemReader) {
        return jobBuilderFactory.get("importVoucher").flow(step1(itemReader)).end().build();
    }


    @Bean
    public SkipPolicy skipPolicy() {
        return new ExceptionSkipPolicy();
    }

    @Bean
    public SkipListener skipListener() {
        return new StepSkipListener();
    }




}
