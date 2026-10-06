package sp.senai.org.controlepatrimonio.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.senai.org.controlepatrimonio.dto.request.TagRequest;
import sp.senai.org.controlepatrimonio.dto.response.TagResponse;
import sp.senai.org.controlepatrimonio.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagService tagService;

    // =========================================================
    // LISTAR
    // =========================================================

    @GetMapping
    public ResponseEntity<List<TagResponse>> listar() {

        return ResponseEntity.ok(
                tagService.listar()
        );
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tagService.buscarPorId(id)
        );
    }

    // =========================================================
    // CRIAR
    // =========================================================

    @PostMapping
    public ResponseEntity<TagResponse> criar(
            @Valid @RequestBody TagRequest request
    ) {

        TagResponse response =
                tagService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================================================
    // ATUALIZAR
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request
    ) {

        TagResponse response =
                tagService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // EXCLUIR
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {

        tagService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}