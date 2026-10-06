package sp.senai.org.controlepatrimonio.dto.response;

import java.util.List;

public record DashboardResponse(

        long totalProdutos,
        long totalAmbientes,
        long totalTags,
        long totalLeitores,
        long produtosAtivos,
        long produtosInativos,
        long produtosComTag,
        List<MovimentacaoDashboardResponse> movimentacoesRecentes

) {
}
