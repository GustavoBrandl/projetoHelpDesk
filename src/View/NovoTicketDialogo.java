package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.CategoriaDTO;
import DTO.PrioridadeDTO;
import Controller.HelpDeskController;

public class NovoTicketDialogo extends JDialog {
    private JTextField campoSobre;
    private JTextArea campoDescricao;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboPrioridade;
    private JButton btnOK;
    private JButton btnCancelar;
    private boolean confirmado = false;
    private HelpDeskController controller;
    
    private List<CategoriaDTO> categorias;
    private List<PrioridadeDTO> prioridades;
    
    public NovoTicketDialogo(Frame parent, HelpDeskController controller) {
        super(parent, "Criar Novo Ticket", true);
        this.controller = controller;
        
        setLayout(new BorderLayout());
        setSize(650, 550);
        setLocationRelativeTo(parent);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(BorderFactory.createTitledBorder("Informações do Ticket"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel lblSobre = new JLabel("Assunto:");
        lblSobre.setFont(new Font("Arial", Font.BOLD, 12));
        panelCampos.add(lblSobre, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        campoSobre = new JTextField(35);
        campoSobre.setFont(new Font("Arial", Font.PLAIN, 12));
        panelCampos.add(campoSobre, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 12));
        panelCampos.add(lblCategoria, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        comboCategoria = new JComboBox<>();
        comboCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
        carregarCategorias();
        panelCampos.add(comboCategoria, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel lblPrioridade = new JLabel("Prioridade:");
        lblPrioridade.setFont(new Font("Arial", Font.BOLD, 12));
        panelCampos.add(lblPrioridade, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        comboPrioridade = new JComboBox<>();
        comboPrioridade.setFont(new Font("Arial", Font.PLAIN, 12));
        carregarPrioridades();
        panelCampos.add(comboPrioridade, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setFont(new Font("Arial", Font.BOLD, 12));
        panelCampos.add(lblDescricao, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        campoDescricao = new JTextArea(8, 35);
        campoDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(campoDescricao);
        scrollDescricao.setPreferredSize(new Dimension(400, 150));
        panelCampos.add(scrollDescricao, gbc);
        
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setBackground(new Color(220, 220, 220));
        
        btnOK = new JButton("Criar Ticket");
        btnOK.setFont(new Font("Arial", Font.BOLD, 12));
        btnOK.setBackground(new Color(76, 175, 80));
        btnOK.setForeground(Color.WHITE);
        
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    confirmado = true;
                    dispose();
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmado = false;
                dispose();
            }
        });
        
        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnOK);
        
        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblInfo = new JLabel("Preencha os campos obrigatórios para criar um novo ticket");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        panelInfo.add(lblInfo);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        
        add(panelInfo, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);
        
        campoSobre.addActionListener(e -> campoDescricao.requestFocus());
    }
    
    private void carregarCategorias() {
        try {
            var categoriaController = controller.getCategoriaController();
            categorias = categoriaController.listarCategorias();
            
            comboCategoria.removeAllItems();
            
            if (categorias != null && !categorias.isEmpty()) {
                for (CategoriaDTO cat : categorias) {
                    comboCategoria.addItem(cat.getNome());
                }
                comboCategoria.setEnabled(true);
            } else {
                comboCategoria.addItem("Nenhuma categoria disponível");
                comboCategoria.setEnabled(false);
            }
        } catch (Exception e) {
            comboCategoria.addItem("Erro ao carregar categorias");
            comboCategoria.setEnabled(false);
        }
    }
    
    private void carregarPrioridades() {
        try {
            var prioridadeController = controller.getPrioridadeController();
            prioridades = prioridadeController.listarPrioridades();
            
            comboPrioridade.removeAllItems();
            
            if (prioridades != null && !prioridades.isEmpty()) {
                PrioridadeDTO prioridadePadrao = null;
                for (PrioridadeDTO pri : prioridades) {
                    comboPrioridade.addItem(pri.getNome() + " (Valor: " + pri.getValor() + ")");
                    if (pri.isPadrao()) {
                        prioridadePadrao = pri;
                    }
                }
                
                if (prioridadePadrao != null) {
                    for (int i = 0; i < prioridades.size(); i++) {
                        if (prioridades.get(i).getId() == prioridadePadrao.getId()) {
                            comboPrioridade.setSelectedIndex(i);
                            break;
                        }
                    }
                }
                comboPrioridade.setEnabled(true);
            } else {
                comboPrioridade.addItem("Sem prioridades configuradas");
                comboPrioridade.setEnabled(false);
            }
        } catch (Exception e) {
            comboPrioridade.addItem("Erro ao carregar prioridades");
            comboPrioridade.setEnabled(false);
        }
    }
    
    private boolean validarCampos() {
        if (campoSobre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "O campo 'Assunto' é obrigatório.", 
                "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            campoSobre.requestFocus();
            return false;
        }
        
        if (campoDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "O campo 'Descrição' é obrigatório.", 
                "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            campoDescricao.requestFocus();
            return false;
        }
        
        if (comboCategoria.getSelectedIndex() < 0 || 
            comboCategoria.getSelectedItem().toString().contains("Nenhuma") ||
            comboCategoria.getSelectedItem().toString().contains("Erro")) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma categoria válida.", 
                "Categoria inválida", JOptionPane.WARNING_MESSAGE);
            comboCategoria.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public String getSobre() {
        return campoSobre.getText().trim();
    }
    
    public String getDescricao() {
        return campoDescricao.getText().trim();
    }
    
    public Integer getCategoriaId() {
        int selectedIndex = comboCategoria.getSelectedIndex();
        if (categorias != null && selectedIndex >= 0 && selectedIndex < categorias.size()) {
            return categorias.get(selectedIndex).getId();
        }
        return null;
    }
    
    public Integer getPrioridadeId() {
        int selectedIndex = comboPrioridade.getSelectedIndex();
        if (prioridades != null && selectedIndex >= 0 && selectedIndex < prioridades.size()) {
            return prioridades.get(selectedIndex).getId();
        }
        return 1;
    }
}