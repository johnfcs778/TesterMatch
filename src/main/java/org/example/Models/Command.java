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

    /**
     * Override the equals to not compare object reference but check for deep equality
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if( o == this) {
            return true;
        }

        if(!(o instanceof Command)) {
            return false;
        }

        if(this.countryIsAll == ((Command) o).countryIsAll &&
        this.deviceIsAll == ((Command) o).deviceIsAll &&
        this.countryCriteria.equals(((Command) o).countryCriteria) &&
        this.deviceCriteria.equals(((Command) o).deviceCriteria)) {
            return true;
        } else {
            return false;
        }
    }
}
