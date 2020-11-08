package com.upstoxassignment.upstoxassignment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.upstoxassignment.upstoxassignment.service.*;
import com.upstoxassignment.upstoxassignment.workers.Worker1FileReader;
import com.upstoxassignment.upstoxassignment.workers.Worker2OHLCCalculator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
@ComponentScan(basePackages = "com.*")
public class UpstoxassignmentApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(UpstoxassignmentApplication.class, args);
//        IReader filereader = app.getBean(FileReader.class);
//        IOHLCService iohlcService = app.getBean(OHLCServiceimpl.class);
//        SharedDataService sharedDataService = app.getBean(SharedDataService.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        objectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
//
//        ObserverImpl o = new ObserverImpl(observerName, observeSymbol);
//        SubjectImpl subject = app.getBean(SubjectImpl.class);
//        subject.add(o);
//        File file = null;
//        try {
//            file = ResourceUtils.getFile(filename);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Worker1FileReader worker1FileReader = new Worker1FileReader(iohlcService, filereader, objectMapper, file.getAbsolutePath());
//        worker1FileReader.start();
////        try {
////            worker1FileReader.join();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        Worker2OHLCCalculator worker2OHLCCalculator = new Worker2OHLCCalculator
//                (iohlcService, sharedDataService, app.getEnvironment().getProperty("interval"));
//        worker2OHLCCalculator.start();
    }

    @Bean
    public CommandLineRunner run(ApplicationContext app) {
        return args -> {
            if (args.length > 0) {
                String filename = args[0];
                String observerName = args[1];
                String observeSymbol = args[2];

//            ApplicationContext app = SpringApplication.run(UpstoxassignmentApplication.class, args);
                IReader filereader = app.getBean(FileReader.class);
                IOHLCService iohlcService = app.getBean(OHLCServiceimpl.class);
                SharedDataService sharedDataService = app.getBean(SharedDataService.class);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

                ObserverImpl o = new ObserverImpl(observerName, observeSymbol);
                SubjectImpl subject = app.getBean(SubjectImpl.class);
                subject.add(o);
                File file = null;
                try {
                    file = ResourceUtils.getFile(filename);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Worker1FileReader worker1FileReader = new Worker1FileReader(iohlcService, filereader, objectMapper, file.getAbsolutePath());
                worker1FileReader.setName("Reader");
                worker1FileReader.start();
//                try {
//                    worker1FileReader.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Worker2OHLCCalculator worker2OHLCCalculator = new Worker2OHLCCalculator
                        (iohlcService, sharedDataService, app.getEnvironment().getProperty("interval"));
                worker2OHLCCalculator.setName("Calculator");
                worker2OHLCCalculator.start();
            }
        };
    }
}
