package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.PrioridadeDTO;
import Conexao.Conexao;

public class PrioridadeDAO {

	final String NOMEDATABELA = "prioridade";
	
	public boolean inserir(PrioridadeDTO prioridade) {
	    try {
	        Connection conn = Conexao.conectar();
	        String sql = "INSERT INTO " + NOMEDATABELA + " (nome, valor, padrao) VALUES (?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, prioridade.getNome());
	        ps.setInt(2, prioridade.getValor());
	        ps.setBoolean(3, prioridade.isPadrao());
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
    public boolean alterar(PrioridadeDTO prioridade) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET nome = ? valor = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, prioridade.getNome());
            ps.setInt(2, prioridade.getValor());
            ps.setInt(3, prioridade.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
        	 e.printStackTrace();
             return false;
        }
    }
    public boolean excluir(PrioridadeDTO prioridade) {
    	try{
    		Connection conn = Conexao.conectar();
    		String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, prioridade.getId());
    		ps.executeUpdate();
    		ps.close();
    		conn.close();
    		return true;
    	}catch(Exception e) {
    		return false;
    	}
    }
    public boolean existe(PrioridadeDTO prioridade) {
    	try {
    		Connection conn = Conexao.conectar();
    		String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, prioridade.getNome());
    		ResultSet rs = ps.executeQuery();
    		boolean existe = rs.next();
    		ps.close();
    		rs.close();
    		conn.close();
   			return existe;
    	}catch(Exception e){
    		return false;
    	}
    }
    public List<PrioridadeDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<PrioridadeDTO> listObj = montarLista(rs);
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<PrioridadeDTO> montarLista(ResultSet rs) {
        List<PrioridadeDTO> listObj = new ArrayList<PrioridadeDTO>();
        try {
            while (rs.next()) {
                PrioridadeDTO obj = new PrioridadeDTO();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setValor(rs.getInt(3));
                obj.setPadrao(rs.getBoolean(4));
                obj.setNumeroTickets(rs.getInt(5));
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean alterarPrioridade(PrioridadeDTO prioridade) {
        try {
            Connection conn = Conexao.conectar();

            StringBuilder sql = new StringBuilder("UPDATE " + NOMEDATABELA + " SET ");
            List<Object> parametros = new ArrayList<>();

            if (prioridade.getNome() != null) {
                sql.append("nome = ?, ");
                parametros.add(prioridade.getNome());
            }

            if (prioridade.getValor() != null) {
            	sql.append("valor = ?, ");
            	parametros.add(prioridade.getValor());
            }
            
            if(parametros.isEmpty()) {
            	return false;
            }
            
            sql.delete(sql.length() - 2, sql.length());
            
            sql.append(" WHERE id = ?");
            parametros.add(prioridade.getId());

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
    public boolean tornarPrioridadePadrao(int idPrioridade) {
        try {
            Connection conn = Conexao.conectar();

            String sql = "UPDATE " + NOMEDATABELA + " SET padrao = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

            String sqll = "UPDATE " + NOMEDATABELA + " SET padrao = true WHERE id = ?";
            PreparedStatement pss = conn.prepareStatement(sqll);
            pss.setInt(1, idPrioridade);
            pss.executeUpdate();
            pss.close();

            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean incrementarNumeroTickets(Integer idCategoria) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE categoria SET numeroTickets = numeroTickets + 1 WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idCategoria);
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
