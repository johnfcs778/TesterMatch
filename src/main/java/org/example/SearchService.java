package org.example;

import org.example.DataAccess.Dao;
import org.example.DataAccess.SearchCache;
import org.example.Models.Command;
import org.example.Models.SearchResult;
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

    public SearchResult findTestersByCriteria(Command command) {


        // If we have already seen this command before, just return the cached result
        SearchResult cachedResult = searchCache.containsAndGet(command);
        if(cachedResult != null) {
            return cachedResult;
        }

        // Since commands take in a string device description, get the list of deviceIds for those devices
        // for ease of lookup
        List<Long> deviceIdsToSearch = new ArrayList<>();
        for(String device : command.getDeviceCriteria()) {
            deviceIdsToSearch.add(mDao.getMDeviceMap().get(device));
        }

        // Perform a search based on the country and device criteria
        List<Tester> countryDeviceFilteredResults = getTestersByCountryandDevice(deviceIdsToSearch, command);

        Map<Tester, Long> bugsForTesterByDevices = new HashMap<>();
        List<Integer> testerBugDataInOrder = new ArrayList<>();

        // Populate a map of tester id to number of bugs to use when sorting
        for(Tester t : countryDeviceFilteredResults) {
            bugsForTesterByDevices.put(t, getBugsForTesterByDevice(t, deviceIdsToSearch, command));
        }

        // This sorting could be done in the initial search but populating a map and comparing
        // using the map constant lookup is quicker than calling the bugLookup in the comparator everytime
        List<Tester> resultsOrderedByExperience = countryDeviceFilteredResults.stream().sorted((o1, o2) -> {
                    if(bugsForTesterByDevices.get(o1) > bugsForTesterByDevices.get(o2)){
                        return -1;
                    } else if (bugsForTesterByDevices.get(o1) < bugsForTesterByDevices.get(o2)) {
                        return 1;
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        // Create a list of testerBugData in order to attach to the SearchResult
        for(Tester t : resultsOrderedByExperience) {
            testerBugDataInOrder.add(Math.toIntExact(bugsForTesterByDevices.get(t)));
        }

        // Create the search result, add it to the cache and return it
        SearchResult res = new SearchResult(resultsOrderedByExperience, testerBugDataInOrder);
        searchCache.put(command, res);

        return res;
    }

    /**
     * Returns a list of testers filtered by command criteria
     * @param deviceIdsToSearch
     * @param command
     * @return
     */
    private List<Tester> getTestersByCountryandDevice(List<Long> deviceIdsToSearch, Command command) {
        // Perform a search based on the country and device criteria
        return mDao.getMTesters()
                .stream()
                .filter(tester -> command.isCountryIsAll() ? true : command.getCountryCriteria().stream().anyMatch(item -> item.equals(tester.getCountry())))
                .filter(tester -> command.isDeviceIsAll() ? true : deviceIdsToSearch.stream().anyMatch(item -> tester.getDevices().contains(item)))
                .collect(Collectors.toList());
    }

    /**
     * Returns the amount of bugs a tester has filed for a given set of devices
     * @param t
     * @param deviceIds
     * @param command
     * @return
     */
    private Long getBugsForTesterByDevice(Tester t, List<Long> deviceIds, Command command) {
        return mDao.getMBugs()
                .stream()
                .filter(bug -> command.isDeviceIsAll() ? true : deviceIds.stream().anyMatch(item -> bug.getDeviceId().equals(item)))
                .filter(bug -> bug.getTesterId().equals(t.getTesterId()))
                .count();
    }
}
