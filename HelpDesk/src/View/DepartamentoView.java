package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.DepartamentoDTO;
import Controller.HelpDeskController;
import Util.PermissaoUtil;

public class DepartamentoView extends JPanel {
    private JTable departamentosTable;
    private DefaultTableModel tableModel;
    private JButton novoButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;

    public DepartamentoView(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("GERENCIAR DEPARTAMENTOS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        departamentosTable = new JTable(tableModel);
        departamentosTable.setFont(new Font("Arial", Font.PLAIN, 11));
        departamentosTable.setRowHeight(25);
        departamentosTable.getSelectionModel().addListSelectionListener(e -> atualizarBotoes());
        JScrollPane scrollPane = new JScrollPane(departamentosTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novoButton = new JButton("NOVO DEPARTAMENTO");
        novoButton.setFont(new Font("Arial", Font.BOLD, 12));
        novoButton.setBackground(new Color(76, 175, 80));
        novoButton.setForeground(Color.WHITE);
        novoButton.setFocusPainted(false);
        bottomPanel.add(novoButton);

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

        novoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovoDepartamento();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDepartamentoSelecionado();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirDepartamentoSelecionado();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
            }
        });

        carregarDepartamentos();
        atualizarBotoes();
    }

    private void carregarDepartamentos() {
        tableModel.setRowCount(0);
        try {
            var controller = mainFrame.getController();
            var usuarioLogado = controller.getUsuarioLogado();
            
            if (usuarioLogado == null || !PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
                tableModel.addRow(new Object[]{"", "Acesso não autorizado"});
                return;
            }
            
            var departamentoController = controller.getDepartamentoController();
            List<DepartamentoDTO> departamentos = departamentoController.pesquisarTodos();
            
            if (departamentos != null && !departamentos.isEmpty()) {
                for (DepartamentoDTO departamento : departamentos) {
                    tableModel.addRow(new Object[]{
                        departamento.getId(),
                        departamento.getNome()
                    });
                }
            } else {
                tableModel.addRow(new Object[]{"", "Nenhum departamento encontrado"});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar departamentos: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        boolean podeGerenciar = PermissaoUtil.podeGerenciarSistema(usuarioLogado);
        int linhaSelecionada = departamentosTable.getSelectedRow();
        
        novoButton.setEnabled(podeGerenciar);
        editarButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
        excluirButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
    }

    private Integer getDepartamentoIdSelecionado() {
        int linha = departamentosTable.getSelectedRow();
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

    private String getDepartamentoNomeSelecionado() {
        int linha = departamentosTable.getSelectedRow();
        if (linha >= 0) {
            return (String) tableModel.getValueAt(linha, 1);
        }
        return null;
    }

    private void criarNovoDepartamento() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para criar departamentos.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nome = JOptionPane.showInputDialog(this, 
            "Nome do departamento:", "Novo Departamento", JOptionPane.PLAIN_MESSAGE);
        
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }
        
        nome = nome.trim();
        
        try {
            var departamentoController = controller.getDepartamentoController();
            boolean sucesso = departamentoController.inserir(nome);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Departamento criado com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDepartamentos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao criar departamento.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void editarDepartamentoSelecionado() {
        Integer departamentoId = getDepartamentoIdSelecionado();
        String nomeAtual = getDepartamentoNomeSelecionado();
        
        if (departamentoId == null || nomeAtual == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um departamento para editar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para editar departamentos.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String novoNome = JOptionPane.showInputDialog(this, 
            "Novo nome do departamento:", "Editar Departamento", 
            JOptionPane.PLAIN_MESSAGE, null, null, nomeAtual).toString();
        
        if (novoNome == null || novoNome.trim().isEmpty() || novoNome.equals(nomeAtual)) {
            return;
        }
        
        novoNome = novoNome.trim();
        
        try {
            var departamentoController = controller.getDepartamentoController();
            boolean sucesso = departamentoController.alterar(departamentoId, novoNome);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Departamento atualizado com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDepartamentos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao atualizar departamento.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void excluirDepartamentoSelecionado() {
        Integer departamentoId = getDepartamentoIdSelecionado();
        String nomeDepartamento = getDepartamentoNomeSelecionado();
        
        if (departamentoId == null || nomeDepartamento == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um departamento para excluir.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para excluir departamentos.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir o departamento:\n\"" + nomeDepartamento + "\"?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var departamentoController = controller.getDepartamentoController();
                DepartamentoDTO departamento = new DepartamentoDTO();
                departamento.setId(departamentoId);
                departamento.setNome(nomeDepartamento);
                
                boolean sucesso = departamentoController.excluir(departamento);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Departamento excluído com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarDepartamentos();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir departamento. Verifique se não há categorias vinculadas.", 
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
        carregarDepartamentos();
        atualizarBotoes();
    }
}