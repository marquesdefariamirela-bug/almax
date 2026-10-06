package sp.senai.org.controlepatrimonio.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sp.senai.org.controlepatrimonio.dto.request.ProdutoRequest;
import sp.senai.org.controlepatrimonio.dto.response.ProdutoResponse;
import sp.senai.org.controlepatrimonio.model.enums.StatusProduto;
import sp.senai.org.controlepatrimonio.service.AmbienteService;
import sp.senai.org.controlepatrimonio.service.ProdutoService;
import sp.senai.org.controlepatrimonio.service.TagService;

import java.util.List;

@Controller
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final AmbienteService ambienteService;
    private final TagService tagService;


    // =========================================================
    // LISTAGEM
    // =========================================================

    @GetMapping
    public String listarProdutos(Model model) {

        List<ProdutoResponse> produtos =
                produtoService.listar();

        model.addAttribute(
                "produtos",
                produtos
        );

        // =========================================================
        // INDICADORES
        // =========================================================

        model.addAttribute(
                "totalProdutos",
                produtoService.contar()
        );

        model.addAttribute(
                "totalAtivos",
                produtoService.contarPorStatus(
                        StatusProduto.ATIVO
                )
        );

        model.addAttribute(
                "totalManutencao",
                produtoService.contarPorStatus(
                        StatusProduto.MANUTENCAO
                )
        );

        model.addAttribute(
                "totalInativos",
                produtoService.contarPorStatus(
                        StatusProduto.INATIVO
                )
        );


        // =========================================================
        // FILTRO — STATUS
        // =========================================================

        model.addAttribute(
                "statusList",
                StatusProduto.values()
        );


        // =========================================================
        // FILTRO — AMBIENTES
        // =========================================================

        model.addAttribute(
                "ambientes",
                ambienteService.listar()
        );


        return "produtos/lista";
    }



    // =========================================================
    // TELA DE NOVO PRODUTO
    // =========================================================

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute(
                "produtoRequest",
                novoProdutoRequest()
        );

        carregarDadosFormulario(model);

        return "produtos/formulario";
    }


    // =========================================================
    // CADASTRAR
    // =========================================================

    @PostMapping
    public String criar(

            @Valid
            @ModelAttribute("produtoRequest")
            ProdutoRequest request,

            BindingResult result,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            carregarDadosFormulario(model);

            return "produtos/formulario";
        }

        produtoService.criar(request);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Patrimônio cadastrado com sucesso!"
        );

        return "redirect:/produtos";
    }


    // =========================================================
    // DETALHES
    // =========================================================

    @GetMapping("/{id}")
    public String detalhes(

            @PathVariable Long id,

            Model model
    ) {

        model.addAttribute(
                "produto",
                produtoService.buscarPorId(id)
        );

        return "produtos/detalhes";
    }


    // =========================================================
    // TELA DE EDIÇÃO
    // =========================================================

    @GetMapping("/{id}/editar")
    public String editar(

            @PathVariable Long id,

            Model model
    ) {

        ProdutoResponse produto =
                produtoService.buscarPorId(id);

        ProdutoRequest request =
                new ProdutoRequest(

                        produto.numeroPatrimonio(),

                        produto.nome(),

                        produto.descricao(),

                        produto.marca(),

                        produto.modelo(),

                        produto.numeroSerie(),

                        produto.valor(),

                        produto.dataAquisicao(),

                        produto.ambienteId(),

                        produto.tagId()
                );

        model.addAttribute(
                "produtoRequest",
                request
        );

        model.addAttribute(
                "produtoId",
                id
        );

        carregarDadosFormulario(model);

        return "produtos/formulario";
    }


    // =========================================================
    // ATUALIZAR
    // =========================================================

    @PostMapping("/{id}")
    public String atualizar(

            @PathVariable Long id,

            @Valid
            @ModelAttribute("produtoRequest")
            ProdutoRequest request,

            BindingResult result,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "produtoId",
                    id
            );

            carregarDadosFormulario(model);

            return "produtos/formulario";
        }

        produtoService.atualizar(
                id,
                request
        );

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Patrimônio atualizado com sucesso!"
        );

        return "redirect:/produtos";
    }

    // =========================================================
    // EXCLUIR
    // =========================================================

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {

        produtoService.deletar(id);

        return ResponseEntity.noContent().build();
    }


    // =========================================================
    // CARREGAR DADOS DOS SELECTS
    // =========================================================

    private void carregarDadosFormulario(Model model) {

        model.addAttribute(
                "ambientes",
                ambienteService.listar()
        );

        model.addAttribute(
                "tags",
                tagService.listarDisponiveis()
        );
    }


    // =========================================================
    // REQUEST VAZIO
    // =========================================================

    private ProdutoRequest novoProdutoRequest() {

        return new ProdutoRequest(

                null, // numeroPatrimonio
                null, // nome
                null, // descricao
                null, // marca
                null, // modelo
                null, // numeroSerie
                null, // valor
                null, // dataAquisicao
                null, // ambienteId
                null  // tagId
        );
    }
}