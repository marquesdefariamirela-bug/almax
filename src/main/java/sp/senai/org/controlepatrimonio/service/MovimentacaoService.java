package sp.senai.org.controlepatrimonio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.senai.org.controlepatrimonio.exception.BusinessException;
import sp.senai.org.controlepatrimonio.exception.ResourceNotFoundException;
import sp.senai.org.controlepatrimonio.dto.request.MovimentacaoRequest;
import sp.senai.org.controlepatrimonio.dto.response.MovimentacaoResponse;
import sp.senai.org.controlepatrimonio.model.entity.*;
import sp.senai.org.controlepatrimonio.model.enums.StatusProduto;
import sp.senai.org.controlepatrimonio.model.enums.TipoMovimentacao;
import sp.senai.org.controlepatrimonio.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final AmbienteRepository ambienteRepository;
    private final LeitorRepository leitorRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public MovimentacaoResponse criar(MovimentacaoRequest request) {

        Produto produto = buscarProduto(request.produtoId());

        validarProduto(produto);

        Ambiente ambienteOrigem =
                buscarAmbiente(request.ambienteOrigemId());

        Ambiente ambienteDestino =
                buscarAmbiente(request.ambienteDestinoId());

        Leitor leitor = buscarLeitor(request.leitorId());

        Usuario usuario = buscarUsuario(request.usuarioId());

        validarMovimentacao(
                produto,
                ambienteOrigem,
                ambienteDestino,
                request.tipo()
        );

        Movimentacao movimentacao = Movimentacao.builder()
                .produto(produto)
                .ambienteOrigem(ambienteOrigem)
                .ambienteDestino(ambienteDestino)
                .leitor(leitor)
                .usuario(usuario)
                .tipo(request.tipo())
                .dataHora(LocalDateTime.now())
                .observacao(request.observacao())
                .build();

        Movimentacao movimentacaoSalva =
                movimentacaoRepository.save(movimentacao);

        atualizarAmbienteProduto(
                produto,
                ambienteDestino
        );

        return toResponse(movimentacaoSalva);
    }

    @Transactional(readOnly = true)
    public MovimentacaoResponse buscarPorId(Long id) {

        Movimentacao movimentacao = buscarMovimentacao(id);

        return toResponse(movimentacao);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponse> listar() {

        return movimentacaoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Produto buscarProduto(Long id) {

        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado: " + id
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

    private Leitor buscarLeitor(Long id) {

        if (id == null) {
            return null;
        }

        return leitorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leitor não encontrado: " + id
                        )
                );
    }

    private Usuario buscarUsuario(Long id) {

        if (id == null) {
            return null;
        }

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado: " + id
                        )
                );
    }

    private Movimentacao buscarMovimentacao(Long id) {

        return movimentacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movimentação não encontrada: " + id
                        )
                );
    }

    private void validarProduto(Produto produto) {

        if (produto.getStatus() == StatusProduto.BAIXADO) {

            throw new BusinessException(
                    "Não é possível movimentar um produto baixado"
            );
        }

        if (produto.getStatus() == StatusProduto.INATIVO) {

            throw new BusinessException(
                    "Não é possível movimentar um produto inativo"
            );
        }
    }

    private void validarMovimentacao(
            Produto produto,
            Ambiente ambienteOrigem,
            Ambiente ambienteDestino,
            TipoMovimentacao tipo
    ) {

        if (tipo == TipoMovimentacao.TRANSFERENCIA) {

            if (ambienteDestino == null) {
                throw new BusinessException(
                        "O ambiente de destino é obrigatório para uma transferência"
                );
            }

            if (ambienteOrigem == null) {
                throw new BusinessException(
                        "O ambiente de origem é obrigatório para uma transferência"
                );
            }

            if (ambienteOrigem.getId().equals(
                    ambienteDestino.getId()
            )) {

                throw new BusinessException(
                        "O ambiente de origem e destino não podem ser iguais"
                );
            }

            if (produto.getAmbiente() != null &&
                    !produto.getAmbiente().getId()
                            .equals(ambienteOrigem.getId())) {

                throw new BusinessException(
                        "O ambiente de origem informado não corresponde "
                                + "ao ambiente atual do produto"
                );
            }
        }

        if (tipo == TipoMovimentacao.ENTRADA &&
                ambienteDestino == null) {

            throw new BusinessException(
                    "O ambiente de destino é obrigatório para uma entrada"
            );
        }

        if (tipo == TipoMovimentacao.SAIDA &&
                ambienteDestino != null) {

            throw new BusinessException(
                    "Uma saída não deve possuir ambiente de destino"
            );
        }

        if (tipo == TipoMovimentacao.LEITURA_RFID &&
                ambienteDestino == null) {

            throw new BusinessException(
                    "O ambiente de destino é obrigatório para uma leitura RFID"
            );
        }
    }

    private void atualizarAmbienteProduto(
            Produto produto,
            Ambiente ambienteDestino
    ) {

        if (ambienteDestino != null) {
            produto.setAmbiente(ambienteDestino);

            produtoRepository.save(produto);
        }
    }

    private MovimentacaoResponse toResponse(
            Movimentacao movimentacao
    ) {

        Produto produto = movimentacao.getProduto();
        Ambiente origem = movimentacao.getAmbienteOrigem();
        Ambiente destino = movimentacao.getAmbienteDestino();
        Leitor leitor = movimentacao.getLeitor();
        Usuario usuario = movimentacao.getUsuario();

        return new MovimentacaoResponse(

                movimentacao.getId(),

                produto != null
                        ? produto.getId()
                        : null,

                produto != null
                        ? produto.getNumeroPatrimonio()
                        : null,

                produto != null
                        ? produto.getNome()
                        : null,

                origem != null
                        ? origem.getId()
                        : null,

                origem != null
                        ? origem.getNome()
                        : null,

                destino != null
                        ? destino.getId()
                        : null,

                destino != null
                        ? destino.getNome()
                        : null,

                leitor != null
                        ? leitor.getId()
                        : null,

                leitor != null
                        ? leitor.getNome()
                        : null,

                usuario != null
                        ? usuario.getId()
                        : null,

                usuario != null
                        ? usuario.getNome()
                        : null,

                movimentacao.getTipo(),

                movimentacao.getDataHora(),

                movimentacao.getObservacao()
        );
    }
}
