package sp.senai.org.controlepatrimonio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.senai.org.controlepatrimonio.exception.BusinessException;
import sp.senai.org.controlepatrimonio.exception.ResourceNotFoundException;
import sp.senai.org.controlepatrimonio.dto.request.UsuarioRequest;
import sp.senai.org.controlepatrimonio.dto.response.UsuarioResponse;
import sp.senai.org.controlepatrimonio.model.entity.Usuario;
import sp.senai.org.controlepatrimonio.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {

        validarEmail(request.email(), null);

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .ativo(request.ativo() != null
                        ? request.ativo()
                        : true)
                .perfil(request.perfil())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return toResponse(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {

        Usuario usuario = buscarUsuario(id);

        return toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponse atualizar(
            Long id,
            UsuarioRequest request
    ) {

        Usuario usuario = buscarUsuario(id);

        validarEmail(request.email(), id);

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());

        if (request.senha() != null &&
                !request.senha().isBlank()) {

            usuario.setSenha(
                    passwordEncoder.encode(request.senha())
            );
        }

        if (request.ativo() != null) {
            usuario.setAtivo(request.ativo());
        }

        if (request.perfil() != null) {
            usuario.setPerfil(request.perfil());
        }

        Usuario usuarioAtualizado =
                usuarioRepository.save(usuario);

        return toResponse(usuarioAtualizado);
    }

    @Transactional
    public void deletar(Long id) {

        Usuario usuario = buscarUsuario(id);

        usuarioRepository.delete(usuario);
    }

    private Usuario buscarUsuario(Long id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado: " + id
                        )
                );
    }

    private void validarEmail(
            String email,
            Long usuarioId
    ) {

        usuarioRepository
                .findByEmail(email)
                .ifPresent(usuario -> {

                    if (usuarioId == null ||
                            !usuario.getId().equals(usuarioId)) {

                        throw new BusinessException(
                                "Já existe um usuário com o e-mail: "
                                        + email
                        );
                    }
                });
    }

    private UsuarioResponse toResponse(Usuario usuario) {

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getAtivo(),
                usuario.getPerfil()
        );
    }
}
