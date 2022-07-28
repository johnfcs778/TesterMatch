package org.example;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.Models.Bug;
import org.example.Models.Command;
import org.example.Models.Device;
import org.example.Models.Tester;
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
        SearchService searchService = new SearchService( reader.readData("src/main/java/org/example/Data"));

        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Welcome to Tester Match");
        System.out.println("Please enter commmands followed by a keyboard enter");
        System.out.println("Please enter commands in the format of CountryCriteria-DeviceCriteria(separated by a single dash)");
        System.out.println("CountryCriteria examples: {US} {US or JP} {ALL}  (Omit brackets)");
        System.out.println("DeviceCritera examples: {iPhone 4} {iPhone5 or iPhone 5} {ALL}  (omit brackets)");
        // Main Program Loop
        while(!userQuit) {
            String[] userCommand = inputReader.readLine().split("-");
            //@TODO extract this command object creation to function or class
            String[] countries = userCommand[0].split("or");
            String[] devices = userCommand[1].split("or");
            for(int i = 0; i<countries.length; i++) {
                countries[i] = countries[i].trim();
            }

            for(int i = 0; i<devices.length; i++) {
                devices[i] = devices[i].trim();
            }
            Command cmd = new Command(List.of(countries), List.of(devices), false, false);
            List<Tester> testersByCountry = searchService.findTestersByCountryAndDevice(cmd);
            for(Tester t : testersByCountry) {
                System.out.println(t.toString());
            }

        }
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

