package org.calculator.models.db;

import org.calculator.controllers.Coordinator;
import org.calculator.models.data.dto.PersonDTO;

import java.util.HashMap;

public class DBconection {

    public static final HashMap<String, PersonDTO> personsMap = new HashMap<>();
    private Coordinator coordinator;

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}
