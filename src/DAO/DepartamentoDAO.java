package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.DepartamentoDTO;
import Conexao.Conexao;

public class DepartamentoDAO {

	final String NOMEDATABELA = "departamento";
	
	public boolean inserir(DepartamentoDTO departamento) {
		try {
			Connection conn = Conexao.conectar();
			String sql = "INSERT INTO " + NOMEDATABELA + " (nome) VALUES (?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString (1, departamento.getNome());
			ps.executeUpdate();
			ps.close();
			conn.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("ERRO COMEÇOU AQUI DAO");
            return false;
		}
	}
    public boolean alterar(DepartamentoDTO departamento) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET nome = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, departamento.getNome());
            ps.setInt(2, departamento.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
        	 e.printStackTrace();
             return false;
        }
    }
    public boolean excluir(DepartamentoDTO departamento) {
    	try{
    		Connection conn = Conexao.conectar();
    		String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, departamento.getId());
    		ps.executeUpdate();
    		ps.close();
    		conn.close();
    		return true;
    	}catch(Exception e) {
    		return false;
    	}
    }
    public boolean existe(DepartamentoDTO departamento) {
    	try {
    		Connection conn = Conexao.conectar();
    		String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, departamento.getNome());
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
    public List<DepartamentoDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<DepartamentoDTO> listObj = montarLista(rs);
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<DepartamentoDTO> montarLista(ResultSet rs) {
        List<DepartamentoDTO> listObj = new ArrayList<DepartamentoDTO>();
        try {
            while (rs.next()) {
                DepartamentoDTO obj = new DepartamentoDTO();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
