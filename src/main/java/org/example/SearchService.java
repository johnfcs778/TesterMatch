package org.example;

import org.example.Models.Tester;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchService {
    private Dao mDao;

    public SearchService(Dao dao) {
        mDao = dao;
    }

    public List<Tester> findTestersByCountryAndDevice(String country, String deviceName) {
        Long deviceIdSearch = mDao.getMDeviceMap().get(deviceName);
        // Get testers by country and device
        List<Tester> result = mDao.getMTesters()
                .stream()
                .filter(tester -> tester.getCountry().equals(country))
                .filter(tester -> tester.getDevices().contains(deviceIdSearch))
                .collect(Collectors.toList());

        Map<Tester, Long> bugsForTesterByDevices = new HashMap<>();

        for(Tester t : result) {
            Long numBugsForTesterDevice = mDao.getMBugs()
                    .stream()
                    .filter(bug -> bug.getDeviceId().equals(deviceIdSearch))
                    .filter(bug -> bug.getTesterId().equals(t.getTesterId()))
                    .count();
            bugsForTesterByDevices.put(t, numBugsForTesterDevice);
        }

        for(Map.Entry<Tester, Long> testers : bugsForTesterByDevices.entrySet()) {
            System.out.println("Tester: " +testers.getKey() + "Num Bugs: " + testers.getValue());
        }

        return mDao.getMTesters()
                .stream()
                .filter(tester -> tester.getCountry().equals(country))
                .filter(tester -> tester.getDevices().contains(deviceIdSearch))
                .collect(Collectors.toList());
    }
}
