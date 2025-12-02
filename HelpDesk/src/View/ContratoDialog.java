package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.YearMonth;

import DAO.ContratoDAO;
import DTO.ContratoDTO;
import DTO.OrganizacaoDTO;

public class ContratoDialog extends JDialog {
    private OrganizacaoDTO organizacao;
    private ContratoDAO contratoDAO = new ContratoDAO();

    private JTextField horasField;
    private JTextField precoField;
    private JTextField extraField;
    private JComboBox<String> monthCombo;
    private JComboBox<Integer> yearCombo;
    private JCheckBox ativoCheck;

    private ContratoDTO contratoAtual;

    public ContratoDialog(Window owner, OrganizacaoDTO organizacao) {
        super(owner, "Contrato - " + (organizacao != null ? organizacao.getNome() : ""), ModalityType.APPLICATION_MODAL);
        this.organizacao = organizacao;
        setSize(420, 300);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Horas contratadas:"), c);
        c.gridx = 1; horasField = new JTextField(10); form.add(horasField, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Valor do contrato (R$):"), c);
        c.gridx = 1; precoField = new JTextField(10); form.add(precoField, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Valor hora extra (R$):"), c);
        c.gridx = 1; extraField = new JTextField(10); form.add(extraField, c);

        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Mês de vigência:"), c);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        monthCombo = new JComboBox<>(); for (int m=1; m<=12; m++) monthCombo.addItem(String.format("%02d", m));
        int yearNow = YearMonth.now().getYear(); yearCombo = new JComboBox<>(); for (int y = yearNow; y >= yearNow-5; y--) yearCombo.addItem(y);
        datePanel.add(monthCombo); datePanel.add(yearCombo);
        c.gridx = 1; form.add(datePanel, c);

        c.gridx = 0; c.gridy = 4; form.add(new JLabel("Ativo:"), c);
        c.gridx = 1; ativoCheck = new JCheckBox(); ativoCheck.setSelected(true); form.add(ativoCheck, c);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton salvar = new JButton("Salvar");
        JButton cancelar = new JButton("Cancelar");
        buttons.add(salvar); buttons.add(cancelar);
        add(buttons, BorderLayout.SOUTH);

        salvar.addActionListener((ActionEvent e) -> salvarContrato());
        cancelar.addActionListener((ActionEvent e) -> dispose());

        carregarContratoExistente();
    }

    private void carregarContratoExistente() {
        try {
            YearMonth ym = YearMonth.now();
            contratoAtual = contratoDAO.pesquisarAtivoOrganizacao(organizacao.getId(), ym);
            if (contratoAtual != null) {
                horasField.setText(String.valueOf(contratoAtual.getHorasContrato()));
                precoField.setText(String.format("%.2f", contratoAtual.getPrecoContrato()));
                extraField.setText(String.format("%.2f", contratoAtual.getPrecoExtra()));
                YearMonth dv = contratoAtual.getDataVigencia();
                monthCombo.setSelectedItem(String.format("%02d", dv.getMonthValue()));
                yearCombo.setSelectedItem(dv.getYear());
                ativoCheck.setSelected(contratoAtual.getAtivo() != null ? contratoAtual.getAtivo() : true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarContrato() {
        try {
            String htxt = horasField.getText().trim();
            String ptxt = precoField.getText().trim();
            String etxt = extraField.getText().trim();
            if (htxt.isEmpty() || ptxt.isEmpty() || etxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int horas = Integer.parseInt(htxt);
            double preco = Double.parseDouble(ptxt.replace(',', '.'));
            double extra = Double.parseDouble(etxt.replace(',', '.'));
            int month = Integer.parseInt((String) monthCombo.getSelectedItem());
            int year = (Integer) yearCombo.getSelectedItem();
            YearMonth dv = YearMonth.of(year, month);
            boolean ativo = ativoCheck.isSelected();

            ContratoDTO dto = new ContratoDTO();
            if (contratoAtual != null && contratoAtual.getId() != null) dto.setId(contratoAtual.getId());
            dto.setOrganizacaoId(organizacao.getId());
            dto.setHorasContrato(horas);
            dto.setPrecoContrato(preco);
            dto.setPrecoExtra(extra);
            dto.setDataVigencia(dv);
            dto.setAtivo(ativo);

            boolean ok;
            if (contratoAtual != null && contratoAtual.getId() != null) {
                ok = contratoDAO.alterarContrato(dto);
            } else {
                ok = contratoDAO.inserir(dto);
            }

            if (ok) {
                JOptionPane.showMessageDialog(this, "Contrato salvo com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar contrato.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
