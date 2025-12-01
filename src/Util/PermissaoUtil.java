package Util;

import ENUM.TipoUsuario;
import DTO.UsuarioDTO;

public class PermissaoUtil {
    
    public static boolean podeTudo(UsuarioDTO usuario) {
        if (usuario == null) {
            System.out.println("❌ PermissaoUtil.podeTudo: usuario é NULL");
            return false;
        }
        boolean resultado = usuario.getTipo() == TipoUsuario.ADMIN;
        System.out.println("🔐 PermissaoUtil.podeTudo: " + resultado + " (Tipo=" + usuario.getTipo() + ", ID=" + usuario.getTipo().getId() + ")");
        return resultado;
    }
    
    public static boolean podeGerenciarSistema(UsuarioDTO usuario) {
        if (usuario == null) {
            System.out.println("❌ PermissaoUtil.podeGerenciarSistema: usuario é NULL");
            return false;
        }
        
        boolean resultado = usuario.getTipo() == TipoUsuario.ADMIN || 
                           usuario.getTipo() == TipoUsuario.TECNICO;
        
        System.out.println("🔐 PermissaoUtil.podeGerenciarSistema: " + resultado);
        System.out.println("   → Tipo usuário: " + usuario.getTipo());
        System.out.println("   → ID tipo: " + usuario.getTipo().getId());
        System.out.println("   → É ADMIN? " + (usuario.getTipo() == TipoUsuario.ADMIN));
        System.out.println("   → É TECNICO? " + (usuario.getTipo() == TipoUsuario.TECNICO));
        System.out.println("   → ADMIN ID: " + TipoUsuario.ADMIN.getId());
        System.out.println("   → TECNICO ID: " + TipoUsuario.TECNICO.getId());
        
        return resultado;
    }
    
    public static boolean podeAtenderTickets(UsuarioDTO usuario) {
        if (usuario == null) {
            System.out.println("❌ PermissaoUtil.podeAtenderTickets: usuario é NULL");
            return false;
        }
        
        boolean resultado = usuario.getTipo() == TipoUsuario.ADMIN || 
                           usuario.getTipo() == TipoUsuario.TECNICO;
        
        System.out.println("🔐 PermissaoUtil.podeAtenderTickets: " + resultado + " (Tipo=" + usuario.getTipo() + ")");
        return resultado;
    }
    
    public static boolean podeVerTodosTickets(UsuarioDTO usuario) {
        if (usuario == null) {
            System.out.println("❌ PermissaoUtil.podeVerTodosTickets: usuario é NULL");
            return false;
        }
        
        boolean resultado = usuario.getTipo() == TipoUsuario.ADMIN || 
                           usuario.getTipo() == TipoUsuario.TECNICO;
        
        System.out.println("🔐 PermissaoUtil.podeVerTodosTickets: " + resultado + " (Tipo=" + usuario.getTipo() + ")");
        return resultado;
    }
    
    public static boolean podeVerOrgTickets(UsuarioDTO usuario) {
        if (usuario == null) return false;
        
        return usuario.getTipo() == TipoUsuario.ADMIN || 
               usuario.getTipo() == TipoUsuario.TECNICO ||
               usuario.getTipo() == TipoUsuario.GERENTE;
    }
    
    public static boolean podeCriarTicket(UsuarioDTO usuario) {
        if (usuario == null) return false;
        
        boolean resultado = usuario.isAtivo();
        System.out.println("🔐 PermissaoUtil.podeCriarTicket: " + resultado + " (Ativo=" + usuario.isAtivo() + ")");
        return resultado;
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
        if (usuario == null) return false;
        
        return usuario.getTipo() == TipoUsuario.ADMIN || 
               usuario.getTipo() == TipoUsuario.TECNICO ||
               usuario.getTipo() == TipoUsuario.GERENTE;
    }
    
    public static boolean podeGerarFaturamento(UsuarioDTO usuario) {
        if (usuario == null) return false;
        
        return usuario.getTipo() == TipoUsuario.ADMIN || 
               usuario.getTipo() == TipoUsuario.TECNICO ||
               usuario.getTipo() == TipoUsuario.GERENTE;
    }
    
    public static String getDescricaoTipo(TipoUsuario tipo) {
        if (tipo == null) return "Desconhecido";
        
        switch (tipo) {
            case ADMIN: return "Administrador";
            case TECNICO: return "Técnico";
            case GERENTE: return "Gerente";
            case USUARIO: return "Usuário";
            default: return "Desconhecido";
        }
    }
}