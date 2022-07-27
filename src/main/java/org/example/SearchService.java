package org.example;

import org.example.Models.Tester;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    private Dao mDao;

    public SearchService(Dao dao) {
        mDao = dao;
    }

    public List<Tester> findTestersByCountryAndDevice(String country, String deviceName) {
        Long deviceIdSearch = mDao.getDeviceMap().get(deviceName);
        return mDao.getMTesters().stream().filter(tester -> tester.getCountry().equals(country))
                .filter(tester -> tester.getDevices().contains(deviceIdSearch))
                .collect(Collectors.toList());
    }
}
