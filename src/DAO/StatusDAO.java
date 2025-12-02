package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.StatusDTO;
import Conexao.Conexao;

public class StatusDAO {

	final String NOMEDATABELA = "status";
	
	public boolean inserir(StatusDTO status) {
	    try {
	        Connection conn = Conexao.conectar();
	        String sql = "INSERT INTO " + NOMEDATABELA + " (nome) VALUES (?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, status.getNome());
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
    public boolean alterar(StatusDTO status) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET nome = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status.getNome());
            ps.setInt(2, status.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
        	 e.printStackTrace();
             return false;
        }
    }
    public boolean excluir(StatusDTO status) {
    	try{
    		Connection conn = Conexao.conectar();
    		String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, status.getId());
    		ps.executeUpdate();
    		ps.close();
    		conn.close();
    		return true;
    	}catch(Exception e) {
    		return false;
    	}
    }
    public boolean existe(StatusDTO status) {
    	try {
    		Connection conn = Conexao.conectar();
    		String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, status.getNome());
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
    public List<StatusDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<StatusDTO> listObj = montarLista(rs);
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<StatusDTO> montarLista(ResultSet rs) {
        List<StatusDTO> listObj = new ArrayList<StatusDTO>();
        try {
            while (rs.next()) {
                StatusDTO obj = new StatusDTO();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setPadrao(rs.getBoolean(3));
                obj.setNumeroTicket(rs.getInt(4));
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean tornarStatusPadrao(int id) {
        try {
            Connection conn = Conexao.conectar();

            String sql = "UPDATE "+ NOMEDATABELA +" SET padrao = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

            String sqll = "UPDATE "+ NOMEDATABELA +" SET padrao = true WHERE id = ?";
            PreparedStatement pss = conn.prepareStatement(sqll);
            pss.setInt(1, id);
            pss.executeUpdate();
            pss.close();

            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
