package org.example.Models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

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
