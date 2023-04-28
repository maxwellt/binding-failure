package be.bluemagma.bindingfailure;

import jakarta.validation.constraints.NotBlank;

public record FormRecord(
        @NotBlank String firstName,
        @NotBlank String lastName,
        Type type
) {}
