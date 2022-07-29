package org.example;

import org.example.Models.*;
import org.example.Utilities.CommandBuilder;
import org.example.Utilities.IDataReader;
import org.example.Utilities.TesterDataReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        System.out.println("DeviceCritera examples: {iPhone 4} {iPhone4 or iPhone 5} {ALL}  (Omit brackets)");
        System.out.println("Type QUIT to quit.");
        // Main Program Loop
        while(!userQuit) {
            String userInput = inputReader.readLine();
            if(userInput.equals("QUIT")) {
                userQuit = true;
            } else {
                Command cmd = CommandBuilder.buildCommand( userInput );
                System.out.println(searchService.findTestersByCriteria(cmd));
            }
        }

    }


}

