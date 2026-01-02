package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class DepartamentoController {
    private final DepartamentoBO departamentoBO;
    
    public DepartamentoController() {
        this.departamentoBO = new DepartamentoBO();
    }
    
    public boolean inserir(DepartamentoDTO departamento) {
        return departamentoBO.inserir(departamento);
    }
    

    public boolean inserir(String nome) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setNome(nome);
        return departamentoBO.inserir(dto);
    }
    
    public boolean alterar(DepartamentoDTO departamento) {
        return departamentoBO.alterar(departamento);
    }
    

    public boolean alterar(Integer id, String nome) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(id);
        dto.setNome(nome);
        return departamentoBO.alterar(dto);
    }
    
    public List<DepartamentoDTO> pesquisarTodos() {
        return departamentoBO.pesquisarTodos();
    }
    

    public List<DepartamentoDTO> listarDepartamentos() {
        return pesquisarTodos();
    }
    
    public boolean excluir(DepartamentoDTO departamento) {
        return departamentoBO.excluir(departamento);
    }
}

