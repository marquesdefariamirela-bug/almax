package sp.senai.org.controlepatrimonio.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sp.senai.org.controlepatrimonio.dto.request.AmbienteRequest;
import sp.senai.org.controlepatrimonio.dto.response.AmbienteResponse;
import sp.senai.org.controlepatrimonio.service.AmbienteService;
import sp.senai.org.controlepatrimonio.service.ProdutoService;

@Controller
@RequestMapping("/ambientes")
@RequiredArgsConstructor
public class AmbienteController {

    private final AmbienteService ambienteService;
    private final ProdutoService produtoService;

    @GetMapping
    public String listarAmbientes(Model model) {

        var ambientes = ambienteService.listar();

        long totalAmbientes = ambientes.size();

        long ambientesAtivos = ambientes.stream()
                .filter(ambiente -> Boolean.TRUE.equals(ambiente.ativo()))
                .count();

        long ambientesInativos = ambientes.stream()
                .filter(ambiente -> Boolean.FALSE.equals(ambiente.ativo()))
                .count();

        model.addAttribute("ambientes", ambientes);
        model.addAttribute("totalAmbientes", totalAmbientes);
        model.addAttribute("ambientesAtivos", ambientesAtivos);
        model.addAttribute("ambientesInativos", ambientesInativos);

        return "ambientes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute(
                "ambienteRequest",
                novoAmbienteRequest()
        );

        carregarDadosFormulario(model);

        return "ambientes/formulario";
    }

    @PostMapping
    public String criar(

            @Valid
            @ModelAttribute("ambienteRequest")
            AmbienteRequest request,

            BindingResult result,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            carregarDadosFormulario(model);
            return "ambientes/formulario";
        }

        ambienteService.criar(request);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Ambiente cadastrado com sucesso!"
        );

        return "redirect:/ambientes";
    }

    @GetMapping("/{id}")
    public String detalhes(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "ambiente",
                ambienteService.buscarPorId(id)
        );

        return "ambientes/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(

            @PathVariable Long id,
            Model model
    ) {

        AmbienteResponse ambiente =
                ambienteService.buscarPorId(id);

        AmbienteRequest request =
                new AmbienteRequest(
                        ambiente.nome(),
                        ambiente.descricao(),
                        ambiente.ativo()
                );

        model.addAttribute(
                "ambienteRequest",
                request
        );

        model.addAttribute(
                "ambienteId",
                id
        );

        carregarDadosFormulario(model);

        return "ambientes/formulario";
    }

    @PostMapping("/{id}")
    public String atualizar(

            @PathVariable Long id,

            @Valid
            @ModelAttribute("ambienteRequest")
            AmbienteRequest request,

            BindingResult result,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "ambienteId",
                    id
            );

            carregarDadosFormulario(model);

            return "ambientes/formulario";
        }

        ambienteService.atualizar(
                id,
                request
        );

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Ambiente atualizado com sucesso!"
        );

        return "redirect:/ambientes";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {

        ambienteService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    private void carregarDadosFormulario(Model model) {

        model.addAttribute(
                "ambientes",
                ambienteService.listar()
        );

        model.addAttribute(
                "produtos",
                produtoService.listar()
        );
    }

    private AmbienteRequest novoAmbienteRequest() {
        return new AmbienteRequest(
                "",
                "",
                true
        );
    }
}