package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.TicketDTO;
import DTO.StatusDTO;
import DTO.PrioridadeDTO;
import DTO.UsuarioDTO;
import Controller.HelpDeskController;
import Util.PermissaoUtil;

public class AtendimentoPanel extends JPanel {
    private JTable ticketsTable;
    private DefaultTableModel tableModel;
    private JButton verDetalhesButton;
    private JButton mudarStatusButton;
    private JButton mudarPrioridadeButton;
    private JButton finalizarButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;

    public AtendimentoPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("TICKETS EM ATENDIMENTO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Sobre", "Solicitante", "Categoria", "Prioridade", "Status", "Criado em"};
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

        verDetalhesButton = new JButton("VER DETALHES");
        verDetalhesButton.setFont(new Font("Arial", Font.BOLD, 12));
        verDetalhesButton.setBackground(new Color(33, 150, 243));
        verDetalhesButton.setForeground(Color.WHITE);
        verDetalhesButton.setFocusPainted(false);
        bottomPanel.add(verDetalhesButton);

        mudarStatusButton = new JButton("MUDAR STATUS");
        mudarStatusButton.setFont(new Font("Arial", Font.BOLD, 12));
        mudarStatusButton.setBackground(new Color(255, 152, 0));
        mudarStatusButton.setForeground(Color.WHITE);
        mudarStatusButton.setFocusPainted(false);
        bottomPanel.add(mudarStatusButton);

        mudarPrioridadeButton = new JButton("MUDAR PRIORIDADE");
        mudarPrioridadeButton.setFont(new Font("Arial", Font.BOLD, 12));
        mudarPrioridadeButton.setBackground(new Color(103, 58, 183));
        mudarPrioridadeButton.setForeground(Color.WHITE);
        mudarPrioridadeButton.setFocusPainted(false);
        bottomPanel.add(mudarPrioridadeButton);

        finalizarButton = new JButton("FINALIZAR");
        finalizarButton.setFont(new Font("Arial", Font.BOLD, 12));
        finalizarButton.setBackground(new Color(244, 67, 54));
        finalizarButton.setForeground(Color.WHITE);
        finalizarButton.setFocusPainted(false);
        bottomPanel.add(finalizarButton);

