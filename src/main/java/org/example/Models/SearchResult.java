package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Object to contain data for a SearchResult returned by a SearchService
 */
@AllArgsConstructor
@Getter
@Setter
public class SearchResult {
    private List<Tester> testersInOrder;
    private List<Integer> testerBugDataInOrder;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(int i =0; i<testersInOrder.size(); i++) {
            builder.append(testersInOrder.get(i).toString());
            builder.append(" has filed: ");
            builder.append(testerBugDataInOrder.get(i));
            builder.append("bugs for the given device(s) \n");
        }

        return builder.toString();
    }
}
