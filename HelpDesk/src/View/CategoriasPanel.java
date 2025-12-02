package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.CategoriaDTO;
import DTO.DepartamentoDTO;
import Controller.HelpDeskController;
import Util.PermissaoUtil;

public class CategoriasPanel extends JPanel {
    private JTable categoriasTable;
    private DefaultTableModel tableModel;
    private JButton novaButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;
    private JComboBox<DepartamentoDTO> comboDepartamento;

    public CategoriasPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("GERENCIAR CATEGORIAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Total Tickets", "Departamento"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        categoriasTable = new JTable(tableModel);
        categoriasTable.setFont(new Font("Arial", Font.PLAIN, 11));
        categoriasTable.setRowHeight(25);
        categoriasTable.getSelectionModel().addListSelectionListener(e -> atualizarBotoes());
        JScrollPane scrollPane = new JScrollPane(categoriasTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novaButton = new JButton("NOVA CATEGORIA");
        novaButton.setFont(new Font("Arial", Font.BOLD, 12));
        novaButton.setBackground(new Color(76, 175, 80));
        novaButton.setForeground(Color.WHITE);
        novaButton.setFocusPainted(false);
        bottomPanel.add(novaButton);

        editarButton = new JButton("EDITAR");
        editarButton.setFont(new Font("Arial", Font.BOLD, 12));
        editarButton.setBackground(new Color(33, 150, 243));
        editarButton.setForeground(Color.WHITE);
        editarButton.setFocusPainted(false);
        bottomPanel.add(editarButton);

        excluirButton = new JButton("EXCLUIR");
        excluirButton.setFont(new Font("Arial", Font.BOLD, 12));
        excluirButton.setBackground(new Color(244, 67, 54));
        excluirButton.setForeground(Color.WHITE);
        excluirButton.setFocusPainted(false);
        bottomPanel.add(excluirButton);

        voltarButton = new JButton("VOLTAR");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 12));
        voltarButton.setBackground(new Color(158, 158, 158));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFocusPainted(false);
        bottomPanel.add(voltarButton);

        add(bottomPanel, BorderLayout.SOUTH);

        novaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovaCategoria();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCategoriaSelecionada();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCategoriaSelecionada();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
            }
        });

        carregarCategorias();
        atualizarBotoes();
    }

    private void carregarCategorias() {
        tableModel.setRowCount(0);
        try {
            var controller = mainFrame.getController();
            var usuarioLogado = controller.getUsuarioLogado();
            
            if (usuarioLogado == null || !PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
                tableModel.addRow(new Object[]{"", "Acesso não autorizado", "", ""});
                return;
            }
            
            var categoriaController = controller.getCategoriaController();
            List<CategoriaDTO> categorias = categoriaController.pesquisarTodos();
            
            if (categorias != null && !categorias.isEmpty()) {
                for (CategoriaDTO categoria : categorias) {
                    String departamentoNome = categoria.getDepartamento() != null ? 
                        categoria.getDepartamento().getNome() : "N/A";
                    
                    tableModel.addRow(new Object[]{
                        categoria.getId(),
                        categoria.getNome(),
                        categoria.getNumeroTicket(),
                        departamentoNome
                    });
                }
            } else {
                tableModel.addRow(new Object[]{"", "Nenhuma categoria encontrada", "", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar categorias: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        boolean podeGerenciar = PermissaoUtil.podeGerenciarSistema(usuarioLogado);
        int linhaSelecionada = categoriasTable.getSelectedRow();
        
        novaButton.setEnabled(podeGerenciar);
        editarButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
        excluirButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
    }

    private Integer getCategoriaIdSelecionada() {
        int linha = categoriasTable.getSelectedRow();
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

    private void criarNovaCategoria() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para criar categorias.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nomeField = new JTextField();
        comboDepartamento = new JComboBox<>();
        
        var departamentoController = controller.getDepartamentoController();
        List<DepartamentoDTO> departamentos = departamentoController.listarDepartamentos();
        
        if (departamentos != null && !departamentos.isEmpty()) {
            for (DepartamentoDTO dept : departamentos) {
                comboDepartamento.addItem(dept);
            }
        } else {
            comboDepartamento.addItem(new DepartamentoDTO(0, "Nenhum departamento"));
        }
        
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Departamento:"));
        panel.add(comboDepartamento);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Nova Categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            DepartamentoDTO departamento = (DepartamentoDTO) comboDepartamento.getSelectedItem();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Informe o nome da categoria.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (departamento == null || departamento.getId() == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um departamento válido.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                var categoriaController = controller.getCategoriaController();
                boolean sucesso = categoriaController.inserir(nome, departamento.getId());
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Categoria criada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarCategorias();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao criar categoria.", 
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

    private void editarCategoriaSelecionada() {
        Integer categoriaId = getCategoriaIdSelecionada();
        if (categoriaId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma categoria para editar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para editar categorias.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int linha = categoriasTable.getSelectedRow();
        String nomeAtual = (String) tableModel.getValueAt(linha, 1);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nomeField = new JTextField(nomeAtual);
        comboDepartamento = new JComboBox<>();
        
        var departamentoController = controller.getDepartamentoController();
        List<DepartamentoDTO> departamentos = departamentoController.listarDepartamentos();
        
        if (departamentos != null && !departamentos.isEmpty()) {
            for (DepartamentoDTO dept : departamentos) {
                comboDepartamento.addItem(dept);
            }
        }
        
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Departamento:"));
        panel.add(comboDepartamento);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Editar Categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            DepartamentoDTO departamento = (DepartamentoDTO) comboDepartamento.getSelectedItem();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Informe o nome da categoria.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (departamento == null || departamento.getId() == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um departamento válido.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                var categoriaController = controller.getCategoriaController();
                boolean sucesso = categoriaController.alterar(categoriaId, nome, departamento.getId());
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Categoria alterada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarCategorias();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao editar categoria.", 
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

    private void excluirCategoriaSelecionada() {
        Integer categoriaId = getCategoriaIdSelecionada();
        if (categoriaId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma categoria para excluir.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para excluir categorias.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int linha = categoriasTable.getSelectedRow();
        String nomeSelecionado = (String) tableModel.getValueAt(linha, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir a categoria '" + nomeSelecionado + "'?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var categoriaController = controller.getCategoriaController();
                List<CategoriaDTO> categorias = categoriaController.listarCategorias();
                CategoriaDTO categoriaExcluir = null;
                
                for (CategoriaDTO cat : categorias) {
                    if (cat.getId() == categoriaId) {
                        categoriaExcluir = cat;
                        break;
                    }
                }
                
                if (categoriaExcluir != null) {
                    boolean sucesso = categoriaController.excluirCategoria(categoriaExcluir);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, 
                            "Categoria excluída com sucesso!", 
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarCategorias();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Erro ao excluir categoria.", 
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Categoria não encontrada.", 
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
        carregarCategorias();
        atualizarBotoes();
    }
}