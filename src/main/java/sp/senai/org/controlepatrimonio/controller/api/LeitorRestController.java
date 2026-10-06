package sp.senai.org.controlepatrimonio.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.senai.org.controlepatrimonio.dto.request.LeitorRequest;
import sp.senai.org.controlepatrimonio.dto.response.LeitorResponse;
import sp.senai.org.controlepatrimonio.service.LeitorService;

import java.util.List;

@RestController
@RequestMapping("/api/leitores")
@RequiredArgsConstructor
public class LeitorRestController {

    private final LeitorService leitorService;

    // =========================================================
    // LISTAR
    // =========================================================

    @GetMapping
    public ResponseEntity<List<LeitorResponse>> listar() {

        return ResponseEntity.ok(
                leitorService.listar()
        );
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<LeitorResponse> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                leitorService.buscarPorId(id)
        );
    }

    // =========================================================
    // CRIAR
    // =========================================================

    @PostMapping
    public ResponseEntity<LeitorResponse> criar(
            @Valid @RequestBody LeitorRequest request
    ) {

        LeitorResponse leitor =
                leitorService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leitor);
    }

    // =========================================================
    // ATUALIZAR
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<LeitorResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LeitorRequest request
    ) {

        LeitorResponse leitor =
                leitorService.atualizar(id, request);

        return ResponseEntity.ok(leitor);
    }

    // =========================================================
    // EXCLUIR
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {

        leitorService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}