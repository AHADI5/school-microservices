package com.ushirk.schools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StudentManagement {
    public static void main(String[] args) {
        SpringApplication.run(StudentManagement.class , args);
    }
}