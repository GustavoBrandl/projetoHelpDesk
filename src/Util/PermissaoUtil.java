package Util;

import ENUM.TipoUsuario;
import DTO.UsuarioDTO;

public class PermissaoUtil {
    
    public static boolean podeTudo(UsuarioDTO usuario) {
        return usuario != null && usuario.getTipo() == TipoUsuario.ADMIN;
    }
    
    public static boolean podeGerenciarSistema(UsuarioDTO usuario) {
        return usuario != null && 
               (usuario.getTipo() == TipoUsuario.ADMIN || 
                usuario.getTipo() == TipoUsuario.TECNICO);
    }
    
    public static boolean podeAtenderTickets(UsuarioDTO usuario) {
        return usuario != null && 
               (usuario.getTipo() == TipoUsuario.ADMIN || 
                usuario.getTipo() == TipoUsuario.TECNICO);
    }
    
    public static boolean podeVerTodosTickets(UsuarioDTO usuario) {
        return usuario != null && 
               (usuario.getTipo() == TipoUsuario.ADMIN || 
                usuario.getTipo() == TipoUsuario.TECNICO);
    }
    
    public static boolean podeVerOrgTickets(UsuarioDTO usuario) {
        return usuario != null && 
               (usuario.getTipo() == TipoUsuario.ADMIN || 
                usuario.getTipo() == TipoUsuario.TECNICO ||
                usuario.getTipo() == TipoUsuario.GERENTE);
    }
    
    public static boolean podeCriarTicket(UsuarioDTO usuario) {
        return usuario != null && usuario.isAtivo();
    }
    
    public static boolean podeGerenciarUsuarios(UsuarioDTO editor, UsuarioDTO alvo) {
        if (editor == null || alvo == null) return false;
        
        if (editor.getTipo() == TipoUsuario.ADMIN) return true;
        
        if (editor.getTipo() == TipoUsuario.TECNICO) {
            return alvo.getTipo() == TipoUsuario.GERENTE || 
                   alvo.getTipo() == TipoUsuario.USUARIO;
        }
        
        return false;
    }
    
    public static boolean podeVerRelatorios(UsuarioDTO usuario) {
        return usuario != null && 
               (usuario.getTipo() == TipoUsuario.ADMIN || 
                usuario.getTipo() == TipoUsuario.TECNICO ||
                usuario.getTipo() == TipoUsuario.GERENTE);
    }
    
    public static boolean podeGerarFaturamento(UsuarioDTO usuario) {
        return usuario != null && 
               (usuario.getTipo() == TipoUsuario.ADMIN || 
                usuario.getTipo() == TipoUsuario.TECNICO ||
                usuario.getTipo() == TipoUsuario.GERENTE);
    }
    
    public static String getDescricaoTipo(TipoUsuario tipo) {
        switch (tipo) {
            case ADMIN: return "Administrador";
            case TECNICO: return "Técnico";
            case GERENTE: return "Gerente";
            case USUARIO: return "Usuário";
            default: return "Desconhecido";
        }
    }
}