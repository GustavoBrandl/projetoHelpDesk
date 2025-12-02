package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.PrioridadeDTO;
import Controller.HelpDeskController;
import Util.PermissaoUtil;

public class PrioridadesPanel extends JPanel {
    private JTable prioridadesTable;
    private DefaultTableModel tableModel;
    private JButton novaButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton padraoButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;

    public PrioridadesPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("GERENCIAR PRIORIDADES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Valor", "Padrão", "Total Tickets"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        prioridadesTable = new JTable(tableModel);
        prioridadesTable.setFont(new Font("Arial", Font.PLAIN, 11));
        prioridadesTable.setRowHeight(25);
        prioridadesTable.getSelectionModel().addListSelectionListener(e -> atualizarBotoes());
        JScrollPane scrollPane = new JScrollPane(prioridadesTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novaButton = new JButton("NOVA PRIORIDADE");
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

        padraoButton = new JButton("DEFINIR PADRÃO");
        padraoButton.setFont(new Font("Arial", Font.BOLD, 12));
        padraoButton.setBackground(new Color(255, 152, 0));
        padraoButton.setForeground(Color.WHITE);
        padraoButton.setFocusPainted(false);
        bottomPanel.add(padraoButton);

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
                criarNovaPrioridade();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarPrioridadeSelecionada();
            }
        });

        padraoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                definirPrioridadePadrao();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirPrioridadeSelecionada();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
            }
        });

        carregarPrioridades();
        atualizarBotoes();
    }

    private void carregarPrioridades() {
        tableModel.setRowCount(0);
        try {
            var controller = mainFrame.getController();
            var usuarioLogado = controller.getUsuarioLogado();
            
            if (usuarioLogado == null || !PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
                tableModel.addRow(new Object[]{"", "Acesso não autorizado", "", "", ""});
                return;
            }
            
            var prioridadeController = controller.getPrioridadeController();
            List<PrioridadeDTO> prioridades = prioridadeController.listarPrioridades();
            
            if (prioridades != null && !prioridades.isEmpty()) {
                for (PrioridadeDTO prioridade : prioridades) {
                    tableModel.addRow(new Object[]{
                        prioridade.getId(),
                        prioridade.getNome(),
                        prioridade.getValor(),
                        prioridade.isPadrao() ? "Sim" : "Não",
                        prioridade.getNumeroTickets()
                    });
                }
            } else {
                tableModel.addRow(new Object[]{"", "Nenhuma prioridade encontrada", "", "", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar prioridades: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        boolean podeGerenciar = PermissaoUtil.podeGerenciarSistema(usuarioLogado);
        int linhaSelecionada = prioridadesTable.getSelectedRow();
        
        novaButton.setEnabled(podeGerenciar);
        editarButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
        padraoButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
        excluirButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
    }

    private Integer getPrioridadeIdSelecionada() {
        int linha = prioridadesTable.getSelectedRow();
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

    private void criarNovaPrioridade() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para criar prioridades.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nomeField = new JTextField();
        JSpinner valorSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        JCheckBox padraoCheck = new JCheckBox("Definir como padrão");
        
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Valor (1-10):"));
        panel.add(valorSpinner);
        panel.add(new JLabel(""));
        panel.add(padraoCheck);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Nova Prioridade", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            Integer valor = (Integer) valorSpinner.getValue();
            boolean padrao = padraoCheck.isSelected();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Informe o nome da prioridade.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                var prioridadeController = controller.getPrioridadeController();
                boolean sucesso = prioridadeController.criarPrioridade(nome, valor, padrao);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Prioridade criada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarPrioridades();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao criar prioridade.", 
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

    private void editarPrioridadeSelecionada() {
        Integer prioridadeId = getPrioridadeIdSelecionada();
        if (prioridadeId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma prioridade para editar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para editar prioridades.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int linha = prioridadesTable.getSelectedRow();
        String nomeAtual = (String) tableModel.getValueAt(linha, 1);
        int valorAtual = (Integer) tableModel.getValueAt(linha, 2);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nomeField = new JTextField(nomeAtual);
        JSpinner valorSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(valorAtual, 1, 10, 1));
        
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Valor (1-10):"));
        panel.add(valorSpinner);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Editar Prioridade", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            Integer valor = (Integer) valorSpinner.getValue();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Informe o nome da prioridade.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                var prioridadeController = controller.getPrioridadeController();
                boolean sucesso = prioridadeController.alterarPrioridade(prioridadeId, nome, valor);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Prioridade alterada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarPrioridades();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao editar prioridade.", 
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

    private void definirPrioridadePadrao() {
        Integer prioridadeId = getPrioridadeIdSelecionada();
        if (prioridadeId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma prioridade para definir como padrão.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para definir prioridades padrão.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Definir esta prioridade como padrão para novos tickets?",
            "Confirmar Prioridade Padrão",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var prioridadeController = controller.getPrioridadeController();
                boolean sucesso = prioridadeController.tornarPadrao(prioridadeId);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Prioridade definida como padrão com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarPrioridades();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao definir prioridade padrão.", 
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

    private void excluirPrioridadeSelecionada() {
        Integer prioridadeId = getPrioridadeIdSelecionada();
        if (prioridadeId == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma prioridade para excluir.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarSistema(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para excluir prioridades.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int linha = prioridadesTable.getSelectedRow();
        String nomeSelecionado = (String) tableModel.getValueAt(linha, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir a prioridade '" + nomeSelecionado + "'?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var prioridadeController = controller.getPrioridadeController();
                List<PrioridadeDTO> prioridades = prioridadeController.listarPrioridades();
                PrioridadeDTO prioridadeExcluir = null;
                
                for (PrioridadeDTO p : prioridades) {
                    if (p.getId() == prioridadeId) {
                        prioridadeExcluir = p;
                        break;
                    }
                }
                
                if (prioridadeExcluir != null) {
                    boolean sucesso = prioridadeController.excluirPrioridade(prioridadeExcluir);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, 
                            "Prioridade excluída com sucesso!", 
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarPrioridades();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Erro ao excluir prioridade.", 
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Prioridade não encontrada.", 
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
        carregarPrioridades();
        atualizarBotoes();
    }
}