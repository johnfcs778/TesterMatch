package org.example;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.Models.Bug;
import org.example.Models.Command;
import org.example.Models.Device;
import org.example.Models.Tester;
import org.example.Utilities.IDataReader;
import org.example.Utilities.TesterDataReader;

import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Hello world!");

        IDataReader reader = new TesterDataReader();

        SearchService searchService = new SearchService( reader.readData("src/main/java/org/example/Data"));
        Command cmd = new Command(List.of("US"), List.of("iPhone 4"), true, false);
        List<Tester> testersByCountry = searchService.findTestersByCountryAndDevice(cmd);
        for(Tester t : testersByCountry) {
            System.out.println(t.toString());
        }

        cmd = new Command(List.of("US"), List.of("iPhone 4"), true, false);
        List<Tester> testersByCountry2 = searchService.findTestersByCountryAndDevice(cmd);
        for(Tester t : testersByCountry2) {
            System.out.println(t.toString());
        }
    }


}

