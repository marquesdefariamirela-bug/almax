package sp.senai.org.controlepatrimonio.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sp.senai.org.controlepatrimonio.dto.request.UsuarioRequest;
import sp.senai.org.controlepatrimonio.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    // =========================================================
    // LISTAGEM
    // =========================================================

    @GetMapping
    public String listar(Model model) {

        var usuarios = usuarioService.listar();

        long totalUsuarios = usuarios.size();

        long usuariosAtivos = usuarios.stream()
                .filter(usuario -> Boolean.TRUE.equals(usuario.ativo()))
                .count();

        long usuariosInativos = usuarios.stream()
                .filter(usuario -> Boolean.FALSE.equals(usuario.ativo()))
                .count();

        model.addAttribute(
                "usuarios",
                usuarios
        );

        model.addAttribute(
                "totalUsuarios",
                totalUsuarios
        );

        model.addAttribute(
                "usuariosAtivos",
                usuariosAtivos
        );

        model.addAttribute(
                "usuariosInativos",
                usuariosInativos
        );

        return "usuarios/lista";
    }


    // =========================================================
    // NOVO
    // =========================================================

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute(
                "usuarioRequest",
                new UsuarioRequest(
                        null,
                        null,
                        null,
                        true,
                        null
                )
        );

        model.addAttribute(
                "modoEdicao",
                false
        );

        return "usuarios/formulario";
    }


    // =========================================================
    // CRIAR
    // =========================================================

    @PostMapping
    public String criar(

            @Valid
            @ModelAttribute("usuarioRequest")
            UsuarioRequest request,

            BindingResult result,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "modoEdicao",
                    false
            );

            return "usuarios/formulario";
        }

        usuarioService.criar(request);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Usuário cadastrado com sucesso!"
        );

        return "redirect:/usuarios";
    }


    // =========================================================
    // EDITAR
    // =========================================================

    @GetMapping("/{id}/editar")
    public String editar(

            @PathVariable Long id,

            Model model
    ) {

        var usuario = usuarioService.buscarPorId(id);

        UsuarioRequest request =
                new UsuarioRequest(
                        usuario.nome(),
                        usuario.email(),
                        null,
                        usuario.ativo(),
                        usuario.perfil()
                );

        model.addAttribute(
                "usuarioRequest",
                request
        );

        model.addAttribute(
                "usuarioId",
                id
        );

        model.addAttribute(
                "modoEdicao",
                true
        );

        return "usuarios/formulario";
    }


    // =========================================================
    // ATUALIZAR
    // =========================================================

    @PostMapping("/{id}")
    public String atualizar(

            @PathVariable Long id,

            @Valid
            @ModelAttribute("usuarioRequest")
            UsuarioRequest request,

            BindingResult result,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "usuarioId",
                    id
            );

            model.addAttribute(
                    "modoEdicao",
                    true
            );

            return "usuarios/formulario";
        }

        usuarioService.atualizar(
                id,
                request
        );

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Usuário atualizado com sucesso!"
        );

        return "redirect:/usuarios";
    }


    // =========================================================
    // EXCLUIR
    // =========================================================

    @PostMapping("/{id}/excluir")
    public String excluir(

            @PathVariable Long id,

            RedirectAttributes redirectAttributes
    ) {

        usuarioService.deletar(id);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Usuário excluído com sucesso!"
        );

        return "redirect:/usuarios";
    }
}