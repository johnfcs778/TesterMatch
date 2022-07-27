package org.example.Models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tester {
    @CsvBindByName
    private Long testerId;
    @CsvBindByName
    private String firstName;
    @CsvBindByName
    private String lastName;
    @CsvBindByName
    private String country;
    @CsvBindByName
    private String lastLogin;
    private Set<Long> devices;

}
