package org.example;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.Models.Bug;
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
        List<Tester> testersByCountry = searchService.findTestersByCountryAndDevice("US", "iPhone 4");
        for(Tester t : testersByCountry) {
            System.out.println(t.toString());
        }
    }


}

