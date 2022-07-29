# Tester Match App

This application will read in csv data for devices, testers, and bugs and then allow users to enter in commands which will return Testers that match the search Criteria. Testers will be returned in order based on experience, where experience is defined as the number of bugs the tester has filed with the provided set of devices.


## Search Criteria

Users can search Testers by providing criteria containing the tester's country as well as any devices the tester has used. Commands should be entered with country criteria followed by device criteria separated by a dash. Countries and devices should be listed (Case-Sensitive) and separated by "or" surrounded by two spaces. The keyword "ALL" can also be entered for both country and device to get results for all countries or all devices. 

## Command examples

 - ALL-ALL 
 - US-iPhone 4
 - US or GB-iPhone 4 or iPhone 5
 - US or GB or JP-iPhone 4 or iPhone 4S or iPhone 5 or Galaxy S3 or Galaxy S4 or Nexus 4 or Droid Razor or Droid DNA or HTC One or iPhone 3

## Sample Run

    Welcome to Tester Match
    Please enter commmands followed by a keyboard enter
    Please enter commands in the format of CountryCriteria-DeviceCriteria(separated by a single dash)
    CountryCriteria examples: {US} {US or JP} {ALL}  (Omit brackets)
    DeviceCritera examples: {iPhone 4} {iPhone4 or iPhone 5} {ALL}  (Omit brackets)
    ALL-ALL
    Tester(testerId=4, firstName=Taybin, lastName=Rutkin, country=US, lastLogin=2013-01-01 10:57:38, devices=[1, 2]) has filed: 125bugs for the given device(s) 
    Tester(testerId=7, firstName=Lucas, lastName=Lowry, country=JP, lastLogin=2013-07-12 23:57:38, devices=[4, 5, 6, 7, 8]) has filed: 117bugs for the given device(s) 
    Tester(testerId=8, firstName=Sean, lastName=Wellington, country=JP, lastLogin=2013-08-05 13:27:38, devices=[1, 3, 6, 9, 10]) has filed: 116bugs for the given device(s) 
    Tester(testerId=1, firstName=Miguel, lastName=Bautista, country=US, lastLogin=2013-08-04 23:57:38, devices=[1, 2, 3, 10]) has filed: 114bugs for the given device(s) 
    Tester(testerId=6, firstName=Stanley, lastName=Chen, country=GB, lastLogin=2013-08-04 21:57:38, devices=[3]) has filed: 110bugs for the given device(s) 
    Tester(testerId=5, firstName=Mingquan, lastName=Zheng, country=JP, lastLogin=2013-08-04 22:07:38, devices=[1, 5, 6, 7, 10]) has filed: 109bugs for the given device(s) 
    Tester(testerId=3, firstName=Leonard, lastName=Sutton, country=GB, lastLogin=2013-07-16 21:17:28, devices=[3, 4, 5, 6]) has filed: 106bugs for the given device(s) 
    Tester(testerId=9, firstName=Darshini, lastName=Thiagarajan, country=GB, lastLogin=2013-08-05 15:00:38, devices=[5, 6, 8, 9]) has filed: 104bugs for the given device(s) 
    Tester(testerId=2, firstName=Michael, lastName=Lubavin, country=US, lastLogin=2013-07-12 13:27:18, devices=[4, 5, 6, 7, 8, 9]) has filed: 99bugs for the given device(s) 
    
    US-iPhone 4
    Tester(testerId=4, firstName=Taybin, lastName=Rutkin, country=US, lastLogin=2013-01-01 10:57:38, devices=[1, 2]) has filed: 66bugs for the given device(s) 
    Tester(testerId=1, firstName=Miguel, lastName=Bautista, country=US, lastLogin=2013-08-04 23:57:38, devices=[1, 2, 3, 10]) has filed: 23bugs for the given device(s) 
    US or GB-iPhone 4 or iPhone 5
    Tester(testerId=6, firstName=Stanley, lastName=Chen, country=GB, lastLogin=2013-08-04 21:57:38, devices=[3]) has filed: 110bugs for the given device(s) 
    Tester(testerId=4, firstName=Taybin, lastName=Rutkin, country=US, lastLogin=2013-01-01 10:57:38, devices=[1, 2]) has filed: 66bugs for the given device(s) 
    Tester(testerId=1, firstName=Miguel, lastName=Bautista, country=US, lastLogin=2013-08-04 23:57:38, devices=[1, 2, 3, 10]) has filed: 53bugs for the given device(s) 
    Tester(testerId=3, firstName=Leonard, lastName=Sutton, country=GB, lastLogin=2013-07-16 21:17:28, devices=[3, 4, 5, 6]) has filed: 32bugs for the given device(s) 
    
    US or GB or JP-iPhone 4 or iPhone 4S or iPhone 5 or Galaxy S3 or Galaxy S4 or Nexus 4 or Droid Razor or Droid DNA or HTC One or iPhone 3
    Tester(testerId=4, firstName=Taybin, lastName=Rutkin, country=US, lastLogin=2013-01-01 10:57:38, devices=[1, 2]) has filed: 125bugs for the given device(s) 
    Tester(testerId=7, firstName=Lucas, lastName=Lowry, country=JP, lastLogin=2013-07-12 23:57:38, devices=[4, 5, 6, 7, 8]) has filed: 117bugs for the given device(s) 
    Tester(testerId=8, firstName=Sean, lastName=Wellington, country=JP, lastLogin=2013-08-05 13:27:38, devices=[1, 3, 6, 9, 10]) has filed: 116bugs for the given device(s) 
    Tester(testerId=1, firstName=Miguel, lastName=Bautista, country=US, lastLogin=2013-08-04 23:57:38, devices=[1, 2, 3, 10]) has filed: 114bugs for the given device(s) 
    Tester(testerId=6, firstName=Stanley, lastName=Chen, country=GB, lastLogin=2013-08-04 21:57:38, devices=[3]) has filed: 110bugs for the given device(s) 
    Tester(testerId=5, firstName=Mingquan, lastName=Zheng, country=JP, lastLogin=2013-08-04 22:07:38, devices=[1, 5, 6, 7, 10]) has filed: 109bugs for the given device(s) 
    Tester(testerId=3, firstName=Leonard, lastName=Sutton, country=GB, lastLogin=2013-07-16 21:17:28, devices=[3, 4, 5, 6]) has filed: 106bugs for the given device(s) 
    Tester(testerId=9, firstName=Darshini, lastName=Thiagarajan, country=GB, lastLogin=2013-08-05 15:00:38, devices=[5, 6, 8, 9]) has filed: 104bugs for the given device(s) 
    Tester(testerId=2, firstName=Michael, lastName=Lubavin, country=US, lastLogin=2013-07-12 13:27:18, devices=[4, 5, 6, 7, 8, 9]) has filed: 99bugs for the given device(s)
