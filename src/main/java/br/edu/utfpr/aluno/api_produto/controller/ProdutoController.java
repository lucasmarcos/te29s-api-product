package br.edu.utfpr.aluno.api_produto.controller;

import br.edu.utfpr.aluno.api_produto.dts.ProdutoDTO;
import br.edu.utfpr.aluno.api_produto.model.Produto;
import br.edu.utfpr.aluno.api_produto.repositories.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {
// <<<<<<< HEAD
//   // Lista de produtos, para simular um banco de dados
//   private List<Produto> produtos;
//
//   // Construtor para popular a lista de produtos
//   public ProdutoController(){
//     this.produtos = new ArrayList<>();
//     this.produtos.add(new Produto(1L, "IPHONE 15", 10, 5000.0, "Smartphone"));
//     this.produtos.add(new Produto(2L, "Airpods Pro", 20, 2500.0, "Acessórios"));
//     this.produtos.add(new Produto(3L, "Notebook i7", 30, 4800.0, "Computadores"));
//     this.produtos.add(new Produto(4L, "Cadeira Gamer", 50, 1500.0, "Móveis"));
//   }
//
//   // Lista de endpoints
//
//   @GetMapping
//   public ResponseEntity<List<Produto>> getAll(){
//     return ResponseEntity.ok(this.produtos);
//   }
//
//   @GetMapping(path = "/{id}")
//   public ResponseEntity<Produto> getOne(@PathVariable(name = "id") Long idProduto){
//     Produto produtoEncontrado = this.produtos.stream()
//       .filter(produto -> produto.getId().equals(idProduto))
//       .findFirst()
//       .orElse(null);
//
//     if (produtoEncontrado == null)
//       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//     else
//       return ResponseEntity.status(HttpStatus.OK).body(produtoEncontrado);
//   }
//
//   @PostMapping
//   public ResponseEntity<String> addOne(@RequestBody Produto produto) {
//     if (produto.getDescription() == null || produto.getPrice() < 0){
//       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Descrição ou Preço inválidos");
//     } else {
//       this.produtos.add(produto);
//       return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
// =======

    private ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository){
        this.repository = repository;
// >>>>>>> b14b529be4393041944a02ed552e5e463aa1ff7c
    }
  }

// <<<<<<< HEAD
//   @PutMapping(path="/{id}")
//   public ResponseEntity<String> update(@PathVariable(name="id") Long idProduto, @RequestBody Produto produto) {
//     for (Produto p : this.produtos){
//       if (p.getId().equals(idProduto)){
//         p.setDescription(produto.getDescription());
//         p.setQuantity(produto.getQuantity());
//         p.setPrice(produto.getPrice());
//         p.setCategory(produto.getCategory());
//         return ResponseEntity.ok("Produto atualizado com sucesso.");
//       }
//     }
//     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
//   }
//
//   @DeleteMapping(path = "/{id}")
//   public ResponseEntity<String> delete(@PathVariable(name="id") Long idProduto){
//     Produto produtoRemover = this.produtos.stream()
//       .filter(p->p.getId().equals(idProduto))
//       .findFirst()
//       .orElse(null);
//     if (produtoRemover != null){
//       this.produtos.remove(produtoRemover);
//       return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
// =======
    // Lista de endpoints

    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        return ResponseEntity.ok(this.repository.findAll());
// >>>>>>> b14b529be4393041944a02ed552e5e463aa1ff7c
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
  }

// <<<<<<< HEAD
//   @PostMapping(path = "/{id}/venda")
//   public ResponseEntity<String> venda(
//       @PathVariable(name = "id") Long idProduto,
//       @RequestBody Produto vendaBody) {
//       Produto produtoVenda = this.produtos.stream()
//         .filter(p -> p.getId().equals(idProduto))
//         .findFirst()
//         .orElse(null);
//
//       if (produtoVenda != null && produtoVenda.getQuantity() >= vendaBody.getQuantity()) {
//         produtoVenda.setQuantity(
//             produtoVenda.getQuantity() - vendaBody.getQuantity()
//             );
//
//         return ResponseEntity.status(HttpStatus.OK).body("venda bem sucedida");
//       }
//
//       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("requisicao invalida");
//       }
// }
// =======
    @PostMapping
    public ResponseEntity<String> addOne(@RequestBody Produto produto) {
        if (produto.getDescription() == null || produto.getPrice() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Descrição ou Preço inválidos");
        } else {
            this.repository.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
        }
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<String> update(@PathVariable(name="id") Long idProduto, @RequestBody Produto produto) {
        Produto produtoDB = this.repository.findById(idProduto).orElse(null);
        if (produtoDB != null){
            produtoDB.setDescription(produto.getDescription());
            produtoDB.setQuantity(produto.getQuantity());
            produtoDB.setPrice(produto.getPrice());
            produtoDB.setCategory(produto.getCategory());
            this.repository.save(produtoDB);
            return ResponseEntity.ok("Produto atualizado com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(name="id") Long idProduto){
        Produto produtoRemover = this.repository.findById(idProduto).orElse(null);
        if (produtoRemover != null){
            this.repository.delete(produtoRemover);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
    }

    @GetMapping(path = "/categoria/{category}")
    public ResponseEntity<List<Produto>> getByCategory(@PathVariable(name = "category") String category){
        List<Produto> produtos = this.repository.findByCategory(category);
        if (produtos.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProdutoDTO> getOne(@PathVariable(name = "id") Long idProduto){
        Produto produtoEncontrado = this.repository.findById(idProduto).orElse(null);
        if (produtoEncontrado == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else{
            ProdutoDTO produtoDTO = new ProdutoDTO(
                    produtoEncontrado.getId(),
                    produtoEncontrado.getDescription(),
                    produtoEncontrado.getQuantity(),
                    produtoEncontrado.getPrice(),
                    produtoEncontrado.getCategory()
            );
            return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
        }
    }

    @PostMapping("/estoque/baixa")
    public ResponseEntity<Boolean> atualizarEstoque(@RequestBody List<ProdutoDTO> produtos){
        try {
            executarBaixa(produtos);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.ok(true);
    }

    @Transactional
    public void executarBaixa(List<ProdutoDTO> produtos){

        for (ProdutoDTO item : produtos) {
            // Verificando se produto existe
            Produto produto = this.repository.findById(item.id()).orElse(null);
            if (produto == null) throw new IllegalArgumentException("Produto não encontrado.");
            // Verifica se produto tem estoque suficiente
            int novaQuantidade = produto.getQuantity() - item.quantity();
            if (novaQuantidade < 0) throw new IllegalArgumentException("Produto com estoque insuficiente.");
        }

        // Faz a atualização dos produtos.
        for (ProdutoDTO item : produtos) {
            Produto produto = this.repository.findById(item.id()).orElse(null);
            int novaQuantidade = produto.getQuantity() - item.quantity();
            produto.setQuantity(novaQuantidade);
            this.repository.save(produto);
        }

    }

}
// >>>>>>> b14b529be4393041944a02ed552e5e463aa1ff7c
