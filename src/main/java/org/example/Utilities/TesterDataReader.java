package org.example.Utilities;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.example.DataAccess.Dao;
import org.example.Models.Bug;
import org.example.Models.Device;
import org.example.Models.Tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Data reader class to read in csv files into bean objects which will return
 * a populated data access object containing data to be searched
 */
public class TesterDataReader implements IDataReader{
    @Override
    public Dao readData(String dataFolderPath) throws IOException, CsvValidationException {
        String[] fileNames = null;

        File f =  new File(dataFolderPath);

        fileNames = f.list();

        if(fileNames == null) {
            throw new FileNotFoundException("Directory provided does not exist. ");
        }

        // @TODO: Use reflection to make this code DRY
        FileReader file = new FileReader("src/main/java/org/example/Data/testers.csv");
        List<Tester> beans = new CsvToBeanBuilder<Tester>(file)
                .withType(Tester.class)
                .build()
                .parse();

        file.close();

        file = new FileReader("src/main/java/org/example/Data/devices.csv");
        List<Device> beansDevices = new CsvToBeanBuilder<Device>(file)
                .withType(Device.class)
                .build()
                .parse();


        file.close();

        Map<String, Long> deviceMap = new HashMap<>();
        file = new FileReader("src/main/java/org/example/Data/devices.csv");
        try(CSVReader csvReader = new CSVReader(file)) {
            String[] values = null;
            csvReader.readNext();
            Long val1;
            String val2;
            while((values = csvReader.readNext()) != null) {
                val1 = Long.parseLong(values[0]);
                val2 = values[1];
                if(!deviceMap.containsKey(val1)) {
                    deviceMap.put(val2, val1);
                }

            }
        }

        file.close();
        file = new FileReader("src/main/java/org/example/Data/bugs.csv");
        List<Bug> beansBugs = new CsvToBeanBuilder<Bug>(file)
                .withType(Bug.class)
                .build()
                .parse();



        Map<Long, Set<Long>> map = new HashMap<>();
        file = new FileReader("src/main/java/org/example/Data/tester_device.csv");
        try(CSVReader csvReader = new CSVReader(file)) {
            String[] values = null;
            csvReader.readNext();
            Long val1, val2;
            while((values = csvReader.readNext()) != null) {
                val1 = Long.parseLong(values[0]);
                val2 = Long.parseLong(values[1]);
                if(map.containsKey(val1)) {
                    map.get(val1).add(val2);
                } else {
                    map.put(Long.parseLong(values[0]), new HashSet<>(Arrays.asList(val2)));
                }

            }
        }
        for(Map.Entry<Long, Set<Long>> en : map.entrySet()) {
            Optional<Tester> find = beans.stream().filter(tester -> tester.getTesterId().equals(en.getKey())).findFirst();
            if(find.isPresent()){
                find.get().setDevices(en.getValue());
            }
        }

        return new Dao(beans, beansDevices, beansBugs, deviceMap, map);

    }
}
