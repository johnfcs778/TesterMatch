package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SearchResult {
    private List<Tester> testersInOrder;
    private List<Integer> testerBugDataInOrder;
}
