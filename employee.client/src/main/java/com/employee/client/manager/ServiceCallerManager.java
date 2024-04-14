package com.employee.client.manager;

import com.employee.client.service.OperationsPerformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class ServiceCallerManager {

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceCallerManager.class);
    private final OperationsPerformerService operationsPerformerService;

    @Autowired
    public ServiceCallerManager(OperationsPerformerService operationsPerformerService){
        this.operationsPerformerService = operationsPerformerService;
    }
    public void startProcess(Integer repeatCount) {
        LOGGER.info("Employee Management Service Caller - Started processing...");
        if(repeatCount > 0){
            IntStream.range(0, repeatCount).forEach(stage -> {
                LOGGER.info("Repeating the process at stage: {}", stage);
                start();
                waitForSomeTime(100);
            });
        } else {
            LOGGER.warn("Repeat count should not be 0 or less");
        }
    }

    private void start(){

        operationsPerformerService.insertDummyData();
        waitForSomeTime(10);
        operationsPerformerService.addEmployee();
        waitForSomeTime(10);
        operationsPerformerService.addEmployeesList();
        waitForSomeTime(10);
        operationsPerformerService.updateEmployee();

        waitForSomeTime(10);
        operationsPerformerService.getAll();
        waitForSomeTime(10);
        operationsPerformerService.getWithPagination();
        waitForSomeTime(10);
        operationsPerformerService.getById();
        waitForSomeTime(10);
        operationsPerformerService.getByUsername();

        waitForSomeTime(10);
        operationsPerformerService.deleteById();
        waitForSomeTime(10);
        operationsPerformerService.deleteByUsername();
        waitForSomeTime(10);
        operationsPerformerService.deleteAll();
    }

    private void waitForSomeTime(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException occurred while waiting for seconds: {}", seconds);
        }
    }

    public void endProcess(){
        LOGGER.info("Employee Management Service Caller - End processing...");
    }
}
