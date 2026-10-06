package sp.senai.org.controlepatrimonio.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RfidLeituraRequest(

        @NotBlank
        String uid,

        @NotBlank
        String deviceId
) {
}
