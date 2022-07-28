package org.example;

import org.example.DataAccess.Dao;
import org.example.DataAccess.SearchCache;
import org.example.Models.Command;
import org.example.Models.Tester;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A service supporting data lookup of testers by search criteria specified by
 * passed in command objects
 */
public class SearchService {

    // Data access object to store the in-memory data for the system
    private Dao mDao;

    // Cache to immediately return results for duplicated commands
    private SearchCache searchCache;

    public SearchService(Dao dao) {
        mDao = dao;
        searchCache = new SearchCache();
    }

    public List<Tester> findTestersByCountryAndDevice(Command command) {

        List<Tester> finalResult = new ArrayList<>();

        // If we have already seen this command before, just return the cached result
        List<Tester> cachedResult = searchCache.containsAndGet(command);
        if(cachedResult != null) {
            return cachedResult;
        }

        // Since commands take in a string device description, get the list of deviceIds for those devices
        List<Long> deviceIdSearch = new ArrayList<>();
        for(String device : command.getDeviceCriteria()) {
            deviceIdSearch.add(mDao.getMDeviceMap().get(device));
        }
        // Get testers by country and device
        List<Tester> result = mDao.getMTesters()
                .stream()
                .filter(tester -> command.isCountryIsAll() ? true : command.getCountryCriteria().stream().anyMatch(item -> item.equals(tester.getCountry())))
                .filter(tester -> command.isDeviceIsAll() ? true : deviceIdSearch.stream().anyMatch(item -> tester.getDevices().contains(item)))
                .collect(Collectors.toList());

        Map<Tester, Long> bugsForTesterByDevices = new HashMap<>();

        for(Tester t : result) {
            bugsForTesterByDevices.put(t, getBugsForTesterByDevice(t, deviceIdSearch));
        }

        for(Map.Entry<Tester, Long> testers : bugsForTesterByDevices.entrySet()) {
            System.out.println("Tester: " +testers.getKey() + "Num Bugs: " + testers.getValue());
        }

        finalResult = mDao.getMTesters()
                .stream()
                .filter(tester -> command.isCountryIsAll() ? true : command.getCountryCriteria().stream().anyMatch(item -> item.equals(tester.getCountry())))
                .filter(tester -> command.isDeviceIsAll() ? true : deviceIdSearch.stream().anyMatch(item -> tester.getDevices().contains(item)))
                .sorted(new Comparator<Tester>() {
                    @Override
                    public int compare(Tester o1, Tester o2) {
                        if(bugsForTesterByDevices.get(o1) > bugsForTesterByDevices.get(o2)){
                            return -1;
                        } else if (bugsForTesterByDevices.get(o1) < bugsForTesterByDevices.get(o2)) {
                            return 1;
                        }
                        return 0;
                    }
                })
                .collect(Collectors.toList());

        searchCache.put(command, finalResult);

        return finalResult;
    }

    private Long getBugsForTesterByDevice(Tester t, List<Long> deviceIds) {
        return mDao.getMBugs()
                .stream()
                .filter(bug -> deviceIds.stream().anyMatch(item -> bug.getDeviceId().equals(item)))
                .filter(bug -> bug.getTesterId().equals(t.getTesterId()))
                .count();
    }
}
