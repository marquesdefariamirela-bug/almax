package sp.senai.org.controlepatrimonio.exception;

public record FieldErrorResponse(

        String field,
        String message
) {
}
