package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.TicketDTO;
import DTO.UsuarioDTO;
import Controller.HelpDeskController;
import Util.PermissaoUtil;
import ENUM.TipoUsuario;

public class ChamadosFinalizadosPanel extends JPanel {
    private JTable ticketsTable;
    private DefaultTableModel tableModel;
    private JButton verDetalhesButton;
    private HelpDeskUI mainFrame;

    public ChamadosFinalizadosPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        
        // Criar tabela
        String[] colunas = {"ID", "Sobre", "Categoria", "Prioridade", "Status", "Data Finalização", "Tempo (h)"};
        tableModel = new DefaultTableModel(new Object[][]{}, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        ticketsTable = new JTable(tableModel);
        ticketsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketsTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(ticketsTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        verDetalhesButton = new JButton("Ver Detalhes");
        verDetalhesButton.setEnabled(false);
        verDetalhesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalhes();
            }
        });
        
        panelBotoes.add(verDetalhesButton);
        add(panelBotoes, BorderLayout.SOUTH);
        
        // Adicionar listener de seleção
        ticketsTable.getSelectionModel().addListSelectionListener(e -> atualizarBotoes());
    }

    public void carregarTickets() {
        tableModel.setRowCount(0);
        
        try {
            var controller = mainFrame.getController();
            var usuarioLogado = controller.getUsuarioLogado();
            
            if (usuarioLogado == null) return;
            
            var ticketController = controller.getTicketController();
            List<TicketDTO> todosTickets = ticketController.pesquisarTodos();
            
            // Filtrar apenas tickets finalizados (status = 3)
            // TECNICO: vê todos os finalizados
            // GERENTE: vê finalizados da sua organização
            // USUARIO: vê apenas seus finalizados
            for (TicketDTO ticket : todosTickets) {
                if (ticket.getStatus() != null && ticket.getStatus().getId() == 3) {
                    boolean incluir = false;
                    
                    if (usuarioLogado.getTipo() == TipoUsuario.ADMIN || 
                        usuarioLogado.getTipo() == TipoUsuario.TECNICO) {
                        // ADMIN e TECNICO veem todos os finalizados
                        incluir = true;
                    } else if (usuarioLogado.getTipo() == TipoUsuario.GERENTE) {
                        // GERENTE vê apenas da sua organização
                        if (ticket.getSolicitante() != null && 
                            ticket.getSolicitante().getOrganizacao() != null &&
                            usuarioLogado.getOrganizacao() != null &&
                            ticket.getSolicitante().getOrganizacao().getId().equals(usuarioLogado.getOrganizacao().getId())) {
                            incluir = true;
                        }
                    } else if (usuarioLogado.getTipo() == TipoUsuario.USUARIO) {
                        // USUARIO vê apenas seus
                        if (ticket.getSolicitante() != null && 
                            ticket.getSolicitante().getId() == usuarioLogado.getId()) {
                            incluir = true;
                        }
                    }
                    
                    if (incluir) {
                        Object[] row = {
                            ticket.getId(),
                            ticket.getSobre(),
                            ticket.getCategoria() != null ? ticket.getCategoria().getNome() : "",
                            ticket.getPrioridade() != null ? ticket.getPrioridade().getNome() : "",
                            ticket.getStatus() != null ? ticket.getStatus().getNome() : "",
                            ticket.getDataHoraFinalizacao() != null ? ticket.getDataHoraFinalizacao().toLocalDate() : "",
                            ticket.getTempoChamado() != null ? String.format("%.2f", ticket.getTempoChamado()) : "0"
                        };
                        tableModel.addRow(row);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        int linhaSelecionada = ticketsTable.getSelectedRow();
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        verDetalhesButton.setEnabled(usuarioLogado != null && linhaSelecionada >= 0);
    }

    private void verDetalhes() {
        int linha = ticketsTable.getSelectedRow();
        if (linha < 0) return;
        
        Integer ticketId = (Integer) tableModel.getValueAt(linha, 0);
        
        try {
            var controller = mainFrame.getController();
            var ticketController = controller.getTicketController();
            TicketDTO ticket = ticketController.pesquisarPorId(ticketId);
            
            if (ticket != null) {
                StringBuilder detalhes = new StringBuilder();
                detalhes.append("ID: ").append(ticket.getId()).append("\n");
                detalhes.append("Sobre: ").append(ticket.getSobre()).append("\n");
                detalhes.append("Descrição: ").append(ticket.getDescricao()).append("\n");
                detalhes.append("Categoria: ").append(ticket.getCategoria() != null ? ticket.getCategoria().getNome() : "-").append("\n");
                detalhes.append("Prioridade: ").append(ticket.getPrioridade() != null ? ticket.getPrioridade().getNome() : "-").append("\n");
                detalhes.append("Status: ").append(ticket.getStatus() != null ? ticket.getStatus().getNome() : "-").append("\n");
                detalhes.append("Solicitante: ").append(ticket.getSolicitante() != null ? ticket.getSolicitante().getUsername() : "-").append("\n");
                detalhes.append("Técnico: ").append(ticket.getTecnico() != null ? ticket.getTecnico().getUsername() : "-").append("\n");
                detalhes.append("Data Abertura: ").append(ticket.getDataCriacao()).append("\n");
                detalhes.append("Data Finalização: ").append(ticket.getDataHoraFinalizacao()).append("\n");
                detalhes.append("Tempo Chamado: ").append(String.format("%.2f", ticket.getTempoChamado() != null ? ticket.getTempoChamado() : 0)).append(" horas");
                
                JTextArea textArea = new JTextArea(detalhes.toString());
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                
                JOptionPane.showMessageDialog(this, scrollPane, "Detalhes do Ticket", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private Integer getTicketIdSelecionado() {
        int linha = ticketsTable.getSelectedRow();
        if (linha < 0) return null;
        return (Integer) tableModel.getValueAt(linha, 0);
    }

    public void atualizar() {
        carregarTickets();
        atualizarBotoes();
    }
}
