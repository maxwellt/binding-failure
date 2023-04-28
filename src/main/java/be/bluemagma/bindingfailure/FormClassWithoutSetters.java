package be.bluemagma.bindingfailure;

import jakarta.validation.constraints.NotBlank;

public class FormClassWithoutSetters {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Type type;

    public FormClassWithoutSetters() {
    }

    public FormClassWithoutSetters(String firstName, String lastName, Type type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Type getType() {
        return type;
    }
}
