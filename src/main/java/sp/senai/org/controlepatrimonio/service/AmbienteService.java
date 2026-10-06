package sp.senai.org.controlepatrimonio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.senai.org.controlepatrimonio.exception.BusinessException;
import sp.senai.org.controlepatrimonio.exception.ResourceNotFoundException;
import sp.senai.org.controlepatrimonio.dto.request.AmbienteRequest;
import sp.senai.org.controlepatrimonio.dto.response.AmbienteResponse;
import sp.senai.org.controlepatrimonio.model.entity.Ambiente;
import sp.senai.org.controlepatrimonio.repository.AmbienteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmbienteService {

    private final AmbienteRepository ambienteRepository;

    @Transactional
    public AmbienteResponse criar(AmbienteRequest request) {

        Ambiente ambiente = Ambiente.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .ativo(request.ativo() != null
                        ? request.ativo()
                        : true)
                .build();

        Ambiente ambienteSalvo = ambienteRepository.save(ambiente);

        return toResponse(ambienteSalvo);
    }

    @Transactional(readOnly = true)
    public AmbienteResponse buscarPorId(Long id) {

        Ambiente ambiente = buscarAmbiente(id);

        return toResponse(ambiente);
    }

    @Transactional(readOnly = true)
    public List<AmbienteResponse> listar() {

        return ambienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AmbienteResponse atualizar(
            Long id,
            AmbienteRequest request
    ) {

        Ambiente ambiente = buscarAmbiente(id);

        ambiente.setNome(request.nome());
        ambiente.setDescricao(request.descricao());

        if (request.ativo() != null) {
            ambiente.setAtivo(request.ativo());
        }

        Ambiente ambienteAtualizado =
                ambienteRepository.save(ambiente);

        return toResponse(ambienteAtualizado);
    }

    @Transactional
    public void deletar(Long id) {

        Ambiente ambiente = buscarAmbiente(id);

        if (!ambiente.getProdutos().isEmpty()) {
            throw new BusinessException(
                    "Não é possível excluir o ambiente porque existem "
                            + "produtos vinculados a ele"
            );
        }

        if (!ambiente.getLeitores().isEmpty()) {
            throw new BusinessException(
                    "Não é possível excluir o ambiente porque existem "
                            + "leitores vinculados a ele"
            );
        }

        ambienteRepository.delete(ambiente);
    }

    private Ambiente buscarAmbiente(Long id) {

        return ambienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ambiente não encontrado: " + id
                        )
                );
    }

    private AmbienteResponse toResponse(Ambiente ambiente) {

        return new AmbienteResponse(
                ambiente.getId(),
                ambiente.getNome(),
                ambiente.getDescricao(),
                ambiente.getAtivo(),
                ambiente.getProdutos() != null
                        ? ambiente.getProdutos().size()
                        : 0,
                ambiente.getLeitores() != null
                        ? ambiente.getLeitores().size()
                        : 0
        );
    }
}
