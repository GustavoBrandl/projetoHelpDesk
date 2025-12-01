package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import BO.CategoriaBO;
import DTO.CategoriaDTO;

public class CategoriasPanel extends JPanel {
    private JTable categoriasTable;
    private DefaultTableModel tableModel;
    private JButton novaButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;
    private CategoriaBO categoriaBO;

    public CategoriasPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        this.categoriaBO = new CategoriaBO();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Painel Superior
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("GERENCIAR CATEGORIAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "Nome", "Total Tickets"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        categoriasTable = new JTable(tableModel);
        categoriasTable.setFont(new Font("Arial", Font.PLAIN, 11));
        categoriasTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(categoriasTable);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novaButton = new JButton("NOVA CATEGORIA");
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
                criarNovaCategoria();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
            }
        });

        carregarCategorias();
    }

    private void carregarCategorias() {
        tableModel.setRowCount(0);
        try {
            List<CategoriaDTO> categorias = categoriaBO.pesquisarTodos();
            if (categorias != null && !categorias.isEmpty()) {
                for (CategoriaDTO categoria : categorias) {
                    tableModel.addRow(new Object[]{
                        categoria.getId(),
                        categoria.getNome(),
                        categoria.getNumeroTicket()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar categorias: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarNovaCategoria() {
        String nome = JOptionPane.showInputDialog(this, "Nome da categoria:", "Nova Categoria", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }

        String deptStr = JOptionPane.showInputDialog(this, "ID do Departamento:", "Nova Categoria", JOptionPane.PLAIN_MESSAGE);
        if (deptStr == null || deptStr.trim().isEmpty()) {
            return;
        }

        try {
            int deptId = Integer.parseInt(deptStr);
            if (categoriaBO.criarCategoria(nome, deptId)) {
                JOptionPane.showMessageDialog(this, "Categoria criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarCategorias();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar categoria!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de departamento inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
