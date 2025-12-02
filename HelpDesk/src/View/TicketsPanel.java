package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.TicketDTO;
import DTO.CategoriaDTO;
import Controller.HelpDeskController;
import Util.PermissaoUtil;
import ENUM.TipoUsuario;

public class TicketsPanel extends JPanel {
    private JTable ticketsTable;
    private DefaultTableModel tableModel;
    private JButton novoTicketButton;
    private JButton atualizarButton;
    private JButton sairButton;
    private JButton gerenciarButton;
    private JButton atenderButton;
    private JButton finalizarButton;
    private HelpDeskUI mainFrame;

    public TicketsPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("LISTA DE TICKETS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Sobre", "Descrição", "Categoria", "Prioridade", "Status", "Solicitante"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ticketsTable = new JTable(tableModel);
        ticketsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        ticketsTable.setRowHeight(25);
        ticketsTable.getSelectionModel().addListSelectionListener(e -> atualizarBotoes());
        JScrollPane scrollPane = new JScrollPane(ticketsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novoTicketButton = new JButton("NOVO TICKET");
        novoTicketButton.setFont(new Font("Arial", Font.BOLD, 12));
        novoTicketButton.setBackground(new Color(76, 175, 80));
        novoTicketButton.setForeground(Color.WHITE);
        novoTicketButton.setFocusPainted(false);
        bottomPanel.add(novoTicketButton);

        atenderButton = new JButton("ATENDER");
        atenderButton.setFont(new Font("Arial", Font.BOLD, 12));
        atenderButton.setBackground(new Color(33, 150, 243));
        atenderButton.setForeground(Color.WHITE);
        atenderButton.setFocusPainted(false);
        bottomPanel.add(atenderButton);

        finalizarButton = new JButton("FINALIZAR");
        finalizarButton.setFont(new Font("Arial", Font.BOLD, 12));
        finalizarButton.setBackground(new Color(255, 152, 0));
        finalizarButton.setForeground(Color.WHITE);
        finalizarButton.setFocusPainted(false);
        bottomPanel.add(finalizarButton);

        atualizarButton = new JButton("ATUALIZAR");
        atualizarButton.setFont(new Font("Arial", Font.BOLD, 12));
        atualizarButton.setBackground(new Color(158, 158, 158));
        atualizarButton.setForeground(Color.WHITE);
        atualizarButton.setFocusPainted(false);
        bottomPanel.add(atualizarButton);

        gerenciarButton = new JButton("GERENCIAR");
        gerenciarButton.setFont(new Font("Arial", Font.BOLD, 12));
        gerenciarButton.setBackground(new Color(156, 39, 176));
        gerenciarButton.setForeground(Color.WHITE);
        gerenciarButton.setFocusPainted(false);
        bottomPanel.add(gerenciarButton);

        sairButton = new JButton("SAIR");
        sairButton.setFont(new Font("Arial", Font.BOLD, 12));
        sairButton.setBackground(new Color(244, 67, 54));
        sairButton.setForeground(Color.WHITE);
        sairButton.setFocusPainted(false);
        bottomPanel.add(sairButton);

        add(bottomPanel, BorderLayout.SOUTH);

        novoTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovoTicketReal();
            }
        });

        atenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atenderTicketSelecionado();
            }
        });

        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarTicketSelecionado();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTickets();
            }
        });

        gerenciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarCategorias();
            }
        });

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getController().logout();
                mainFrame.mostrarLogin();
            }
        });

        carregarTickets();
        atualizarBotoes();
    }

    private void carregarTickets() {
        tableModel.setRowCount(0);
        try {
            var controller = mainFrame.getController();
            var usuarioLogado = controller.getUsuarioLogado();
            
            if (usuarioLogado == null) {
                System.out.println("DEBUG TicketsPanel: Nenhum usuário logado, pulando carregamento de tickets");
                return;
            }
            
            var ticketController = controller.getTicketController();
            List<TicketDTO> tickets;
            
            // TECNICO e ADMIN veem todos os tickets abertos (status != 3)
            // GERENTE vê todos os tickets da sua organização (abertos e fechados)
            // USUARIO vê apenas seus próprios tickets abertos
            if (PermissaoUtil.podeVerTodosTickets(usuarioLogado)) {
                // ADMIN e TECNICO veem TODOS os abertos
                tickets = ticketController.pesquisarTodos();
            } else {
                // USUARIO e GERENTE veem apenas seus
                tickets = ticketController.pesquisarPorUsuario(usuarioLogado);
            }
            
            if (tickets != null && !tickets.isEmpty()) {
                for (TicketDTO ticket : tickets) {
                    // Excluir tickets com status = 3 (Finalizado)
                    if (ticket.getStatus() != null && ticket.getStatus().getId() == 3) {
                        continue;
                    }
                    
                    // Se é GERENTE, verificar se o ticket é da mesma organização
                    if (usuarioLogado.getTipo() == TipoUsuario.GERENTE) {
                        if (ticket.getSolicitante() == null || 
                            ticket.getSolicitante().getOrganizacao() == null ||
                            usuarioLogado.getOrganizacao() == null ||
                            !ticket.getSolicitante().getOrganizacao().getId().equals(usuarioLogado.getOrganizacao().getId())) {
                            continue;
                        }
                    }
                    
                    // Se é USUARIO, verificar se é seu ticket
                    if (usuarioLogado.getTipo() == TipoUsuario.USUARIO) {
                        if (ticket.getSolicitante() == null || 
                            ticket.getSolicitante().getId() != usuarioLogado.getId()) {
                            continue;
                        }
                    }
                    
                    String categoriaNome = ticket.getCategoria() != null ? 
                        ticket.getCategoria().getNome() : "N/A";
                    String prioridadeNome = ticket.getPrioridade() != null ? 
                        ticket.getPrioridade().getNome() : "N/A";
                    String statusNome = ticket.getStatus() != null ? 
                        ticket.getStatus().getNome() : "N/A";
                    String solicitanteNome = ticket.getSolicitante() != null ? 
                        ticket.getSolicitante().getUsername() : "N/A";
                    
                    tableModel.addRow(new Object[]{
                        ticket.getId(),
                        ticket.getSobre(),
                        ticket.getDescricao() != null ? 
                            ticket.getDescricao().substring(0, Math.min(30, ticket.getDescricao().length())) + "..." : "",
                        categoriaNome,
                        prioridadeNome,
                        statusNome,
                        solicitanteNome
                    });
                }
            }
            
            if (tableModel.getRowCount() == 0) {
                tableModel.addRow(new Object[]{"", "Nenhum ticket encontrado", "", "", "", "", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar tickets: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        atualizarBotoes();
    }

    private void atualizarBotoes() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (usuarioLogado == null) {
            System.out.println("DEBUG TicketsPanel: Usuário NÃO logado!");
            novoTicketButton.setEnabled(false);
            atenderButton.setEnabled(false);
            finalizarButton.setEnabled(false);
            gerenciarButton.setEnabled(false);
            return;
        }
        
        System.out.println("=========================================");
        System.out.println("DEBUG TicketsPanel: Usuário logado: " + usuarioLogado.getUsername());
        System.out.println("DEBUG TicketsPanel: Email: " + usuarioLogado.getEmail());
        System.out.println("DEBUG TicketsPanel: Tipo do usuário: " + usuarioLogado.getTipo());
        System.out.println("DEBUG TicketsPanel: ID do tipo: " + usuarioLogado.getTipo().getId());
        System.out.println("DEBUG TicketsPanel: É ADMIN? " + (usuarioLogado.getTipo() == TipoUsuario.ADMIN));
        System.out.println("DEBUG TicketsPanel: É TECNICO? " + (usuarioLogado.getTipo() == TipoUsuario.TECNICO));
        
        System.out.println("DEBUG TicketsPanel: podeCriarTicket: " + PermissaoUtil.podeCriarTicket(usuarioLogado));
        System.out.println("DEBUG TicketsPanel: podeAtenderTickets: " + PermissaoUtil.podeAtenderTickets(usuarioLogado));
        System.out.println("DEBUG TicketsPanel: podeGerenciarSistema: " + PermissaoUtil.podeGerenciarSistema(usuarioLogado));
        System.out.println("DEBUG TicketsPanel: podeVerTodosTickets: " + PermissaoUtil.podeVerTodosTickets(usuarioLogado));
        System.out.println("=========================================");
        
        novoTicketButton.setEnabled(PermissaoUtil.podeCriarTicket(usuarioLogado));
        atenderButton.setEnabled(PermissaoUtil.podeAtenderTickets(usuarioLogado));
        finalizarButton.setEnabled(PermissaoUtil.podeAtenderTickets(usuarioLogado));
        gerenciarButton.setEnabled(PermissaoUtil.podeGerenciarSistema(usuarioLogado));
        
        int linhaSelecionada = ticketsTable.getSelectedRow();
        atenderButton.setEnabled(atenderButton.isEnabled() && linhaSelecionada >= 0);
        finalizarButton.setEnabled(finalizarButton.isEnabled() && linhaSelecionada >= 0);
    }

    private Integer getTicketIdSelecionado() {
        int linha = ticketsTable.getSelectedRow();
        if (linha >= 0) {
            Object idObj = tableModel.getValueAt(linha, 0);
            if (idObj instanceof Integer) return (Integer) idObj;
            if (idObj instanceof String) {
                try {
                    return Integer.parseInt((String) idObj);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private void atenderTicketSelecionado() {
        Integer ticketId = getTicketIdSelecionado();
        if (ticketId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um ticket para atender.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeAtenderTickets(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para atender tickets.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja atender o ticket #" + ticketId + "?",
            "Confirmar Atendimento",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var ticketController = controller.getTicketController();
                boolean sucesso = ticketController.atender(ticketId, usuarioLogado);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Ticket atendido com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarTickets();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao atender ticket.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void finalizarTicketSelecionado() {
        Integer ticketId = getTicketIdSelecionado();
        if (ticketId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um ticket para finalizar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeAtenderTickets(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para finalizar tickets.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja finalizar o ticket #" + ticketId + "?",
            "Confirmar Finalização",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var ticketController = controller.getTicketController();
                boolean sucesso = ticketController.finalizarTicket(ticketId, usuarioLogado);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Ticket finalizado com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarTickets();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao finalizar ticket.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void criarNovoTicketReal() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (usuarioLogado == null) {
            JOptionPane.showMessageDialog(this, 
                "Usuário não logado!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!PermissaoUtil.podeCriarTicket(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para criar tickets.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        NovoTicketDialogo dialog = new NovoTicketDialogo(
            (Frame) SwingUtilities.getWindowAncestor(this), 
            controller
        );
        dialog.setVisible(true);
        
        if (!dialog.isConfirmado()) {
            return;
        }
        
        try {
            Integer categoriaId = dialog.getCategoriaId();
            Integer prioridadeId = dialog.getPrioridadeId();
            
            if (categoriaId == null) {
                JOptionPane.showMessageDialog(this, 
                    "Selecione uma categoria válida.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            var ticketController = controller.getTicketController();
            boolean sucesso = ticketController.criarTicket(
                dialog.getSobre(), 
                dialog.getDescricao(), 
                categoriaId,
                prioridadeId,
                usuarioLogado
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Ticket criado com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarTickets();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao criar ticket.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro: " + ex.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    public void atualizar() {
        carregarTickets();
        atualizarBotoes();
    }
}