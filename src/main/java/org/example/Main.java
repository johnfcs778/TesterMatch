package org.example;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.Models.*;
import org.example.Utilities.CommandBuilder;
import org.example.Utilities.IDataReader;
import org.example.Utilities.TesterDataReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        boolean userQuit = false;

        IDataReader reader = new TesterDataReader();
        long start1 = System.currentTimeMillis();
        SearchService searchService = new SearchService( reader.readData("src/main/java/org/example/Data"));
        long end1 = System.currentTimeMillis();
        System.out.println("Elapsed Time in milli seconds: "+ (end1-start1));

        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Welcome to Tester Match");
        System.out.println("Please enter commmands followed by a keyboard enter");
        System.out.println("Please enter commands in the format of CountryCriteria-DeviceCriteria(separated by a single dash)");
        System.out.println("CountryCriteria examples: {US} {US or JP} {ALL}  (Omit brackets)");
        System.out.println("DeviceCritera examples: {iPhone 4} {iPhone5 or iPhone 5} {ALL}  (Omit brackets)");
        // Main Program Loop
        while(!userQuit) {
            Command cmd = CommandBuilder.buildCommand( inputReader.readLine());
            long start2 = System.currentTimeMillis();
            System.out.println(searchService.findTestersByCountryAndDevice(cmd));
            long end2 = System.currentTimeMillis();
            System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));

        }
//        Command cmd = new Command(List.of("US"), List.of("iPhone 4"), false, true);
//        SearchResult testersByCountry = searchService.findTestersByCountryAndDevice(cmd);
//        System.out.println(testersByCountry);
//
//        cmd = new Command(List.of("US"), List.of("iPhone 4"), true, false);
//        SearchResult testersByCountry2 = searchService.findTestersByCountryAndDevice(cmd);
//        System.out.println(testersByCountry);
    }


}

