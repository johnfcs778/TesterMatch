package org.example.DataAccess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Models.Bug;
import org.example.Models.Device;
import org.example.Models.Tester;

import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class Dao {
    private List<Tester> mTesters;
    private List<Device> mDevices;
    private List<Bug> mBugs;

    private Map<String, Long> mDeviceMap;

    private Map<Long, Set<Long>> mTesterDeviceMap;

    public Dao(List<Tester> testers, List<Device> devices, List<Bug> bugs, Map<String, Long> deviceMap, Map<Long, Set<Long>> testerDeviceMap ) {
        this.mTesters = testers;
        this.mDevices = devices;
        this.mBugs = bugs;
        this.mDeviceMap = deviceMap;
        this.mTesterDeviceMap = testerDeviceMap;
    }

    public void addTester(Tester tester) {
        mTesters.add(tester);
    }

    public void addDevice(Device device) {
        mDevices.add(device);
    }

    public void addBug(Bug bug) {
        mBugs.add(bug);
    }
}
