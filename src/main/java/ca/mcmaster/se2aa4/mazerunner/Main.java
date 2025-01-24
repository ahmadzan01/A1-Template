package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        System.out.println(" Starting Maze Runner");
        Options options = new Options();
        options.addOption("i", true,  "Input maze file");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("i")){
                String inputFile = cmd.getOptionValue("i");
                logger.info("** Reading the maze from file ", inputFile);
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            logger.info("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            logger.info("PASS ");
                        }
                    }
                    logger.info(System.lineSeparator());
                }
            }else{
                logger.error("Wrong format require option: -i");
            }
            }
        catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        
        System.out.println("** Computing path");
        System.out.println("PATH NOT COMPUTED");
        System.out.println(" End of MazeRunner");
    }
}