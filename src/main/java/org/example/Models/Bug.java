package org.example.Models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

/**
 * Data model representing a filed bug
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Bug {
    @CsvBindByName
    private Long bugId;
    @CsvBindByName
    private Long deviceId;
    @CsvBindByName
    private Long testerId;
}
