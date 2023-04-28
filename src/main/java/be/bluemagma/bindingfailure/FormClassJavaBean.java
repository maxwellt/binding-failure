package be.bluemagma.bindingfailure;

import jakarta.validation.constraints.NotBlank;

public class FormClassJavaBean {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Type type;

    public FormClassJavaBean() {
    }

    public FormClassJavaBean(String firstName, String lastName, Type type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
