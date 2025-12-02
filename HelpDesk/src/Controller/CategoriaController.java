package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class CategoriaController {
    private final CategoriaBO categoriaBO;
    private final DepartamentoBO departamentoBO;
    
    public CategoriaController() {
        this.categoriaBO = new CategoriaBO();
        this.departamentoBO = new DepartamentoBO();
    }
    
    public boolean inserir(CategoriaDTO categoria) {
        return categoriaBO.inserir(categoria);
    }
    
    // Compatibilidade: versão antiga usada pelas Views
    public boolean inserir(String nome, int idDepartamento) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNome(nome);
        DepartamentoDTO dep = new DepartamentoDTO();
        dep.setId(idDepartamento);
        dto.setDepartamento(dep);
        dto.setNumeroTicket(0);
        return categoriaBO.inserir(dto);
    }

    public boolean alterar(CategoriaDTO categoria) {
        return categoriaBO.alterar(categoria);
    }

    // Compatibilidade: versão antiga usada pelas Views
    public boolean alterar(Integer idCategoria, String nome, Integer idDepartamento) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(idCategoria);
        dto.setNome(nome);
        if (idDepartamento != null) {
            DepartamentoDTO dep = new DepartamentoDTO();
            dep.setId(idDepartamento);
            dto.setDepartamento(dep);
        }
        return categoriaBO.alterar(dto);
    }
    
    public List<CategoriaDTO> pesquisarTodos() {
        return categoriaBO.pesquisarTodos();
    }

    // Compatibilidade
    public List<CategoriaDTO> listarCategorias() {
        return pesquisarTodos();
    }
    
    public List<DepartamentoDTO> pesquisarDepartamentos() {
        return departamentoBO.pesquisarTodos();
    }
    
    public boolean excluir(CategoriaDTO categoria) {
        return categoriaBO.excluir(categoria);
    }

    // compatibility wrapper for older Views
    public boolean excluirCategoria(CategoriaDTO categoria) {
        return excluir(categoria);
    }
}