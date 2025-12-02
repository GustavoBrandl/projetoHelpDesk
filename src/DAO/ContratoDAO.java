package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            String sql = "INSERT INTO " + NOMEDATABELA + " (horasContrato, precoContrato, precoExtra, fk_organizacao_contrato) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, contrato.getHorasContrato());
            ps.setDouble(2, contrato.getPrecoContrato());
            ps.setDouble(3, contrato.getPrecoExtra());
            ps.setInt(4, contrato.getOrganizacao().getId());
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

    public List<ContratoDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT c.id, c.horasContrato, c.precoContrato, c.precoExtra, o.id, o.nome " +
                         "FROM " + NOMEDATABELA + " c " +
                         "INNER JOIN organizacao o ON c.fk_organizacao_contrato = o.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<ContratoDTO> lista = montarLista(rs);
            rs.close();
            ps.close();
            conn.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ContratoDTO> montarLista(ResultSet rs) {
        List<ContratoDTO> listObj = new ArrayList<>();
        try {
            while (rs.next()) {
                OrganizacaoDTO org = new OrganizacaoDTO();
                org.setId(rs.getInt(5));
                org.setNome(rs.getString(6));

                ContratoDTO cont = new ContratoDTO();
                cont.setId(rs.getInt(1));
                cont.setHorasContrato(rs.getDouble(2));
                cont.setPrecoContrato(rs.getDouble(3));
                cont.setPrecoExtra(rs.getDouble(4));
                cont.setOrganizacao(org);
                
                listObj.add(cont);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean alterarContrato(ContratoDTO contrato) {
        try {
            Connection conn = Conexao.conectar();

            StringBuilder sql = new StringBuilder("UPDATE " + NOMEDATABELA + " SET ");
            List<Object> parametros = new ArrayList<>();

            if (contrato.getHorasContrato() != null) {
                sql.append("horasContrato = ?, ");
                parametros.add(contrato.getHorasContrato());
            }
            
            if (contrato.getPrecoContrato() != null) {
            	sql.append("precoContrato = ?, ");
            	parametros.add(contrato.getPrecoContrato());
            }

            if (contrato.getPrecoExtra() != null) {
            	sql.append("precoExtra = ?, ");
            	parametros.add(contrato.getPrecoExtra());
            }
            
            if (contrato.getOrganizacao() != null) {
                sql.append("fk_organizacao_contrato = ?, ");
                parametros.add(contrato.getOrganizacao().getId());
            }
            
            if(parametros.isEmpty()) {
            	return false;
            }

            sql.delete(sql.length() - 2, sql.length());
            sql.append(" WHERE id = ?");
            parametros.add(contrato.getId());

            PreparedStatement ps = conn.prepareStatement(sql.toString());

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
