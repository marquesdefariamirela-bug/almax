package sp.senai.org.controlepatrimonio.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.senai.org.controlepatrimonio.dto.request.MovimentacaoRequest;
import sp.senai.org.controlepatrimonio.dto.response.MovimentacaoResponse;
import sp.senai.org.controlepatrimonio.service.MovimentacaoService;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoRestController {

    private final MovimentacaoService movimentacaoService;

    // =========================================================
    // LISTAR
    // =========================================================

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponse>> listar() {

        return ResponseEntity.ok(
                movimentacaoService.listar()
        );
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoResponse> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                movimentacaoService.buscarPorId(id)
        );
    }

    // =========================================================
    // CRIAR
    // =========================================================

    @PostMapping
    public ResponseEntity<MovimentacaoResponse> criar(
            @Valid @RequestBody MovimentacaoRequest request
    ) {

        MovimentacaoResponse response =
                movimentacaoService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}