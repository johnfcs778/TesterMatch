package org.example.Models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

/**
 * Data model representing a tester's device
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Device {
    @CsvBindByName
    private Long deviceId;
    @CsvBindByName
    private String description;

}
