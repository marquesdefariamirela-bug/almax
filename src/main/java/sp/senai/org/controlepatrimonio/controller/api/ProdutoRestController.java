package sp.senai.org.controlepatrimonio.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.senai.org.controlepatrimonio.dto.request.ProdutoRequest;
import sp.senai.org.controlepatrimonio.dto.response.ProdutoResponse;
import sp.senai.org.controlepatrimonio.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoRestController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {
        return ResponseEntity.ok(
                produtoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                produtoService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(
            @Valid @RequestBody ProdutoRequest request
    ) {

        ProdutoResponse response =
                produtoService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Long id,

            @Valid @RequestBody ProdutoRequest request
    ) {

        return ResponseEntity.ok(
                produtoService.atualizar(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {
        produtoService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rfid/{tagRfid}")
    public ResponseEntity<ProdutoResponse> buscarPorTagRfid(
            @PathVariable String tagRfid
    ) {
        return ResponseEntity.ok(
                produtoService.buscarPorTagRfid(tagRfid)
        );
    }
}
