package sp.senai.org.controlepatrimonio.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.senai.org.controlepatrimonio.dto.request.AmbienteRequest;
import sp.senai.org.controlepatrimonio.dto.response.AmbienteResponse;
import sp.senai.org.controlepatrimonio.service.AmbienteService;

import java.util.List;

@RestController
@RequestMapping("/api/ambientes")
@RequiredArgsConstructor
public class AmbienteRestController {

    private final AmbienteService ambienteService;

    // =========================================================
    // LISTAR
    // =========================================================

    @GetMapping
    public ResponseEntity<List<AmbienteResponse>> listar() {

        return ResponseEntity.ok(
                ambienteService.listar()
        );
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<AmbienteResponse> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ambienteService.buscarPorId(id)
        );
    }

    // =========================================================
    // CRIAR
    // =========================================================

    @PostMapping
    public ResponseEntity<AmbienteResponse> criar(
            @Valid @RequestBody AmbienteRequest request
    ) {

        AmbienteResponse ambiente =
                ambienteService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ambiente);
    }

    // =========================================================
    // ATUALIZAR
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<AmbienteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AmbienteRequest request
    ) {

        AmbienteResponse ambiente =
                ambienteService.atualizar(id, request);

        return ResponseEntity.ok(ambiente);
    }

    // =========================================================
    // EXCLUIR
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {

        ambienteService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}