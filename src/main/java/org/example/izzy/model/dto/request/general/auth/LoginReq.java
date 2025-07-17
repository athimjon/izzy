package org.example.izzy.model.dto.request.general.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginReq(
        @NotBlank(message = "Phone Number is required!")
        @Pattern(regexp = "^\\+998(90|91|93|94|50|55|87|88|97|95|99|77|98|33)\\d{7}$",
                message = "Phone number is in an invalid format, e.g., +998 90 123 45 67")
        String phoneNumber,
        @NotBlank(message = "Password is required!")
        @Size(min = 4, message = "Password must be at least 4 characters")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{4,}$",
                message = "Password must contain at least one letter and one number, and be at least 4 characters long")
        String password

) {
}
