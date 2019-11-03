package com.gojek.parking.cli;

import org.junit.Assert;
import org.junit.Test;

public class CommandsFileParserTest {

    @Test
    public void testFileParser() {
        try {
            CommandsFileParser.parseInputFile("src/test/java/com/gojek/parking/cli/input_command_file");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testFileParserWithInvalidPath() throws  Exception {
        try {
            CommandsFileParser.parseInputFile("ghgh");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().contains("No such file or directory"));
        }
    }
}