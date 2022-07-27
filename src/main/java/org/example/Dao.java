package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Models.Bug;
import org.example.Models.Device;
import org.example.Models.Tester;

import java.util.HashMap;
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

    private Map<String, Long> deviceMap;

    private Map<Long, Set<Long>> mTesterDeviceMap;

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
