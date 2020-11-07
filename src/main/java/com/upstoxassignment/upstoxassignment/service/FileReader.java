package com.upstoxassignment.upstoxassignment.service;

import com.oracle.webservices.internal.api.message.PropertySet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


@Component
public class FileReader implements IReader {

    File file;
    Scanner sc;

    @Override
    public String readLine() {
        if (sc.hasNextLine()) {
            return sc.next();
        } else {
            return null;
        }
    }

    public void init(String filepath) {
        file = new File(filepath);
        try {
            sc = new Scanner(file).useDelimiter("\\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