        voltarButton = new JButton("VOLTAR");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 12));
        voltarButton.setBackground(new Color(158, 158, 158));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFocusPainted(false);
        bottomPanel.add(voltarButton);

        add(bottomPanel, BorderLayout.SOUTH);

        verDetalhesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalhes();
            }
        });

        mudarStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarStatus();
            }
        });

        mudarPrioridadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarPrioridade();
            }
        });

        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarTicket();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
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
                tableModel.addRow(new Object[]{"", "Nenhum usuário logado", "", "", "", "", ""});
                return;
            }
            
            var ticketController = controller.getTicketController();
            List<TicketDTO> todosTickets = ticketController.pesquisarTodos();
            
            if (todosTickets != null && !todosTickets.isEmpty()) {
                for (TicketDTO ticket : todosTickets) {
                    // Apenas mostrar tickets que esse usuário está atendendo e que NÃO estão finalizados
                    if (ticket.getTecnico() != null && 
                        ticket.getTecnico().getId() == usuarioLogado.getId() &&
                        (ticket.getStatus() == null || ticket.getStatus().getId() != 3)) {
                        
                        String solicitanteNome = ticket.getSolicitante() != null ? 
                            ticket.getSolicitante().getUsername() : "N/A";
                        String categoriaNome = ticket.getCategoria() != null ? 
                            ticket.getCategoria().getNome() : "N/A";
                        String prioridadeNome = ticket.getPrioridade() != null ? 
                            ticket.getPrioridade().getNome() : "N/A";
                        String statusNome = ticket.getStatus() != null ? 
                            ticket.getStatus().getNome() : "N/A";
                        String dataCriacao = ticket.getDataCriacao() != null ? 
                            ticket.getDataCriacao().toString() : "N/A";
                        
                        tableModel.addRow(new Object[]{
                            ticket.getId(),
                            ticket.getSobre(),
                            solicitanteNome,
                            categoriaNome,
                            prioridadeNome,
                            statusNome,
                            dataCriacao
                        });
                    }
                }
                
                if (tableModel.getRowCount() == 0) {
                    tableModel.addRow(new Object[]{"", "Nenhum ticket em atendimento", "", "", "", "", ""});
                }
            } else {
                tableModel.addRow(new Object[]{"", "Nenhum ticket encontrado", "", "", "", "", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar tickets: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        int linhaSelecionada = ticketsTable.getSelectedRow();
        boolean temSelecao = linhaSelecionada >= 0 && tableModel.getValueAt(linhaSelecionada, 0) instanceof Integer;
        
        verDetalhesButton.setEnabled(temSelecao);
        mudarStatusButton.setEnabled(temSelecao);
        mudarPrioridadeButton.setEnabled(temSelecao);
        finalizarButton.setEnabled(temSelecao);
    }

    private Integer getTicketIdSelecionado() {
        int linha = ticketsTable.getSelectedRow();
        if (linha >= 0) {
            Object idObj = tableModel.getValueAt(linha, 0);
            if (idObj instanceof Integer) return (Integer) idObj;
            if (idObj instanceof String) {
                try {
                    Integer id = Integer.parseInt((String) idObj);
                    return id > 0 ? id : null;
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private void verDetalhes() {
        Integer ticketId = getTicketIdSelecionado();
        if (ticketId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um ticket.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var ticketController = controller.getTicketController();
        TicketDTO ticket = ticketController.pesquisarPorId(ticketId);
        
        if (ticket == null) {
            JOptionPane.showMessageDialog(this, 
                "Ticket não encontrado.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String detalhes = "ID: " + ticket.getId() + "\n" +
                         "Sobre: " + ticket.getSobre() + "\n" +
                         "Descrição: " + ticket.getDescricao() + "\n" +
                         "Solicitante: " + (ticket.getSolicitante() != null ? ticket.getSolicitante().getUsername() : "N/A") + "\n" +
                         "Categoria: " + (ticket.getCategoria() != null ? ticket.getCategoria().getNome() : "N/A") + "\n" +
                         "Prioridade: " + (ticket.getPrioridade() != null ? ticket.getPrioridade().getNome() : "N/A") + "\n" +
                         "Status: " + (ticket.getStatus() != null ? ticket.getStatus().getNome() : "N/A") + "\n" +
                         "Criado em: " + ticket.getDataCriacao() + "\n" +
                         "Técnico: " + (ticket.getTecnico() != null ? ticket.getTecnico().getUsername() : "Sem atendimento");
        
        JOptionPane.showMessageDialog(this, detalhes, "Detalhes do Ticket", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mudarStatus() {
        Integer ticketId = getTicketIdSelecionado();
        if (ticketId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um ticket.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var ticketController = controller.getTicketController();
        
        List<StatusDTO> statuses = ticketController.pesquisarStatus();
        if (statuses == null || statuses.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nenhum status disponível.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        StatusDTO[] statusArray = statuses.toArray(new StatusDTO[0]);
        StatusDTO novoStatus = (StatusDTO) JOptionPane.showInputDialog(
            this,
            "Selecione o novo status:",
            "Mudar Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            statusArray,
            statusArray[0]
        );
        
        if (novoStatus != null) {
            try {
                var usuarioLogado = controller.getUsuarioLogado();
                boolean sucesso = ticketController.alterarStatusTicket(ticketId, novoStatus.getId(), usuarioLogado);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Status alterado com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarTickets();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao alterar status.", 
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

    private void mudarPrioridade() {
        Integer ticketId = getTicketIdSelecionado();
        if (ticketId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um ticket.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var ticketController = controller.getTicketController();
        
        List<PrioridadeDTO> prioridades = ticketController.pesquisarPrioridades();
        if (prioridades == null || prioridades.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nenhuma prioridade disponível.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        PrioridadeDTO[] prioridadeArray = prioridades.toArray(new PrioridadeDTO[0]);
        PrioridadeDTO novaPrioridade = (PrioridadeDTO) JOptionPane.showInputDialog(
            this,
            "Selecione a nova prioridade:",
            "Mudar Prioridade",
            JOptionPane.QUESTION_MESSAGE,
            null,
            prioridadeArray,
            prioridadeArray[0]
        );
        
        if (novaPrioridade != null) {
            try {
                var usuarioLogado = controller.getUsuarioLogado();
                boolean sucesso = ticketController.alterarPrioridade(ticketId, novaPrioridade.getId(), usuarioLogado);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Prioridade alterada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarTickets();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao alterar prioridade.", 
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

    private void finalizarTicket() {
        Integer ticketId = getTicketIdSelecionado();
        if (ticketId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um ticket.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Pedir tempo de atendimento
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel labelHora = new JLabel("Tempo gasto (HH:mm):");
        JTextField horaField = new JTextField("", 10);
        
        panel.add(labelHora);
        panel.add(horaField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Tempo de Atendimento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        
        String tempo = horaField.getText().trim();
        if (tempo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Informe o tempo gasto no atendimento.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar formato HH:mm e converter para Double (horas)
        if (!tempo.matches("\\d{1,2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, 
                "Formato inválido. Use HH:mm (ex: 2:30 para 2 horas e 30 minutos)", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Converter HH:mm para Double (horas)
        String[] partes = tempo.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        double tempoEmHoras = horas + (minutos / 60.0);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja finalizar o ticket #" + ticketId + " com tempo de " + tempo + "?",
            "Confirmar Finalização",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var controller = mainFrame.getController();
                var usuarioLogado = controller.getUsuarioLogado();
                var ticketController = controller.getTicketController();
                
                // Chamar finalizarTicket com o tempo informado
                boolean sucesso = ticketController.finalizarTicket(ticketId, usuarioLogado, tempoEmHoras);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Ticket finalizado com sucesso! Tempo: " + tempo, 
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
    
    public void atualizar() {
        carregarTickets();
        atualizarBotoes();
    }
}
