package com.upstoxassignment.upstoxassignment.service;

import org.springframework.stereotype.Component;

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
