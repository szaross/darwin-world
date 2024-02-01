package agh.ics.oop.project.model.util;

import agh.ics.oop.project.model.SimulationConfiguration;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.List;

public class SimulationConfigurationLoader {
    private static final File CONFIG_FILE_PATH = new File("src/main/resources/configurationsCsv.csv");

    public static List<SimulationConfiguration> loadConfigurations() throws FileNotFoundException {
        return new CsvToBeanBuilder<SimulationConfiguration>(new FileReader(CONFIG_FILE_PATH))
                .withType(SimulationConfiguration.class)
                .build()
                .parse();
    }

    public static void saveConfigurations(List<SimulationConfiguration> configurations) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CONFIG_FILE_PATH))) {
            // Create StatefulBeanToCsv
            StatefulBeanToCsv<SimulationConfiguration> beanToCsv = new StatefulBeanToCsvBuilder<SimulationConfiguration>(writer)
                    .withSeparator(',')
                    .build();

            // Write CSV file
            beanToCsv.write(configurations);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
