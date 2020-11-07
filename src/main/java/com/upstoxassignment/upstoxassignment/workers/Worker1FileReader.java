package com.upstoxassignment.upstoxassignment.workers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.upstoxassignment.upstoxassignment.pojo.Trade;
import com.upstoxassignment.upstoxassignment.service.IOHLCService;
import com.upstoxassignment.upstoxassignment.service.IReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Worker1FileReader extends Thread {

    IOHLCService iohlcService;

    IReader iReader;

    ObjectMapper objectMapper;

    String filepath;

    public Worker1FileReader(IOHLCService iohlcService, IReader iReader, ObjectMapper objectMapper, String filepath) {
        this.iohlcService = iohlcService;
        this.iReader = iReader;
        this.objectMapper = objectMapper;
        this.filepath = filepath;
    }

    public Worker1FileReader() {
    }

    @Override
    public void run() {

        iReader.init(filepath);
        while (true) {
            String line = iReader.readLine();
            if (line == null) {
                break;
            }
            Trade trade = null;
            try {
                trade = objectMapper.readValue(line, Trade.class);
                iohlcService.insertTrade(trade);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
