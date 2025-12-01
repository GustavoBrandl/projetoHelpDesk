package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import BO.TicketBO;
import DTO.TicketDTO;

public class TicketsPanel extends JPanel {
    private JTable ticketsTable;
    private DefaultTableModel tableModel;
    private JButton novoTicketButton;
    private JButton atualizarButton;
    private JButton sairButton;
    private JButton gerenciarButton;
    private HelpDeskUI mainFrame;
    private TicketBO ticketBO;

    public TicketsPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        this.ticketBO = new TicketBO();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Painel Superior com Título
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("LISTA DE TICKETS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Tabela de Tickets
        String[] colunas = {"ID", "Sobre", "Descrição", "Categoria", "Prioridade", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ticketsTable = new JTable(tableModel);
        ticketsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        ticketsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(ticketsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novoTicketButton = new JButton("NOVO TICKET");
        novoTicketButton.setFont(new Font("Arial", Font.BOLD, 12));
        novoTicketButton.setBackground(new Color(76, 175, 80));
        novoTicketButton.setForeground(Color.WHITE);
        novoTicketButton.setFocusPainted(false);
        bottomPanel.add(novoTicketButton);

        atualizarButton = new JButton("ATUALIZAR");
        atualizarButton.setFont(new Font("Arial", Font.BOLD, 12));
        atualizarButton.setBackground(new Color(33, 150, 243));
        atualizarButton.setForeground(Color.WHITE);
        atualizarButton.setFocusPainted(false);
        bottomPanel.add(atualizarButton);

        gerenciarButton = new JButton("GERENCIAR");
        gerenciarButton.setFont(new Font("Arial", Font.BOLD, 12));
        gerenciarButton.setBackground(new Color(255, 152, 0));
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

        // Ações dos botões
        novoTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovoTicket();
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
                mainFrame.mostrarLogin();
            }
        });

        // Carregar tickets ao inicializar
        carregarTickets();
    }

    private void carregarTickets() {
        tableModel.setRowCount(0);
        try {
            List<TicketDTO> tickets = ticketBO.listarTodos();
            if (tickets != null && !tickets.isEmpty()) {
                for (TicketDTO ticket : tickets) {
                    String categoriaNome = ticket.getCategoria() != null ? String.valueOf(ticket.getCategoria().getId()) : "N/A";
                    String prioridadeNome = ticket.getPrioridade() != null ? String.valueOf(ticket.getPrioridade().getId()) : "N/A";
                    String statusNome = ticket.getStatus() != null ? String.valueOf(ticket.getStatus().getId()) : "N/A";
                    
                    tableModel.addRow(new Object[]{
                        ticket.getId(),
                        ticket.getSobre(),
                        ticket.getDescricao() != null ? ticket.getDescricao().substring(0, Math.min(30, ticket.getDescricao().length())) + "..." : "",
                        categoriaNome,
                        prioridadeNome,
                        statusNome
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tickets: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarNovoTicket() {
        String sobre = JOptionPane.showInputDialog(this, "Sobre do ticket:", "Novo Ticket", JOptionPane.PLAIN_MESSAGE);
        if (sobre == null || sobre.trim().isEmpty()) {
            return;
        }

        String descricao = JOptionPane.showInputDialog(this, "Descrição do ticket:", "Novo Ticket", JOptionPane.PLAIN_MESSAGE);
        if (descricao == null || descricao.trim().isEmpty()) {
            return;
        }

        String categoriaStr = JOptionPane.showInputDialog(this, "ID da Categoria:", "Novo Ticket", JOptionPane.PLAIN_MESSAGE);
        if (categoriaStr == null || categoriaStr.trim().isEmpty()) {
            return;
        }

        try {
            int categoriaId = Integer.parseInt(categoriaStr);
            // Aqui você implementaria a lógica de criar um novo ticket
            JOptionPane.showMessageDialog(this, "Ticket criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarTickets();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de categoria inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
