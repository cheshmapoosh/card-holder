package ir.co.isc.assignment.cardholder.batch;

import ir.co.isc.assignment.cardholder.model.dto.CardDto;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.mapper.CardDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
public class InitDbJobConfiguration {
    public static final String INIT_DB_JOB = "init-db-job";
    public static final String INIT_DB_STEP = "init-db-step";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;
    private final CardDtoMapper cardDtoMapper;
    private final int maxChunkSize;
    private final String sampleDataFile;

    public InitDbJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory,
                                  EntityManagerFactory emf,
                                  CardDtoMapper cardDtoMapper,
                                  @Value("${cardholder.init-db-job.max-chunk-size}") int maxChunkSize,
                                  @Value("${cardholder.init-db-job.sample-data}") String sampleDataFile) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.emf = emf;
        this.cardDtoMapper = cardDtoMapper;
        this.maxChunkSize = maxChunkSize;
        this.sampleDataFile = sampleDataFile;
    }

    @Bean
    public Job initCardHolderDbJob() {
        return this.jobBuilderFactory.get(INIT_DB_JOB)
                .start(initCardHolderDbStep())
                .build();
    }

    @Bean
    public Step initCardHolderDbStep() {
        return stepBuilderFactory.get(INIT_DB_STEP)
                .<CardDto, CardEntity>chunk(maxChunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<CardDto> reader() {
        return new FlatFileItemReaderBuilder<CardDto>()
                .name("personItemReader")
                .resource(new ClassPathResource(sampleDataFile))
                .delimited()
                .names("number",
                        "type",
                        "expireDate",
                        "enable",
                        "issuerIin",
                        "issuerName",
                        "holderNationalCode",
                        "holderFirstName",
                        "holderLastName",
                        "holderCallNumber",
                        "holderAddress",
                        "accountNumber")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CardDto>() {{
                    setTargetType(CardDto.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<CardDto, CardEntity> processor() {
        return cardDtoMapper::mapToCardEntity;
    }

    @Bean
    public JpaItemWriter<CardEntity> writer() {
        JpaItemWriter<CardEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }
}
