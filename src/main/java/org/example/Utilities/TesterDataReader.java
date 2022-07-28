package org.example.Utilities;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.example.DataAccess.Dao;
import org.example.Models.Bug;
import org.example.Models.Command;
import org.example.Models.Device;
import org.example.Models.Tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Data reader class to read in csv files into bean objects which will return
 * a populated data access object containing data to be searched
 */
public class TesterDataReader implements IDataReader{
    @Override
    public Dao readData(String dataFolderPath) throws IOException, CsvValidationException, ExecutionException, InterruptedException {
        String[] fileNames = null;

        File f =  new File(dataFolderPath);

        fileNames = f.list();

        if(fileNames == null) {
            throw new FileNotFoundException("Directory provided does not exist. ");
        }

        List<CompletableFuture> futureList = new ArrayList<>();

        // Read Tester Data
        CompletableFuture<List<Tester>> testersRead = CompletableFuture.supplyAsync(() -> {
            FileReader fileT = null;
            List<Tester> testerBeans = null;
            try {
                fileT = new FileReader("src/main/java/org/example/Data/testers.csv");
                testerBeans = new CsvToBeanBuilder<Tester>(fileT)
                    .withType(Tester.class)
                    .build()
                    .parse();

                fileT.close();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
                 return testerBeans;
            });

        futureList.add(testersRead);

        // Read Device Data
        CompletableFuture<List<Device>> devicesRead = CompletableFuture.supplyAsync(() -> {
            FileReader fileD = null;
            List<Device> deviceBeans;
            try {
                fileD = new FileReader("src/main/java/org/example/Data/devices.csv");
                deviceBeans = new CsvToBeanBuilder<Device>(fileD)
                .withType(Device.class)
                .build()
                .parse();

                fileD.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return deviceBeans;
        });
        futureList.add(devicesRead);

        // Populate Device Map
        CompletableFuture<Map<String, Long>> populateDeviceMap = CompletableFuture.supplyAsync(() -> {
            Map<String, Long> deviceMap = new HashMap<>();
            FileReader fileDM = null;
            try {
                fileDM = new FileReader("src/main/java/org/example/Data/devices.csv");

            try(CSVReader csvReader = new CSVReader(fileDM)) {
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

            fileDM.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return deviceMap;
        });

        futureList.add(populateDeviceMap);

        CompletableFuture<List<Bug>> bugsRead = CompletableFuture.supplyAsync(()->{
            FileReader fileB = null;
            List<Bug> beansBugs = null;
            try {
                fileB = new FileReader("src/main/java/org/example/Data/bugs.csv");
                beansBugs = new CsvToBeanBuilder<Bug>(fileB)
                        .withType(Bug.class)
                        .build()
                        .parse();

                fileB.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return beansBugs;
        });

        futureList.add(bugsRead);

        CompletableFuture<Map<Long, Set<Long>>> populateTesterDeviceMap = CompletableFuture.supplyAsync(() -> {
            Map<Long, Set<Long>> testerDeviceMap = new HashMap<>();
            FileReader fileTD = null;
            try {
                fileTD = new FileReader("src/main/java/org/example/Data/tester_device.csv");
                try(CSVReader csvReader = new CSVReader(fileTD)) {
                    String[] values = null;
                    csvReader.readNext();
                    Long val1, val2;
                    while((values = csvReader.readNext()) != null) {
                        val1 = Long.parseLong(values[0]);
                        val2 = Long.parseLong(values[1]);
                        if(testerDeviceMap.containsKey(val1)) {
                            testerDeviceMap.get(val1).add(val2);
                        } else {
                            testerDeviceMap.put(Long.parseLong(values[0]), new HashSet<>(Arrays.asList(val2)));
                        }

                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return testerDeviceMap;
        });

        futureList.add(populateTesterDeviceMap);

        return CompletableFuture.allOf(testersRead, devicesRead, bugsRead, populateDeviceMap, populateTesterDeviceMap)
                .thenApplyAsync(dummy -> {
                    List<Tester> testers = testersRead.join();
                    List<Device> devices = devicesRead.join();
                    List<Bug> bugs = bugsRead.join();
                    Map<String, Long> deviceMap = populateDeviceMap.join();
                    Map<Long, Set<Long>> tdMap = populateTesterDeviceMap.join();
                    for(Map.Entry<Long, Set<Long>> en : tdMap.entrySet()) {
                        Optional<Tester> find = testers.stream().filter(tester -> tester.getTesterId().equals(en.getKey())).findFirst();
                        if(find.isPresent()){
                            find.get().setDevices(en.getValue());
                        }
                    }
                    return new Dao(testers, devices, bugs, deviceMap, tdMap);
                }).get();

    }
}
