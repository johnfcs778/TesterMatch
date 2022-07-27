package org.example;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvBadConverterException;
import lombok.Setter;
import org.example.Models.Bug;
import org.example.Models.Device;
import org.example.Models.Tester;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Hello world!");
        //List<String[]> funTest = readLineByLineExample();

//        File file = new File("./src/main/java/org/example/Data");
//        for(String fileNames : file.list()) System.out.println(fileNames);
        Dao dao = new Dao();


        FileReader file = new FileReader("src/main/java/org/example/Data/testers.csv");
        List<Tester> beans = new CsvToBeanBuilder<Tester>(file)
                .withType(Tester.class)
                .build()
                .parse();
        dao.setMTesters(beans);
//        for(Tester t : beans) {
//            System.out.println(t.toString());
//        }
        file.close();

        file = new FileReader("src/main/java/org/example/Data/devices.csv");
        List<Device> beansDevices = new CsvToBeanBuilder<Device>(file)
                .withType(Device.class)
                .build()
                .parse();

//        for(Device t : beansDevices) {
//            System.out.println(t.toString());
//        }

        dao.setMDevices(beansDevices);
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
        for(Map.Entry<String, Long> en : deviceMap.entrySet()) {
            System.out.println(en.getKey() + ":" + en.getValue());
        }
        dao.setDeviceMap(deviceMap);

        file.close();
        file = new FileReader("src/main/java/org/example/Data/bugs.csv");
        List<Bug> beansBugs = new CsvToBeanBuilder<Bug>(file)
                .withType(Bug.class)
                .build()
                .parse();

//        for(Bug t : beansBugs) {
//            System.out.println(t.toString());
//        }
        dao.setMBugs(beansBugs);

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
            System.out.println(en.getKey() + ":" + en.getValue());
            Optional<Tester> find = dao.getMTesters().stream().filter(tester -> tester.getTesterId().equals(en.getKey())).findFirst();
            if(find.isPresent()){
                System.out.println("here");
                find.get().setDevices(en.getValue());
            }
        }
        dao.setMTesterDeviceMap(map);

        SearchService searchService = new SearchService(dao);
        List<Tester> testersByCountry = searchService.findTestersByCountryAndDevice("US", "iPhone 4");
        for(Tester t : testersByCountry) {
            System.out.println(t.toString());
        }
    }

//    public static List<String[]> readLineByLine(Path filePath) throws Exception {
//        List<String[]> list = new ArrayList<>();
//        try (Reader reader = Files.newBufferedReader(filePath)) {
//            try (CSVReader csvReader = new CSVReader(reader)) {
//                String[] line;
//                while ((line = csvReader.readNext()) != null) {
//                    list.add(line);
//                }
//            }
//        }
//        return list;
//    }
//
//    public static List<String[]> readLineByLineExample() throws Exception {
//        Path path = Paths.get(
//                ClassLoader.getSystemResource("TesterMatch/src/main/java/org.example/Data/testers.csv").toURI());
//        return readLineByLine(path);
//    }
}

