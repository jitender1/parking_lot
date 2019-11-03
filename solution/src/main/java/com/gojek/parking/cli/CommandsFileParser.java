package com.gojek.parking.cli;

import com.gojek.parking.exceptions.CommandInputException;
import com.gojek.parking.exceptions.Errors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CommandsFileParser {

    public static void parseInputFile(String filePath) throws CommandInputException {

        File inputFile = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    parseTextInput(line.trim());
                }
            } catch (IOException ex) {
                System.out.println("Error in reading the input file.");
                ex.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found in the path specified.");
            e.printStackTrace();
        }
    }

    public static void parseTextInput(String args) throws CommandInputException {
        String[] params = args.split(" ");
        if (params.length > 0) {
            CommandsHandler.handleRequest(params[0].toLowerCase(), Arrays.copyOfRange(params, 1, params.length));
            return;
        }
        throw new CommandInputException(Errors.INPUT_ERROR_WRONG_COMMAND_FORMAT);
    }
}