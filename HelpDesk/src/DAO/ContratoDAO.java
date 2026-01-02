package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import DTO.ContratoDTO;
import DTO.OrganizacaoDTO;
import Conexao.Conexao;

public class ContratoDAO {

    final String NOMEDATABELA = "contrato";

    public boolean inserir(ContratoDTO contrato) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (organizacao_id, horas_contrato, preco_contrato, preco_extra, data_vigencia, ativo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, contrato.getOrganizacaoId());
            ps.setInt(2, contrato.getHorasContrato());
            ps.setDouble(3, contrato.getPrecoContrato());
            ps.setDouble(4, contrato.getPrecoExtra());
            ps.setString(5, contrato.getDataVigencia().toString());
            ps.setBoolean(6, contrato.getAtivo() != null ? contrato.getAtivo() : true);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("ERRO NO INSERT DAO");
            return false;
        }
    }

    public boolean excluir(ContratoDTO contrato) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, contrato.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ContratoDTO pesquisarPorId(Integer id) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ContratoDTO contrato = null;
            if (rs.next()) {
                contrato = montarObjeto(rs);
            }
            rs.close();
            ps.close();
            conn.close();
            return contrato;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ContratoDTO> pesquisarPorOrganizacao(Integer organizacaoId) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE organizacao_id = ? ORDER BY data_vigencia DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizacaoId);
            ResultSet rs = ps.executeQuery();
            List<ContratoDTO> lista = montarLista(rs);
            rs.close();
            ps.close();
            conn.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ContratoDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " ORDER BY data_vigencia DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<ContratoDTO> lista = montarLista(rs);
            rs.close();
            ps.close();
            conn.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ContratoDTO> pesquisarAtivos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE ativo = true ORDER BY data_vigencia DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<ContratoDTO> lista = montarLista(rs);
            rs.close();
            ps.close();
            conn.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ContratoDTO pesquisarAtivoOrganizacao(Integer organizacaoId, YearMonth mes) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE organizacao_id = ? AND ativo = true AND data_vigencia = ? ORDER BY id DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizacaoId);
            ps.setString(2, mes.toString());
            ResultSet rs = ps.executeQuery();
            ContratoDTO contrato = null;
            if (rs.next()) {
                contrato = montarObjeto(rs);
            }
            rs.close();
            ps.close();
            conn.close();
            return contrato;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ContratoDTO montarObjeto(ResultSet rs) {
        try {
            ContratoDTO contrato = new ContratoDTO();
            contrato.setId(rs.getInt("id"));
            contrato.setOrganizacaoId(rs.getInt("organizacao_id"));
            contrato.setHorasContrato(rs.getInt("horas_contrato"));
            contrato.setPrecoContrato(rs.getDouble("preco_contrato"));
            contrato.setPrecoExtra(rs.getDouble("preco_extra"));
            contrato.setDataVigencia(YearMonth.parse(rs.getString("data_vigencia")));
            contrato.setAtivo(rs.getBoolean("ativo"));
            return contrato;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean alterarContrato(ContratoDTO contrato) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET horas_contrato = ?, preco_contrato = ?, preco_extra = ?, data_vigencia = ?, ativo = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, contrato.getHorasContrato());
            ps.setDouble(2, contrato.getPrecoContrato());
            ps.setDouble(3, contrato.getPrecoExtra());
            ps.setString(4, contrato.getDataVigencia().toString());
            ps.setBoolean(5, contrato.getAtivo());
            ps.setInt(6, contrato.getId());

            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<ContratoDTO> montarLista(ResultSet rs) {
        List<ContratoDTO> listObj = new ArrayList<>();
        try {
            while (rs.next()) {
                ContratoDTO cont = montarObjeto(rs);
                if (cont != null) {
                    listObj.add(cont);
                }
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}


