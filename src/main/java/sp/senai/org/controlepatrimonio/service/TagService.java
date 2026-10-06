package sp.senai.org.controlepatrimonio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sp.senai.org.controlepatrimonio.dto.request.TagRequest;
import sp.senai.org.controlepatrimonio.dto.response.TagResponse;
import sp.senai.org.controlepatrimonio.exception.BusinessException;
import sp.senai.org.controlepatrimonio.exception.ResourceNotFoundException;
import sp.senai.org.controlepatrimonio.model.entity.Produto;
import sp.senai.org.controlepatrimonio.model.entity.Tag;
import sp.senai.org.controlepatrimonio.repository.ProdutoRepository;
import sp.senai.org.controlepatrimonio.repository.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ProdutoRepository produtoRepository;


    // =========================================================
    // CRIAR
    // =========================================================

    @Transactional
    public TagResponse criar(TagRequest request) {

        validarEpc(request.epc(), null);

        Produto produto = buscarProduto(request.produtoId());

        validarProdutoDisponivel(produto, null);

        Tag tag = Tag.builder()
                .epc(request.epc())
                .codigo(request.codigo())
                .ativa(request.ativa() != null
                        ? request.ativa()
                        : true)
                .produto(produto)
                .build();

        Tag tagSalva = tagRepository.save(tag);

        return toResponse(tagSalva);
    }


    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    @Transactional
    public TagResponse buscarPorId(Long id) {

        Tag tag = buscarTag(id);

        return toResponse(tag);
    }


    // =========================================================
    // LISTAR
    // =========================================================

    @Transactional
    public List<TagResponse> listar() {

        return tagRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    // =========================================================
    // LISTAR TAGS DISPONÍVEIS
    // =========================================================

    @Transactional
    public List<TagResponse> listarDisponiveis() {

        return tagRepository.findAll()
                .stream()
                .filter(tag -> tag.getProduto() == null)
                .filter(Tag::getAtiva)
                .map(this::toResponse)
                .toList();
    }


    // =========================================================
    // ATUALIZAR
    // =========================================================

    @Transactional
    public TagResponse atualizar(
            Long id,
            TagRequest request
    ) {

        Tag tag = buscarTag(id);

        validarEpc(request.epc(), id);

        Produto produto = buscarProduto(request.produtoId());

        validarProdutoDisponivel(produto, id);

        tag.setEpc(request.epc());
        tag.setCodigo(request.codigo());

        if (request.ativa() != null) {
            tag.setAtiva(request.ativa());
        }

        tag.setProduto(produto);

        Tag tagAtualizada = tagRepository.save(tag);

        return toResponse(tagAtualizada);
    }


    // =========================================================
    // DELETAR
    // =========================================================

    @Transactional
    public void deletar(Long id) {

        Tag tag = buscarTag(id);

        if (tag.getProduto() != null) {

            throw new BusinessException(
                    "Não é possível excluir uma tag vinculada a um produto"
            );
        }

        tagRepository.delete(tag);
    }


    // =========================================================
    // BUSCAR TAG
    // =========================================================

    private Tag buscarTag(Long id) {

        return tagRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tag não encontrada: " + id
                        )
                );
    }


    // =========================================================
    // BUSCAR PRODUTO
    // =========================================================

    private Produto buscarProduto(Long id) {

        if (id == null) {
            return null;
        }

        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado: " + id
                        )
                );
    }


    // =========================================================
    // VALIDAR EPC
    // =========================================================

    private void validarEpc(
            String epc,
            Long tagId
    ) {

        if (epc == null || epc.isBlank()) {
            return;
        }

        tagRepository
                .findByEpc(epc)
                .ifPresent(tag -> {

                    if (tagId == null ||
                            !tag.getId().equals(tagId)) {

                        throw new BusinessException(
                                "Já existe uma tag com o EPC: " + epc
                        );
                    }
                });
    }


    // =========================================================
    // VALIDAR PRODUTO DISPONÍVEL
    // =========================================================

    private void validarProdutoDisponivel(
            Produto produto,
            Long tagId
    ) {

        if (produto == null) {
            return;
        }

        Tag tagAtual = produto.getTag();

        if (tagAtual == null) {
            return;
        }

        if (tagId == null ||
                !tagAtual.getId().equals(tagId)) {

            throw new BusinessException(
                    "O produto já possui uma tag vinculada"
            );
        }
    }


    // =========================================================
    // CONVERTER PARA RESPONSE
    // =========================================================

    private TagResponse toResponse(Tag tag) {

        Produto produto = tag.getProduto();

        return new TagResponse(
                tag.getId(),
                tag.getEpc(),
                tag.getCodigo(),
                tag.getAtiva(),

                produto != null
                        ? produto.getId()
                        : null,

                produto != null
                        ? produto.getNumeroPatrimonio()
                        : null,

                produto != null
                        ? produto.getNome()
                        : null
        );
    }
}