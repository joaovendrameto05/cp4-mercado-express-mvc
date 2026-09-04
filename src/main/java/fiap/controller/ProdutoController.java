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
        return "produtos/lista"; 
    }

    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/formulario"; 
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        repository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de produto inválido: " + id));
        
        model.addAttribute("produto", produto);
        
        return "produtos/formulario"; 
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/produtos";
    }
}
