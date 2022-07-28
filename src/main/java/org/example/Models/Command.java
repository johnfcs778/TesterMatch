package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Command {
    private List<String> countryCriteria;
    private List<String> deviceCriteria;
    private boolean countryIsAll;
    private boolean deviceIsAll;
}
