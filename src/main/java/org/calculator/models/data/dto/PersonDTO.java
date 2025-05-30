package org.calculator.models.data.dto;

public class PersonDTO {

    private String document;
    private String name;
    private int age;

    public PersonDTO() {}

    public PersonDTO(String document, String name, int age) {
        this.document = document;
        this.name = name;
        this.age = age;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("PersonaDTO [documento=%s, nombre=%s, edad=%d]", document, name, age);
    }
}
