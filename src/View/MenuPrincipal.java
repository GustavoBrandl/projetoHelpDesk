package View;

import javax.swing.*;
import Util.PermissaoUtil;
import Controller.HelpDeskController;
import DTO.UsuarioDTO;

public class MenuPrincipal extends JMenuBar {
    private HelpDeskController controller;
    private HelpDeskUI mainFrame;
    
    public MenuPrincipal(HelpDeskController controller, HelpDeskUI mainFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;

        UsuarioDTO usuario = controller.getUsuarioLogado();

        if (usuario == null) {
            criarMenuFake();
        } else {
            criarMenuReal(usuario);
        }
    }
    
    private void criarMenuFake() {
        JMenu menuTeste = new JMenu("TESTE");
        menuTeste.add(new JMenuItem("Item 1"));
        menuTeste.add(new JMenuItem("Item 2"));
        add(menuTeste);

        JMenu menuSair = new JMenu("SAIR");
        JMenuItem sair = new JMenuItem("Sair");
        sair.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarLogin();
        });
        menuSair.add(sair);
        add(menuSair);
    }
    
    private void criarMenuReal(UsuarioDTO usuario) {
        boolean podeGerenciar = PermissaoUtil.podeGerenciarSistema(usuario);

        JMenu menuTickets = new JMenu("Tickets");

        JMenuItem itemNovoTicket = new JMenuItem("Novo Ticket");
        itemNovoTicket.addActionListener(e -> mainFrame.mostrarTickets());

        JMenuItem itemMeusTickets = new JMenuItem("Meus Tickets");
        itemMeusTickets.addActionListener(e -> mainFrame.mostrarTickets());

        menuTickets.add(itemNovoTicket);
        menuTickets.add(itemMeusTickets);

        add(menuTickets);

        if (podeGerenciar) {
            JMenu menuGerenciar = new JMenu("Gerenciar");

            JMenuItem itemCategorias = new JMenuItem("Categorias");
            itemCategorias.addActionListener(e -> mainFrame.mostrarCategorias());

            JMenuItem itemPrioridades = new JMenuItem("Prioridades");
            itemPrioridades.addActionListener(e -> mainFrame.mostrarPrioridades());

            JMenuItem itemStatus = new JMenuItem("Status");
            itemStatus.addActionListener(e -> mainFrame.mostrarStatus());

            JMenuItem itemDepartamentos = new JMenuItem("Departamentos");
            itemDepartamentos.addActionListener(e -> mainFrame.mostrarDepartamentos());

            JMenuItem itemUsuarios = new JMenuItem("Usuários");
            itemUsuarios.addActionListener(e -> mainFrame.mostrarUsuarios());

            menuGerenciar.add(itemCategorias);
            menuGerenciar.add(itemPrioridades);
            menuGerenciar.add(itemStatus);
            menuGerenciar.add(itemDepartamentos);
            menuGerenciar.add(itemUsuarios);

            add(menuGerenciar);
        }

        JMenu menuUsuario = new JMenu(usuario.getUsername());
        JMenuItem itemSair = new JMenuItem("Sair");
        
        itemSair.addActionListener(e -> {
            controller.logout();
            if (mainFrame != null) mainFrame.mostrarLogin();
        });
        
        menuUsuario.add(itemSair);
        add(menuUsuario);
    }
}
