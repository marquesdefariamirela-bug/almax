package sp.senai.org.controlepatrimonio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sp.senai.org.controlepatrimonio.exception.BusinessException;
import sp.senai.org.controlepatrimonio.exception.ResourceNotFoundException;
import sp.senai.org.controlepatrimonio.dto.request.LeitorRequest;
import sp.senai.org.controlepatrimonio.dto.response.LeitorResponse;
import sp.senai.org.controlepatrimonio.model.entity.Ambiente;
import sp.senai.org.controlepatrimonio.model.entity.Leitor;
import sp.senai.org.controlepatrimonio.repository.AmbienteRepository;
import sp.senai.org.controlepatrimonio.repository.LeitorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeitorService {

    private final LeitorRepository leitorRepository;
    private final AmbienteRepository ambienteRepository;

    @Transactional
    public LeitorResponse criar(LeitorRequest request) {

        validarIdentificador(request.identificador(), null);

        Ambiente ambiente = buscarAmbiente(request.ambienteId());

        Leitor leitor = Leitor.builder()
                .nome(request.nome())
                .identificador(request.identificador())
                .ip(request.ip())
                .fabricante(request.fabricante())
                .modelo(request.modelo())
                .ativo(request.ativo() != null
                        ? request.ativo()
                        : true)
                .ambiente(ambiente)
                .build();

        Leitor leitorSalvo = leitorRepository.save(leitor);

        return toResponse(leitorSalvo);
    }

    @Transactional
    public LeitorResponse buscarPorId(Long id) {

        Leitor leitor = buscarLeitor(id);

        return toResponse(leitor);
    }

    @Transactional
    public List<LeitorResponse> listar() {

        return leitorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LeitorResponse atualizar(
            Long id,
            LeitorRequest request
    ) {

        Leitor leitor = buscarLeitor(id);

        validarIdentificador(
                request.identificador(),
                id
        );

        Ambiente ambiente = buscarAmbiente(request.ambienteId());

        leitor.setNome(request.nome());
        leitor.setIdentificador(request.identificador());
        leitor.setIp(request.ip());
        leitor.setFabricante(request.fabricante());
        leitor.setModelo(request.modelo());

        if (request.ativo() != null) {
            leitor.setAtivo(request.ativo());
        }

        leitor.setAmbiente(ambiente);

        Leitor leitorAtualizado =
                leitorRepository.save(leitor);

        return toResponse(leitorAtualizado);
    }

    @Transactional
    public void deletar(Long id) {

        Leitor leitor = buscarLeitor(id);

        if (!leitor.getMovimentacoes().isEmpty()) {

            throw new BusinessException(
                    "Não é possível excluir o leitor porque "
                            + "existem movimentações vinculadas a ele"
            );
        }

        leitorRepository.delete(leitor);
    }

    private Leitor buscarLeitor(Long id) {

        return leitorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leitor não encontrado: " + id
                        )
                );
    }

    private Ambiente buscarAmbiente(Long id) {

        if (id == null) {
            return null;
        }

        return ambienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ambiente não encontrado: " + id
                        )
                );
    }

    private void validarIdentificador(
            String identificador,
            Long leitorId
    ) {

        leitorRepository
                .findByIdentificador(identificador)
                .ifPresent(leitor -> {

                    if (leitorId == null ||
                            !leitor.getId().equals(leitorId)) {

                        throw new BusinessException(
                                "Já existe um leitor com o identificador: "
                                        + identificador
                        );
                    }
                });
    }

    private LeitorResponse toResponse(Leitor leitor) {

        return new LeitorResponse(
                leitor.getId(),
                leitor.getNome(),
                leitor.getIdentificador(),
                leitor.getIp(),
                leitor.getFabricante(),
                leitor.getModelo(),
                leitor.getAtivo(),

                leitor.getAmbiente() != null
                        ? leitor.getAmbiente().getId()
                        : null,

                leitor.getAmbiente() != null
                        ? leitor.getAmbiente().getNome()
                        : null,

                leitor.getMovimentacoes() != null
                        ? leitor.getMovimentacoes().size()
                        : 0
        );
    }
}
