package sp.senai.org.controlepatrimonio.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sp.senai.org.controlepatrimonio.dto.request.MovimentacaoRequest;
import sp.senai.org.controlepatrimonio.service.*;

@Controller
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;
    private final ProdutoService produtoService;
    private final AmbienteService ambienteService;
    private final LeitorService leitorService;
    private final UsuarioService usuarioService;


    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "movimentacoes",
                movimentacaoService.listar()
        );

        return "movimentacoes/lista";
    }


    @GetMapping("/novo")
    public String nova(Model model) {

        model.addAttribute(
                "movimentacaoRequest",
                new MovimentacaoRequest(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        carregarDados(model);

        return "movimentacoes/formulario";
    }


    @PostMapping
    public String criar(
            @Valid
            @ModelAttribute("movimentacaoRequest")
            MovimentacaoRequest request,

            BindingResult bindingResult,

            Model model
    ) {

        if (bindingResult.hasErrors()) {

            carregarDados(model);

            return "movimentacoes/formulario";
        }

        movimentacaoService.criar(request);

        return "redirect:/movimentacoes";
    }


    @GetMapping("/{id}")
    public String detalhes(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "movimentacao",
                movimentacaoService.buscarPorId(id)
        );

        return "movimentacoes/detalhes";
    }


    private void carregarDados(Model model) {

        model.addAttribute(
                "produtos",
                produtoService.listar()
        );

        model.addAttribute(
                "ambientes",
                ambienteService.listar()
        );

        model.addAttribute(
                "leitores",
                leitorService.listar()
        );

        model.addAttribute(
                "usuarios",
                usuarioService.listar()
        );

        model.addAttribute(
                "tiposMovimentacao",
                sp.senai.org.controlepatrimonio.model.enums.TipoMovimentacao.values()
        );
    }
}