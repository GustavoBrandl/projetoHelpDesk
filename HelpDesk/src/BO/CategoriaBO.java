package BO;

import DTO.DepartamentoDTO;
import DTO.CategoriaDTO;
import DAO.CategoriaDAO;
import java.util.List;

public class CategoriaBO {
    
	public boolean inserir(CategoriaDTO categoria){
		if (existe(categoria) != true) {
		    CategoriaDAO categoriasDAO = new CategoriaDAO();
		    return categoriasDAO.inserir(categoria);
		} 
			return false;
	}
	
    public boolean excluir(CategoriaDTO categoria) {
    	CategoriaDAO categoriasDAO = new CategoriaDAO();
    	return categoriasDAO.excluir(categoria);
    }
    
    public boolean existe(CategoriaDTO categoria){
        CategoriaDAO categoriasDAO = new CategoriaDAO();
        return categoriasDAO.existe(categoria);
    }

    public List<CategoriaDTO> pesquisarTodos(){
        CategoriaDAO categoriasDAO = new CategoriaDAO();
        return categoriasDAO.pesquisarTodos();
    }
    
    public boolean criarCategoria(String nome, int idDepartamento) {

        DepartamentoDTO departamento = new DepartamentoDTO();
        departamento.setId(idDepartamento);

        CategoriaDTO categoria = new CategoriaDTO();
        categoria.setNome(nome);
        categoria.setNumeroTicket(0);
        categoria.setDepartamento(departamento);

        return inserir(categoria);
    }
    
    public boolean alterarCategoria(Integer idCategoria, String nome, Integer idDepartamento) {

        CategoriaDTO categoria = new CategoriaDTO();
        categoria.setId(idCategoria);

        if (nome != null) {
            categoria.setNome(nome);
        }

        if (idDepartamento != null) {
            DepartamentoDTO dep = new DepartamentoDTO();
            dep.setId(idDepartamento);
            categoria.setDepartamento(dep);
        }

        CategoriaDAO categoriasDAO = new CategoriaDAO();
        return categoriasDAO.alterarCategoria(categoria);
    }
    
    public void listarCategoria(Integer id) {
    	List<CategoriaDTO> categorias = pesquisarTodos();
    	
    	if (id == null) {
	    	for (CategoriaDTO categoria : categorias) {
	    		System.out.println(categoria);
	    	}
    	} else {
    		for (CategoriaDTO categoria : categorias) {
    			if(categoria.getDepartamento().getId() == id) {
    				System.out.println(categoria);
    			}
    		}
    	}
    }
    
    

}
