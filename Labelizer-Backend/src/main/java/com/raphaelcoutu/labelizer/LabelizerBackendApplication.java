package com.raphaelcoutu.labelizer;

import com.raphaelcoutu.labelizer.repository.RefreshableCrudRepositoryImpl;
import com.raphaelcoutu.labelizer.service.ReportService;
import com.raphaelcoutu.labelizer.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableCrudRepositoryImpl.class)
public class LabelizerBackendApplication implements CommandLineRunner {

    @Resource
    StorageService storageService;

    @Resource
    ReportService reportService;

    public static void main(String[] args) {
        SpringApplication.run(LabelizerBackendApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        storageService.init();
        reportService.init();
    }
}
