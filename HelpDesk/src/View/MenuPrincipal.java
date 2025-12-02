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
        boolean podeAtender = PermissaoUtil.podeAtenderTickets(usuario);
        System.out.println("DEBUG MenuPrincipal: Criando menu para usuário: " + usuario.getUsername());
        System.out.println("DEBUG MenuPrincipal: Pode gerenciar? " + podeGerenciar);
        System.out.println("DEBUG MenuPrincipal: Pode atender? " + podeAtender);

        JMenu menuTickets = new JMenu("Tickets");

        JMenuItem itemNovoTicket = new JMenuItem("Novo Ticket");
        itemNovoTicket.addActionListener(e -> {
            System.out.println("DEBUG MenuPrincipal: Clicou em 'Novo Ticket'");
            mainFrame.mostrarTickets();
        });

        JMenuItem itemMeusTickets = new JMenuItem("Meus Tickets");
        itemMeusTickets.addActionListener(e -> {
            System.out.println("DEBUG MenuPrincipal: Clicou em 'Meus Tickets'");
            mainFrame.mostrarTickets();
        });

        menuTickets.add(itemNovoTicket);
        menuTickets.add(itemMeusTickets);
        
        menuTickets.addSeparator();
        JMenuItem itemFinalizados = new JMenuItem("Chamados Finalizados");
        itemFinalizados.addActionListener(e -> {
            System.out.println("DEBUG MenuPrincipal: Clicou em 'Chamados Finalizados'");
            mainFrame.mostrarFinalizados();
        });
        menuTickets.add(itemFinalizados);
        
        if (podeAtender) {
            menuTickets.addSeparator();
            JMenuItem itemAtendimento = new JMenuItem("Meu Atendimento");
            itemAtendimento.addActionListener(e -> {
                System.out.println("DEBUG MenuPrincipal: Clicou em 'Meu Atendimento'");
                mainFrame.mostrarAtendimento();
            });
            menuTickets.add(itemAtendimento);
        }

        add(menuTickets);

        if (podeGerenciar) {
            JMenu menuGerenciar = new JMenu("Gerenciar");

            JMenuItem itemCategorias = new JMenuItem("Categorias");
            itemCategorias.addActionListener(e -> {
                System.out.println("DEBUG MenuPrincipal: Clicou em 'Categorias'");
                mainFrame.mostrarCategorias();
            });

            JMenuItem itemPrioridades = new JMenuItem("Prioridades");
            itemPrioridades.addActionListener(e -> {
                System.out.println("DEBUG MenuPrincipal: Clicou em 'Prioridades'");
                mainFrame.mostrarPrioridades();
            });

            JMenuItem itemStatus = new JMenuItem("Status");
            itemStatus.addActionListener(e -> {
                System.out.println("DEBUG MenuPrincipal: Clicou em 'Status'");
                mainFrame.mostrarStatus();
            });

            JMenuItem itemFaturamento = new JMenuItem("Faturamento");
            itemFaturamento.addActionListener(e -> {
                System.out.println("DEBUG MenuPrincipal: Clicou em 'Faturamento'");
                mainFrame.mostrarFaturamento();
            });

            JMenuItem itemDepartamentos = new JMenuItem("Departamentos");
            itemDepartamentos.addActionListener(e -> {
                System.out.println("DEBUG MenuPrincipal: Clicou em 'Departamentos'");
                mainFrame.mostrarDepartamentos();
            });

            JMenuItem itemUsuarios = new JMenuItem("Usuários");
            itemUsuarios.addActionListener(e -> {
                System.out.println("DEBUG MenuPrincipal: Clicou em 'Usuários'");
                mainFrame.mostrarUsuarios();
            });

            menuGerenciar.add(itemCategorias);
            menuGerenciar.add(itemPrioridades);
            menuGerenciar.add(itemStatus);
            menuGerenciar.addSeparator();
            menuGerenciar.add(itemFaturamento);
            menuGerenciar.add(itemDepartamentos);
            menuGerenciar.add(itemUsuarios);

            // Apenas ADMIN pode gerenciar organizações
            if (usuario.getTipo().getId() == 4) {
                menuGerenciar.addSeparator();
                JMenuItem itemOrganizacoes = new JMenuItem("Organizações");
                itemOrganizacoes.addActionListener(e -> {
                    System.out.println("DEBUG MenuPrincipal: Clicou em 'Organizações'");
                    mainFrame.mostrarOrganizacoes();
                });
                menuGerenciar.add(itemOrganizacoes);
            }

            add(menuGerenciar);
            System.out.println("DEBUG MenuPrincipal: Menu 'Gerenciar' adicionado com itens");
        } else {
            System.out.println("DEBUG MenuPrincipal: Usuário não tem permissão de gerenciamento");
        }

        JMenu menuUsuario = new JMenu(usuario.getUsername());
        JMenuItem itemSair = new JMenuItem("Sair");
        
        itemSair.addActionListener(e -> {
            System.out.println("DEBUG MenuPrincipal: Clicou em 'Sair'");
            controller.logout();
            if (mainFrame != null) mainFrame.mostrarLogin();
        });
        
        menuUsuario.add(itemSair);
        add(menuUsuario);
        System.out.println("DEBUG MenuPrincipal: Menu do usuário adicionado");
    }
}
