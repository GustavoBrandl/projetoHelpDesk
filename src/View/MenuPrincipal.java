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
        criarMenu();
    }
    
    private void criarMenu() {
        UsuarioDTO usuario = controller.getUsuarioLogado();
        if (usuario == null) return;
        
        JMenu menuTickets = new JMenu("Tickets");
        JMenuItem itemNovoTicket = new JMenuItem("Novo Ticket");
        JMenuItem itemMeusTickets = new JMenuItem("Meus Tickets");
        JMenuItem itemTodosTickets = new JMenuItem("Todos Tickets");
        
        itemNovoTicket.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarTickets();
        });
        
        itemMeusTickets.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarTickets();
        });
        
        itemTodosTickets.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarTickets();
        });
        
        itemNovoTicket.setEnabled(PermissaoUtil.podeCriarTicket(usuario));
        itemTodosTickets.setEnabled(PermissaoUtil.podeVerTodosTickets(usuario));
        
        menuTickets.add(itemNovoTicket);
        menuTickets.add(itemMeusTickets);
        menuTickets.addSeparator();
        menuTickets.add(itemTodosTickets);
        
        if (PermissaoUtil.podeGerenciarSistema(usuario)) {
            JMenu menuGerenciar = new JMenu("Gerenciar");
            JMenuItem itemCategorias = new JMenuItem("Categorias");
            JMenuItem itemPrioridades = new JMenuItem("Prioridades");
            JMenuItem itemDepartamentos = new JMenuItem("Departamentos");
            JMenuItem itemStatus = new JMenuItem("Status");
            
            itemCategorias.addActionListener(e -> {
                if (mainFrame != null) mainFrame.mostrarCategorias();
            });
            
            itemPrioridades.addActionListener(e -> {
                if (mainFrame != null) mainFrame.mostrarPrioridades();
            });
            
            itemDepartamentos.addActionListener(e -> {
                if (mainFrame != null) mainFrame.mostrarDepartamentos();
            });
            
            itemStatus.addActionListener(e -> {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Tela de Status em desenvolvimento.", 
                    "Em desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
            });
            
            menuGerenciar.add(itemCategorias);
            menuGerenciar.add(itemPrioridades);
            menuGerenciar.add(itemDepartamentos);
            menuGerenciar.add(itemStatus);
            
            if (usuario.getTipo() == TipoUsuario.ADMIN || 
                usuario.getTipo() == TipoUsuario.TECNICO) {
                JMenuItem itemUsuarios = new JMenuItem("Usuários");
                itemUsuarios.addActionListener(e -> {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Tela de Usuários em desenvolvimento.", 
                        "Em desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
                });
                menuGerenciar.add(itemUsuarios);
            }
            
            add(menuGerenciar);
        }
        
        if (PermissaoUtil.podeVerRelatorios(usuario)) {
            JMenu menuRelatorios = new JMenu("Relatórios");
            JMenuItem itemRelTickets = new JMenuItem("Relatório de Tickets");
            JMenuItem itemRelFaturamento = new JMenuItem("Faturamento");
            
            itemRelTickets.addActionListener(e -> {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Relatórios em desenvolvimento.", 
                    "Em desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
            });
            
            itemRelFaturamento.addActionListener(e -> {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Faturamento em desenvolvimento.", 
                    "Em desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
            });
            
            if (usuario.getTipo() == TipoUsuario.GERENTE) {
                itemRelFaturamento.setText("Faturamento (Minha Org)");
            }
            
            menuRelatorios.add(itemRelTickets);
            menuRelatorios.add(itemRelFaturamento);
            add(menuRelatorios);
        }
        
        add(menuTickets);
        
        add(Box.createHorizontalGlue());
        
        JMenu menuUsuario = new JMenu(usuario.getUsername() + " (" + 
            PermissaoUtil.getDescricaoTipo(usuario.getTipo()) + ")");
        
        JMenuItem itemPerfil = new JMenuItem("Meu Perfil");
        JMenuItem itemSair = new JMenuItem("Sair");
        
        itemPerfil.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame, 
                "Perfil em desenvolvimento.", 
                "Em desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
        });
        
        itemSair.addActionListener(e -> {
            controller.logout();
            if (mainFrame != null) mainFrame.mostrarLogin();
        });
        
        menuUsuario.add(itemPerfil);
        menuUsuario.addSeparator();
        menuUsuario.add(itemSair);
        
        add(menuUsuario);
    }
}