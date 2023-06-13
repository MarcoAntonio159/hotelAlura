package DAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Reservas;

public class ReservaDAO {

	private Connection con;

	public ReservaDAO(Connection con) {
		super();
		this.con = con;
	}
	
	public void guardar(Reservas reservas) {
		try {
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_pago) VALUES (?,?,?,?)";
			try(PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				pstm.setObject(1, reservas.getFechaE());
				pstm.setObject(2, reservas.getFechaS());
				pstm.setObject(3, reservas.getValor());
				pstm.setObject(4, reservas.getFormaPago());
				pstm.executeUpdate();
				
				try(ResultSet rst = pstm.getGeneratedKeys()){
					while(rst.next()) {
						reservas.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Reservas> mostrar(){
		List<Reservas> reservas = new ArrayList<Reservas>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas";
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.execute();
				transformarResultado(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public List<Reservas> buscarId(String Id){
		List<Reservas> reservas = new ArrayList<Reservas>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas WHERE id=?";
			try(PreparedStatement pstm = con.prepareStatement(sql)){
				pstm.setString(1, Id);
				pstm.execute();
				transformarResultado(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void actualizar(LocalDate fechaE, LocalDate fechaS, String valor, String formaPago, Integer id) {
		try(PreparedStatement stm = con.prepareStatement("UPDATE reservas SET fecha_entrada=?, fecha_salida=?, valor=?, forma_pago=? WHERE id=?")){
			stm.setObject(1, Date.valueOf(fechaE));
			stm.setObject(2, Date.valueOf(fechaS));
			stm.setString(3, valor);
			stm.setString(4, formaPago);
			stm.setInt(5, id);
			stm.execute();
		}catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void eliminarReserva(Integer id) {
		try {
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS=0");
			PreparedStatement stm = con.prepareStatement("DELETE FROM reservas WHERE id=?");
			stm.setInt(1, id);
			stm.execute();
			state.execute("SET FOREIGN_KEY_CHECKS=1");
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	
	public void transformarResultado(List<Reservas> reservas, PreparedStatement pstm) throws SQLException {
		try(ResultSet rst = pstm.getResultSet()){
			while(rst.next()) {
				int id= rst.getInt("id");
				LocalDate fechaE = rst.getDate("fecha_entrada").toLocalDate();
				LocalDate fechaS = rst.getDate("fecha_salida").toLocalDate();
				String valor = rst.getString("valor");
				String formaPago = rst.getString("forma_pago");
				Reservas propucto = new Reservas(id, fechaE, fechaS, valor, formaPago);
				reservas.add(propucto);
			}
		}
	}
}
