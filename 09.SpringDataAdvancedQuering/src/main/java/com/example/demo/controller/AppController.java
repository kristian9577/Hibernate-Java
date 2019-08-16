package com.example.demo.controller;

import com.example.demo.domain.repository.LabelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class AppController implements CommandLineRunner {

    private final LabelRepo labelRepo;

    @Autowired
    public AppController(LabelRepo labelRepo) {
        this.labelRepo = labelRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String labelSubtitle=reader.readLine();

        this.labelRepo
                .findAllBySubtitle(labelSubtitle)
                .forEach(label -> System.out.printf("%d  %s <-> %s\r\n",label.getId(),label.getTitle(),label.getSubtitle()));
    }
}
