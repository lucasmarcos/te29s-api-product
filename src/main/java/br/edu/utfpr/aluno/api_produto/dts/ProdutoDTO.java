package br.edu.utfpr.aluno.api_produto.dts;

public record ProdutoDTO(
        Long id,
        String description,
        Integer quantity,
        Double price,
        String category
) {}
