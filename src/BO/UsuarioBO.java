package BO;

import ENUM.TipoUsuario;
import DTO.OrganizacaoDTO;
import DAO.OrganizacaoDAO;
import DTO.UsuarioDTO;
import DAO.UsuarioDAO;
import java.util.List;

public class UsuarioBO {
    
	public boolean inserir(UsuarioDTO usuario){
		if (existe(usuario) != true) {
		    UsuarioDAO usuariosDAO = new UsuarioDAO();
		    return usuariosDAO.inserir(usuario);
		} 
			return false;
	}
	
    public boolean excluir(UsuarioDTO usuario) {
    	UsuarioDAO usuariosDAO = new UsuarioDAO();
    	return usuariosDAO.excluir(usuario);
    }
    
    public boolean existe(UsuarioDTO usuario){
        UsuarioDAO usuariosDAO = new UsuarioDAO();
        return usuariosDAO.existe(usuario);
    }

    public List<UsuarioDTO> pesquisarTodos(){
        UsuarioDAO usuariosDAO = new UsuarioDAO();
        return usuariosDAO.pesquisarTodos();
    }
    
    public boolean criarUsuario(String username, String email, String password, String telefone, Integer tipo, int idOrganizacao) {

        OrganizacaoDTO organizacao = new OrganizacaoDTO();
        organizacao.setId(idOrganizacao);

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTelefone(telefone);
        usuario.setTipo(TipoUsuario.fromId(tipo));
        usuario.setOrganizacao(organizacao);

        return inserir(usuario);
    }
    
    public boolean alterarUsuario(Integer id, String username, String email, String telefone, Integer tipo, Integer idOrganizacao) {

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(id);

        if (username != null) {
            usuario.setUsername(username);
        }
        
        if (email != null) {
        	usuario.setEmail(email);
        }
        
        if (telefone != null) {
        	usuario.setTelefone(telefone);
        }
        
        if (tipo != null) {
        	usuario.setTipo(TipoUsuario.fromId(tipo));
        }

        if (idOrganizacao != null) {
            OrganizacaoDTO org= new OrganizacaoDTO();
            org.setId(idOrganizacao);
            usuario.setOrganizacao(org);
        }

        UsuarioDAO usuariosDAO = new UsuarioDAO();
        return usuariosDAO.alterarUsuario(usuario);
    }
    
    public void listarUsuario(Integer id) {
    	List<UsuarioDTO> usuarios = pesquisarTodos();
    	
    	if (id == null) {
	    	for (UsuarioDTO usuario : usuarios) {
	    		System.out.println(usuario);
	    	}
    	} else {
    		for (UsuarioDTO usuario: usuarios) {
    			if(usuario.getOrganizacao().getId() == id) {
    				System.out.println(usuario);
    			}
    		}
    	}
    }
    
    public UsuarioDTO autenticar(String email, String senha) {
        try {
            System.out.println("DEBUG autenticar: Email=" + email + ", Senha=" + senha);
            
            List<UsuarioDTO> todosUsuarios = pesquisarTodos();
            
            if (todosUsuarios == null) {
                System.out.println("DEBUG: Lista de usuários é NULL");
                return null;
            }
            
            System.out.println("DEBUG: Total de usuários no BD: " + todosUsuarios.size());
            
            for (UsuarioDTO usuario : todosUsuarios) {
                System.out.println("DEBUG: Comparando com -> EmailBD=" + usuario.getEmail() + 
                                 ", SenhaBD=" + usuario.getPassword() + 
                                 ", Ativo=" + usuario.isAtivo());
                
                if (usuario.getEmail() != null && 
                    usuario.getEmail().equalsIgnoreCase(email.trim()) &&
                    usuario.getPassword() != null &&
                    usuario.getPassword().equals(senha.trim()) &&
                    usuario.isAtivo()) {
                    
                    System.out.println("DEBUG: ✅ Usuário autenticado: " + usuario.getUsername());
                    return usuario;
                }
            }
            
            System.out.println("DEBUG: ❌ Nenhum usuário encontrado com estas credenciais");
            return null;
            
        } catch (Exception e) {
            System.out.println("DEBUG: ❌ Erro na autenticação:");
            e.printStackTrace();
            return null;
        }
    }

    public boolean ativarDesativar(int id) {
        UsuarioDAO usuariosDAO = new UsuarioDAO();
        return usuariosDAO.ativarDesativar(id);
    }

}
