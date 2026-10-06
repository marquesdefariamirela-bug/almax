package sp.senai.org.controlepatrimonio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sp.senai.org.controlepatrimonio.dto.request.RfidLeituraRequest;
import sp.senai.org.controlepatrimonio.dto.response.RfidLeituraResponse;
import sp.senai.org.controlepatrimonio.model.entity.Ambiente;
import sp.senai.org.controlepatrimonio.model.entity.Leitor;
import sp.senai.org.controlepatrimonio.model.entity.Movimentacao;
import sp.senai.org.controlepatrimonio.model.entity.Produto;
import sp.senai.org.controlepatrimonio.model.enums.TipoMovimentacao;
import sp.senai.org.controlepatrimonio.repository.LeitorRepository;
import sp.senai.org.controlepatrimonio.repository.MovimentacaoRepository;
import sp.senai.org.controlepatrimonio.repository.ProdutoRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RfidService {

    private final ProdutoRepository produtoRepository;
    private final LeitorRepository leitorRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public RfidLeituraResponse processarLeitura(
            RfidLeituraRequest request
    ) {

        Produto produto = produtoRepository
                .findByTag(request.uid())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Tag RFID não cadastrada."
                        )
                );

        Leitor leitor = leitorRepository
                .findByIdentificador(request.deviceId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Leitor não cadastrado."
                        )
                );

        Ambiente ambienteOrigem =
                produto.getAmbiente();

        Ambiente ambienteDestino =
                leitor.getAmbiente();

        if (ambienteDestino == null) {

            throw new RuntimeException(
                    "O leitor não está vinculado a nenhum ambiente."
            );
        }

        // Evita registrar movimentação se o produto
        // já estiver no ambiente detectado.
        if (ambienteOrigem != null
                && ambienteOrigem.getId()
                .equals(ambienteDestino.getId())) {

            return new RfidLeituraResponse(
                    true,
                    "Produto já está neste ambiente.",
                    produto.getId(),
                    produto.getNome(),
                    ambienteDestino.getNome()
            );
        }

        Movimentacao movimentacao =
                Movimentacao.builder()
                        .produto(produto)
                        .ambienteOrigem(ambienteOrigem)
                        .ambienteDestino(ambienteDestino)
                        .leitor(leitor)
                        .tipo(TipoMovimentacao.ENTRADA)
                        .dataHora(LocalDateTime.now())
                        .observacao(
                                "Movimentação registrada automaticamente "
                                        + "por leitura RFID."
                        )
                        .build();

        movimentacaoRepository.save(movimentacao);

        // Atualiza o ambiente atual do produto
        produto.setAmbiente(ambienteDestino);

        produtoRepository.save(produto);

        return new RfidLeituraResponse(
                true,
                "Movimentação registrada com sucesso.",
                produto.getId(),
                produto.getNome(),
                ambienteDestino.getNome()
        );
    }
}