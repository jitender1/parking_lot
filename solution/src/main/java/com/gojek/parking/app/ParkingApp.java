package com.gojek.parking.app;

import com.gojek.parking.cli.CommandsFileParser;
import com.gojek.parking.exceptions.CommandInputException;
import com.gojek.parking.exceptions.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParkingApp {

    public static void main(String... args) {

        switch (args.length) {
            case 0:
                System.out.println("Please enter 'exit' to quit");
                System.out.println("Waiting for input...");

                for (; ; ) {
                    try {
                        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                        String inputString = bufferRead.readLine();
                        if (inputString.equalsIgnoreCase("exit")) {
                            break;

                        } else if ((inputString == null) || (inputString.isEmpty())) {

                        } else {
                            CommandsFileParser.parseTextInput(inputString.trim());
                        }
                    } catch (IOException e) {
                        System.out.println("Oops! Error in reading the input from console.");
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                CommandsFileParser.parseInputFile(args[0]);
                break;
            default:
                String filePath = "src/main/resources/sample_input.txt";
                CommandsFileParser.parseInputFile(filePath);
        }
    }
}