package fiap.controller;

import fiap.model.Produto;
import fiap.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", repository.findAll());
        return "produtos/lista"; // Retorna o HTML de listagem
    }

    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/formulario"; // Retorna o HTML de cadastro
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        repository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/produtos";
    }
}