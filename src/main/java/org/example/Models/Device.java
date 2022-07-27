package org.example.Models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

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
