package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Huespedes;

public class HuespedDAO {

	Connection con;

	public HuespedDAO(Connection con) {
		super();
		this.con = con;
	}
	
	public void guardar(Huespedes huespedes) {
		try {
			String sql = "INSERT INTO huesped (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva) VALUES (?,?,?,?,?,?)";
			try(PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				pstm.setString(1, huespedes.getNombre());
				pstm.setString(2, huespedes.getApellido());
				pstm.setObject(3, huespedes.getFechaNacimiento());
				pstm.setString(4, huespedes.getNacionalidad());
				pstm.setString(5, huespedes.getTelefono());
				pstm.setInt(6, huespedes.getIdReserva());
				pstm.execute();
				try(ResultSet rst = pstm.getGeneratedKeys()){
					while(rst.next())
					{
						huespedes.setId(rst.getInt(1));
						}
					}
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public List<Huespedes> mostrar(){
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huesped";
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.execute();
				transformarResultado(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public List<Huespedes> buscarId(String id){
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huesped WHERE id=?";
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.setString(1, id);
				pstm.execute();
				transformarResultado(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void actualizarH(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		try(PreparedStatement stm = con.prepareStatement("UPDATE huesped SET nombre=?, apellido=?, fecha_nacimiento=?, nacionalidad=?, telefono=?, id_reserva=? WHERE id=?")){
			stm.setString(1, nombre);
			stm.setString(2, apellido);
			stm.setObject(3, fechaNacimiento);
			stm.setString(4, nacionalidad);
			stm.setString(5, telefono);
			stm.setInt(6, idReserva);
			stm.setInt(7, id);
			stm.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("------");
			throw new RuntimeException();
		}
	}
	
	public void eliminar(Integer id) {
		try(PreparedStatement stm = con.prepareStatement("DELETE FROM huesped WHERE id=?")){
			stm.setInt(1, id);
			stm.execute();
		}catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void transformarResultado(List<Huespedes> huespedes, PreparedStatement pstm) throws SQLException {
		try(ResultSet rst = pstm.executeQuery()){
			while(rst.next()) {
				int id= rst.getInt("id");
				String nombre = rst.getString("nombre");
				String apellido = rst.getString("apellido");
				LocalDate fechaN = rst.getDate("fecha_nacimiento").toLocalDate();
				String nacionalidad = rst.getString("nacionalidad");
				String telefono = rst.getString("telefono");
				int idR= rst.getInt("id_reserva");
				
				Huespedes huesped = new Huespedes(id, nombre, apellido, fechaN, nacionalidad, telefono, idR);
				huespedes.add(huesped);
			}
		}
	}
	
}
