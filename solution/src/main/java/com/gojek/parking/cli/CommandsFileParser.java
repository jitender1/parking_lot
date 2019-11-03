package com.gojek.parking.cli;

import com.gojek.parking.exceptions.CommandInputException;
import com.gojek.parking.exceptions.Errors;
import com.gojek.parking.exceptions.ServiceException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CommandsFileParser {

    public static void parseInputFile(String filePath) throws FileNotFoundException, CommandInputException, ServiceException, IOException {

        File inputFile = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = br.readLine()) != null) {
            parseTextInput(line.trim());
        }
    }

    public static void parseTextInput(String args) throws CommandInputException, ServiceException {
        String[] params = args.split(" ");
        if (params.length > 0) {
            CommandsHandler.handleRequest(params[0].toLowerCase(), Arrays.copyOfRange(params, 1, params.length));
            return;
        }
    }
}