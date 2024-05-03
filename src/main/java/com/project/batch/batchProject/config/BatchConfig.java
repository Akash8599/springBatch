package com.project.batch.batchProject.config;

import com.project.batch.batchProject.processor.StudentProcessor;
import com.project.batch.batchProject.student.Student;
import com.project.batch.batchProject.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public FlatFileItemReader<Student> itemReader(){
        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/student1.csv"));
        flatFileItemReader.setName("csvReader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }


    @Bean
    public StudentProcessor processor(){
        return new StudentProcessor();
    }

    @Bean
    public RepositoryItemWriter<Student> write(){
        RepositoryItemWriter<Student> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(studentRepository);
        itemWriter.setMethodName("save");
        return itemWriter;

    }

    @Bean
    public Step importStep(){
        return new StepBuilder("csvImport", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(write())
                .build();
    }

    @Bean
    public Job runJob(){
        return new JobBuilder("importStudents", jobRepository)
                .start(importStep())
                .build();
    }
    private LineMapper<Student> lineMapper() {
        DefaultLineMapper<Student> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "first_name", "last_name", "age");
        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;

    }
}
