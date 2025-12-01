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
    
    public boolean criarCategoria(String nome, int idDepartamento) {
        return categoriaBO.criarCategoria(nome, idDepartamento);
    }
    
    public boolean alterarCategoria(Integer id, String nome, Integer idDepartamento) {
        return categoriaBO.alterarCategoria(id, nome, idDepartamento);
    }
    
    public List<CategoriaDTO> listarCategorias() {
        return categoriaBO.pesquisarTodos();
    }
    
    public List<DepartamentoDTO> listarDepartamentos() {
        return departamentoBO.pesquisarTodos();
    }
    
    public boolean excluirCategoria(CategoriaDTO categoria) {
        return categoriaBO.excluir(categoria);
    }
}