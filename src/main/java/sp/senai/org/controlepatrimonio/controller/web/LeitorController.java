package sp.senai.org.controlepatrimonio.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sp.senai.org.controlepatrimonio.dto.request.LeitorRequest;
import sp.senai.org.controlepatrimonio.service.AmbienteService;
import sp.senai.org.controlepatrimonio.service.LeitorService;

@Controller
@RequestMapping("/leitores")
@RequiredArgsConstructor
public class LeitorController {

    private final LeitorService leitorService;
    private final AmbienteService ambienteService;

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "leitores",
                leitorService.listar()
        );

        return "leitores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        LeitorRequest request = new LeitorRequest(
                "",
                "",
                "",
                "",
                "",
                true,
                null
        );

        model.addAttribute("leitorRequest", request);
        model.addAttribute("modoEdicao", false);
        model.addAttribute("formAction", "/leitores");

        carregarAmbientes(model);

        return "leitores/formulario";
    }

    @PostMapping
    public String criar(
            @Valid @ModelAttribute("leitorRequest")
            LeitorRequest request,
            RedirectAttributes redirectAttributes
    ) {

        leitorService.criar(request);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Leitor cadastrado com sucesso!"
        );

        return "redirect:/leitores";
    }

    @GetMapping("/{id}/editar")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {

        var leitor = leitorService.buscarPorId(id);

        Long ambienteId = null;

        if (leitor.ambienteNome() != null) {
            ambienteId = leitor.ambienteId();
        }

        LeitorRequest request = new LeitorRequest(
                leitor.nome(),
                leitor.identificador(),
                leitor.ip(),
                leitor.fabricante(),
                leitor.modelo(),
                leitor.ativo(),
                ambienteId
        );

        model.addAttribute("leitorRequest", request);
        model.addAttribute("modoEdicao", true);
        model.addAttribute("formAction", "/leitores/" + id);

        carregarAmbientes(model);

        return "leitores/formulario";
    }

    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("leitorRequest")
            LeitorRequest request,
            RedirectAttributes redirectAttributes
    ) {

        leitorService.atualizar(id, request);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Leitor atualizado com sucesso!"
        );

        return "redirect:/leitores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        leitorService.deletar(id);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Leitor excluído com sucesso!"
        );

        return "redirect:/leitores";
    }

    private void carregarAmbientes(Model model) {

        model.addAttribute(
                "ambientes",
                ambienteService.listar()
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

        model.addAttribute(
                "leitor",
                leitorService.buscarPorId(id)
        );

        return "leitores/detalhes";
    }
}
