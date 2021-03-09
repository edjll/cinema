package ru.edjll.cinema.validation.user.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdentityPasswordValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdentityPassword {

    String message() default "error.validation.user.password.identity";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
