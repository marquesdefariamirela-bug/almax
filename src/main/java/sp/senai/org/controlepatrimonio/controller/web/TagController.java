package sp.senai.org.controlepatrimonio.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sp.senai.org.controlepatrimonio.dto.request.TagRequest;
import sp.senai.org.controlepatrimonio.dto.response.TagResponse;
import sp.senai.org.controlepatrimonio.service.ProdutoService;
import sp.senai.org.controlepatrimonio.service.TagService;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final ProdutoService produtoService;

    // =========================================================
    // LISTAR
    // =========================================================
    @GetMapping
    public String listar(Model model) {

        var tags = tagService.listar();

        long totalTags = tags.size();

        long tagsAtivas = tags.stream()
                .filter(tag -> Boolean.TRUE.equals(tag.ativo()))
                .count();

        long tagsVinculadas = tags.stream()
                .filter(tag -> tag.produtoId() != null)
                .count();

        long tagsDisponiveis = tags.stream()
                .filter(tag -> tag.produtoId() == null)
                .count();

        model.addAttribute("tags", tags);

        model.addAttribute("totalTags", totalTags);
        model.addAttribute("tagsAtivas", tagsAtivas);
        model.addAttribute("tagsVinculadas", tagsVinculadas);
        model.addAttribute("tagsDisponiveis", tagsDisponiveis);

        return "tags/lista";
    }

    // =========================================================
    // NOVA TAG
    // =========================================================
    @GetMapping("/nova")
    public String nova(Model model) {

        model.addAttribute(
                "tagRequest",
                new TagRequest(
                        null,
                        null,
                        true,
                        null
                )
        );

        model.addAttribute("modoEdicao", false);
        model.addAttribute("formAction", "/tags");

        carregarProdutos(model);

        return "tags/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {
        TagResponse tag = tagService.buscarPorId(id);

        TagRequest request = new TagRequest(
                tag.epc(),
                tag.codigo(),
                tag.ativo(),
                tag.produtoId()
        );

        model.addAttribute("tagRequest", request);
        model.addAttribute("modoEdicao", true);
        model.addAttribute("formAction", "/tags/" + id);

        carregarProdutos(model);

        return "tags/formulario";
    }

    // =========================================================
    // CRIAR
    // =========================================================

    @PostMapping
    public String criar(
            @Valid @ModelAttribute("tagRequest") TagRequest request,
            RedirectAttributes redirectAttributes
    ) {

        tagService.criar(request);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Tag cadastrada com sucesso!"
        );

        return "redirect:/tags";
    }

    // =========================================================
    // ATUALIZAR
    // =========================================================

    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("tagRequest") TagRequest request,
            RedirectAttributes redirectAttributes
    ) {

        tagService.atualizar(id, request);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Tag atualizada com sucesso!"
        );

        return "redirect:/tags";
    }

    // =========================================================
    // EXCLUIR
    // =========================================================

    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        tagService.deletar(id);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Tag excluída com sucesso!"
        );

        return "redirect:/tags";
    }

    // =========================================================
    // PRODUTOS
    // =========================================================

    private void carregarProdutos(Model model) {

        model.addAttribute(
                "produtos",
                produtoService.listar()
        );
    }

    // =========================================================
    // DETALHES
    // =========================================================

    @GetMapping("/{id}")
    public String detalhes(
            @PathVariable Long id,
            Model model
    ) {

        TagResponse tag = tagService.buscarPorId(id);

        model.addAttribute("tag", tag);

        return "tags/detalhes";
    }
}