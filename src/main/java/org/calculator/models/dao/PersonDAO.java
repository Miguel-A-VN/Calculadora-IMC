package org.calculator.models.dao;

import org.calculator.controllers.Coordinator;
import org.calculator.models.data.dto.PersonDTO;
import org.calculator.models.db.DBconection;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private Coordinator coordinator;

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    /**
     * Registra una persona si no existe previamente.
     * @param person DTO con datos de la persona
     * @return "si" si se registró correctamente, "no" si ya existe
     */
    public String personRegister(PersonDTO person) {
        if (!DBconection.personsMap.containsKey(person.getDocument())) {
            DBconection.personsMap.put(person.getDocument(), person);
            return "si";
        }
        return "no";
    }

    /**
     * Busca una persona por su documento.
     * @param document documento de la persona
     * @return DTO de la persona o null si no existe
     */
    public PersonDTO consultPersonDocument(String document) {
        return DBconection.personsMap.getOrDefault(document, null);
    }

    /**
     * Obtiene una lista de todas las personas registradas.
     * @return lista con todos los DTOs
     */
    public List<PersonDTO> consultListPersons() {
        return new ArrayList<>(DBconection.personsMap.values());
    }

    /**
     * Actualiza la información de una persona.
     * @param person DTO con datos actualizados
     * @return "ok" si actualizó, "error" si no existe
     */
    public String personUpdate(PersonDTO person) {
        if (DBconection.personsMap.containsKey(person.getDocument())) {
            DBconection.personsMap.put(person.getDocument(), person);
            return "ok";
        }
        return "error";
    }

    /**
     * Elimina una persona por documento.
     * @param document documento de la persona
     * @return "ok" si eliminó, "error" si no existe
     */
    public String personDelete(String document) {
        if (DBconection.personsMap.containsKey(document)) {
            DBconection.personsMap.remove(document);
            return "ok";
        }
        return "error";
    }
}
