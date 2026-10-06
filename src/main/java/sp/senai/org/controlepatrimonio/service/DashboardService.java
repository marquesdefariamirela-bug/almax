package sp.senai.org.controlepatrimonio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.senai.org.controlepatrimonio.dto.response.DashboardResponse;
import sp.senai.org.controlepatrimonio.dto.response.MovimentacaoDashboardResponse;
import sp.senai.org.controlepatrimonio.model.entity.Movimentacao;
import sp.senai.org.controlepatrimonio.model.enums.StatusProduto;
import sp.senai.org.controlepatrimonio.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProdutoRepository produtoRepository;
    private final AmbienteRepository ambienteRepository;
    private final TagRepository tagRepository;
    private final LeitorRepository leitorRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Transactional(readOnly = true)
    public DashboardResponse obterDashboard() {

        long totalProdutos = produtoRepository.count();

        long totalAmbientes = ambienteRepository.count();

        long totalTags = tagRepository.count();

        long totalLeitores = leitorRepository.count();

        long produtosAtivos =
                produtoRepository.countByStatus((StatusProduto.ATIVO));

        long produtosInativos =
                produtoRepository.countByStatus(StatusProduto.INATIVO);

        long produtosComTag =
                produtoRepository.countByTagIsNotNull();

        List<MovimentacaoDashboardResponse> movimentacoesRecentes =
                movimentacaoRepository
                        .findTop10ByOrderByDataHoraDesc()
                        .stream()
                        .map(this::toMovimentacaoResponse)
                        .toList();

        return new DashboardResponse(
                totalProdutos,
                totalAmbientes,
                totalTags,
                totalLeitores,
                produtosAtivos,
                produtosInativos,
                produtosComTag,
                movimentacoesRecentes
        );
    }

    private MovimentacaoDashboardResponse toMovimentacaoResponse(
            Movimentacao movimentacao
    ) {

        return new MovimentacaoDashboardResponse(

                movimentacao.getId(),

                movimentacao.getProduto() != null
                        ? movimentacao.getProduto().getNome()
                        : "Produto não informado",

                movimentacao.getAmbienteOrigem() != null
                        ? movimentacao.getAmbienteOrigem().getNome()
                        : "Não informado",

                movimentacao.getAmbienteDestino() != null
                        ? movimentacao.getAmbienteDestino().getNome()
                        : "Não informado",

                movimentacao.getDataHora()
        );
    }
}