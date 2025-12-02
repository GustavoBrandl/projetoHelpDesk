package Controller;

import BO.UsuarioBO;
import BO.OrganizacaoBO;
import DTO.UsuarioDTO;
import DTO.OrganizacaoDTO;
import java.util.List;

public class UsuarioController {
    private final UsuarioBO usuarioBO;
    private final OrganizacaoBO organizacaoBO;
    
    public UsuarioController() {
        this.usuarioBO = new UsuarioBO();
        this.organizacaoBO = new OrganizacaoBO();
    }
    
    public UsuarioDTO autenticar(String email, String senha) {
        return usuarioBO.autenticar(email, senha);
    }
    
    public boolean cadastrarUsuario(String username, String email, String password, 
                                   String telefone, Integer tipo, Integer idOrganizacao) {
        return usuarioBO.criarUsuario(username, email, password, telefone, tipo, idOrganizacao);
    }
    
    public List<OrganizacaoDTO> listarOrganizacoes() {
        return organizacaoBO.pesquisarTodos();
    }
    
    public Integer criarOuObterOrganizacaoPadrao() {
        List<OrganizacaoDTO> orgs = listarOrganizacoes();
        if (orgs == null || orgs.isEmpty()) {
            organizacaoBO.inserir("Organização Padrão", "padrao.com");
            orgs = listarOrganizacoes();
        }
        return (orgs != null && !orgs.isEmpty()) ? orgs.get(0).getId() : null;
    }
    
    public List<UsuarioDTO> listarTodosUsuarios() {
        return usuarioBO.pesquisarTodos();
    }
    
    public boolean atualizarUsuario(UsuarioDTO usuario) {
        return usuarioBO.alterarUsuario(
            usuario.getId(),
            usuario.getUsername(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getTipo().getId(),
            usuario.getOrganizacao() != null ? usuario.getOrganizacao().getId() : null
        );
    }
    
    public boolean ativarDesativarUsuario(int id) {
        return usuarioBO.ativarDesativar(id);
    }
}