package sp.senai.org.controlepatrimonio.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sp.senai.org.controlepatrimonio.dto.request.RfidLeituraRequest;
import sp.senai.org.controlepatrimonio.service.RfidService;

@RestController
@RequestMapping("/api/rfid")
@RequiredArgsConstructor
public class RfidController {

    private final RfidService rfidService;

    @PostMapping("/leitura")
    public ResponseEntity<?> receberLeitura(
            @RequestBody RfidLeituraRequest request
    ) {

        return ResponseEntity.ok(
                rfidService.processarLeitura(request)
        );
    }
}
