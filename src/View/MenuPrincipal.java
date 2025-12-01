package View;

import javax.swing.*;
import java.awt.*;
import Util.PermissaoUtil;
import Controller.HelpDeskController;
import DTO.UsuarioDTO;
import ENUM.TipoUsuario;

public class MenuPrincipal extends JMenuBar {
    private HelpDeskController controller;
    private HelpDeskUI mainFrame;
    
    public MenuPrincipal(HelpDeskController controller, HelpDeskUI mainFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        
        System.out.println("=== DEBUG MENUPRINCIPAL ===");
        System.out.println("Controller: " + controller);
        System.out.println("MainFrame: " + mainFrame);
        
        UsuarioDTO usuario = controller.getUsuarioLogado();
        System.out.println("Usuário do controller: " + usuario);
        
        if (usuario == null) {
            System.out.println("❌ ERRO: Usuário é NULL no MenuPrincipal!");
            criarMenuFake(); // Menu temporário para teste
        } else {
            System.out.println("✅ Usuário encontrado: " + usuario.getUsername());
            System.out.println("Tipo: " + usuario.getTipo());
            System.out.println("ID Tipo: " + usuario.getTipo().getId());
            criarMenuReal(usuario);
        }
    }
    
    private void criarMenuFake() {
        System.out.println("Criando menu fake para teste...");
        
        JMenu menuTeste = new JMenu("TESTE");
        JMenuItem item1 = new JMenuItem("Item 1");
        JMenuItem item2 = new JMenuItem("Item 2");
        
        menuTeste.add(item1);
        menuTeste.add(item2);
        add(menuTeste);
        
        JMenu menuSair = new JMenu("SAIR");
        JMenuItem sair = new JMenuItem("Sair");
        sair.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarLogin();
        });
        menuSair.add(sair);
        add(menuSair);
        
        System.out.println("✅ Menu fake criado!");
    }
    
    private void criarMenuReal(UsuarioDTO usuario) {
        System.out.println("Criando menu real...");
        
        boolean podeGerenciar = PermissaoUtil.podeGerenciarSistema(usuario);
        System.out.println("podeGerenciarSistema: " + podeGerenciar);
        
        JMenu menuTickets = new JMenu("Tickets");
        JMenuItem itemNovoTicket = new JMenuItem("Novo Ticket");
        JMenuItem itemMeusTickets = new JMenuItem("Meus Tickets");
        
        itemNovoTicket.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarTickets();
        });
        
        itemMeusTickets.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarTickets();
        });
        
        menuTickets.add(itemNovoTicket);
        menuTickets.add(itemMeusTickets);
        add(menuTickets);
        
        if (podeGerenciar) {
            System.out.println("✅ Criando menu Gerenciar...");
            JMenu menuGerenciar = new JMenu("Gerenciar");
            
            JMenuItem itemCategorias = new JMenuItem("Categorias");
            JMenuItem itemUsuarios = new JMenuItem("Usuários");
            
            itemCategorias.addActionListener(e -> {
                if (mainFrame != null) mainFrame.mostrarCategorias();
            });
            
            itemUsuarios.addActionListener(e -> {
                if (mainFrame != null) mainFrame.mostrarUsuarios();
            });
            
            menuGerenciar.add(itemCategorias);
            menuGerenciar.add(itemUsuarios);
            add(menuGerenciar);
        } else {
            System.out.println("❌ Não pode gerenciar!");
        }
        
        JMenu menuUsuario = new JMenu(usuario.getUsername());
        JMenuItem itemSair = new JMenuItem("Sair");
        
        itemSair.addActionListener(e -> {
            controller.logout();
            if (mainFrame != null) mainFrame.mostrarLogin();
        });
        
        menuUsuario.add(itemSair);
        add(menuUsuario);
        
        System.out.println("✅ Menu real criado!");
    }
}