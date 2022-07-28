package org.example.Utilities;

import org.example.Models.Command;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class exposing a static function to build a command from a line
 * of user input taken from stdin
 */
public class CommandBuilder {
    /**
     * Builds and returns a command object given a line of user input
     * @param inputLine
     * @return
     */
    public static Command buildCommand(String inputLine) {
        // This method is a little fragile

        // Split the criteria into countries and devices
        String[] userCommand = inputLine.split("-");

        // Split each set of countries and devices into single items
        String[] countries = userCommand[0].split(" or ");
        String[] devices = userCommand[1].split(" or ");

        // Trim any extra whitespace if any exists
        for(int i = 0; i<countries.length; i++) {
            countries[i] = countries[i].trim();
        }

        for(int i = 0; i<devices.length; i++) {
            devices[i] = devices[i].trim();
        }

        // If the user has entered ALL for country or devices, set that in the command
        boolean countryAll = Arrays.asList(countries).contains("ALL");
        boolean devicesAll = Arrays.asList(devices).contains("ALL");

        return new Command(List.of(countries), List.of(devices), countryAll, devicesAll);
    }
}
