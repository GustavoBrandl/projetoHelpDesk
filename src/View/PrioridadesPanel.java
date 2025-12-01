package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import BO.PrioridadeBO;
import DTO.PrioridadeDTO;

public class PrioridadesPanel extends JPanel {
    private JTable prioridadesTable;
    private DefaultTableModel tableModel;
    private JButton novaButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;
    private PrioridadeBO prioridadeBO;

    public PrioridadesPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        this.prioridadeBO = new PrioridadeBO();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Painel Superior
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("GERENCIAR PRIORIDADES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "Nome", "Valor", "Padrão"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        prioridadesTable = new JTable(tableModel);
        prioridadesTable.setFont(new Font("Arial", Font.PLAIN, 11));
        prioridadesTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(prioridadesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novaButton = new JButton("NOVA PRIORIDADE");
        novaButton.setFont(new Font("Arial", Font.BOLD, 12));
        novaButton.setBackground(new Color(76, 175, 80));
        novaButton.setForeground(Color.WHITE);
        novaButton.setFocusPainted(false);
        bottomPanel.add(novaButton);

        voltarButton = new JButton("VOLTAR");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 12));
        voltarButton.setBackground(new Color(158, 158, 158));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFocusPainted(false);
        bottomPanel.add(voltarButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Ações dos botões
        novaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovaPrioridade();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
            }
        });

        carregarPrioridades();
    }

    private void carregarPrioridades() {
        tableModel.setRowCount(0);
        try {
            List<PrioridadeDTO> prioridades = prioridadeBO.pesquisarTodos();
            if (prioridades != null && !prioridades.isEmpty()) {
                for (PrioridadeDTO prioridade : prioridades) {
                    tableModel.addRow(new Object[]{
                        prioridade.getId(),
                        prioridade.getNome(),
                        prioridade.getValor(),
                        prioridade.isPadrao() ? "Sim" : "Não"
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar prioridades: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarNovaPrioridade() {
        String nome = JOptionPane.showInputDialog(this, "Nome da prioridade:", "Nova Prioridade", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }

        String valorStr = JOptionPane.showInputDialog(this, "Valor da prioridade:", "Nova Prioridade", JOptionPane.PLAIN_MESSAGE);
        if (valorStr == null || valorStr.trim().isEmpty()) {
            return;
        }

        try {
            int valor = Integer.parseInt(valorStr);
            int response = JOptionPane.showConfirmDialog(this, "Definir como padrão?", "Nova Prioridade", JOptionPane.YES_NO_OPTION);
            boolean padrao = response == JOptionPane.YES_OPTION;

            if (prioridadeBO.criarPrioridade(nome, valor, padrao)) {
                JOptionPane.showMessageDialog(this, "Prioridade criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarPrioridades();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar prioridade!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor de prioridade inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
