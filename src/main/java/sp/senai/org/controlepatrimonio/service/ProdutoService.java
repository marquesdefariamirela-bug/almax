package sp.senai.org.controlepatrimonio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sp.senai.org.controlepatrimonio.exception.BusinessException;
import sp.senai.org.controlepatrimonio.exception.ResourceNotFoundException;
import sp.senai.org.controlepatrimonio.dto.request.ProdutoRequest;
import sp.senai.org.controlepatrimonio.dto.response.ProdutoResponse;
import sp.senai.org.controlepatrimonio.model.entity.Ambiente;
import sp.senai.org.controlepatrimonio.model.entity.Produto;
import sp.senai.org.controlepatrimonio.model.entity.Tag;
import sp.senai.org.controlepatrimonio.model.enums.StatusProduto;
import sp.senai.org.controlepatrimonio.repository.AmbienteRepository;
import sp.senai.org.controlepatrimonio.repository.ProdutoRepository;
import sp.senai.org.controlepatrimonio.repository.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final AmbienteRepository ambienteRepository;
    private final TagRepository tagRepository;

    @Transactional
    public ProdutoResponse criar(ProdutoRequest request) {

        validarNumeroPatrimonio(request.numeroPatrimonio(), null);

        Ambiente ambiente = buscarAmbiente(request.ambienteId());

        Tag tag = buscarTag(request.tagId());

        validarTagDisponivel(tag);

        Produto produto = Produto.builder()
                .numeroPatrimonio(request.numeroPatrimonio())
                .nome(request.nome())
                .descricao(request.descricao())
                .marca(request.marca())
                .modelo(request.modelo())
                .numeroSerie(request.numeroSerie())
                .valor(request.valor())
                .dataAquisicao(request.dataAquisicao())
                .ambiente(ambiente)
                .tag(tag)
                .build();

        Produto produtoSalvo = produtoRepository.save(produto);

        return toResponse(produtoSalvo);
    }

    @Transactional
    public ProdutoResponse buscarPorId(Long id) {

        Produto produto = buscarProduto(id);

        return toResponse(produto);
    }

    @Transactional
    public List<ProdutoResponse> listar() {

        return produtoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {

        Produto produto = buscarProduto(id);

        validarNumeroPatrimonio(
                request.numeroPatrimonio(),
                id
        );

        Ambiente ambiente = buscarAmbiente(request.ambienteId());

        Tag tag = buscarTag(request.tagId());

        validarTagDisponivelParaAtualizacao(tag, produto);

        produto.setNumeroPatrimonio(request.numeroPatrimonio());
        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setMarca(request.marca());
        produto.setModelo(request.modelo());
        produto.setNumeroSerie(request.numeroSerie());
        produto.setValor(request.valor());
        produto.setDataAquisicao(request.dataAquisicao());
        produto.setAmbiente(ambiente);
        produto.setTag(tag);

        Produto produtoAtualizado = produtoRepository.save(produto);

        return toResponse(produtoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {

        Produto produto = buscarProduto(id);

        if (!produto.getMovimentacoes().isEmpty()) {
            throw new BusinessException(
                    "Não é possível excluir um produto que possui movimentações"
            );
        }

        produtoRepository.delete(produto);
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

    private Tag buscarTag(Long id) {

        if (id == null) {
            return null;
        }

        return tagRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tag não encontrada: " + id
                        )
                );
    }

    private void validarNumeroPatrimonio(
            String numeroPatrimonio,
            Long produtoId
    ) {

        produtoRepository
                .findByNumeroPatrimonio(numeroPatrimonio)
                .ifPresent(produto -> {

                    if (produtoId == null ||
                            !produto.getId().equals(produtoId)) {

                        throw new BusinessException(
                                "Já existe um produto com o número de patrimônio: "
                                        + numeroPatrimonio
                        );
                    }
                });
    }

    private void validarTagDisponivel(Tag tag) {

        if (tag == null) {
            return;
        }

        if (tag.getProduto() != null) {
            throw new BusinessException(
                    "A tag informada já está vinculada a outro produto"
            );
        }
    }

    private void validarTagDisponivelParaAtualizacao(
            Tag tag,
            Produto produtoAtual
    ) {

        if (tag == null) {
            return;
        }

        if (tag.getProduto() != null &&
                !tag.getProduto().getId().equals(produtoAtual.getId())) {

            throw new BusinessException(
                    "A tag informada já está vinculada a outro produto"
            );
        }
    }

    public long contar() {
        return produtoRepository.count();
    }

    public long contarPorStatus(StatusProduto status) {
        return produtoRepository.countByStatus(status);
    }

    private ProdutoResponse toResponse(Produto produto) {

        return new ProdutoResponse(
                produto.getId(),
                produto.getNumeroPatrimonio(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getMarca(),
                produto.getModelo(),
                produto.getNumeroSerie(),
                produto.getValor(),
                produto.getDataAquisicao(),
                produto.getStatus(),

                produto.getAmbiente() != null
                        ? produto.getAmbiente().getId()
                        : null,

                produto.getAmbiente() != null
                        ? produto.getAmbiente().getNome()
                        : null,

                produto.getTag() != null
                        ? produto.getTag().getId()
                        : null,

                produto.getTag() != null
                        ? produto.getTag().getEpc()
                        : null
        );
    }

    public ProdutoResponse buscarPorTagRfid(String tagRfid) {

        Produto produto = produtoRepository
                .findByTag(tagRfid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Produto não encontrado para a tag RFID: "
                                        + tagRfid
                        )
                );

        return toResponse(produto);
    }
}
