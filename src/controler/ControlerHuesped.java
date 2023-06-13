package controler;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import DAO.HuespedDAO;
import factory.ConnectionFactory;
import model.Huespedes;

public class ControlerHuesped {
	
	private HuespedDAO huespeddao;
	
	public ControlerHuesped() {
		Connection con = new ConnectionFactory().conectar();
		this.huespeddao = new HuespedDAO(con);
	}

	public void guardar(Huespedes huespedes) {
		this.huespeddao.guardar(huespedes);
	}
	
	public List<Huespedes> mostrarHuespedes(){ 
		return this.huespeddao.mostrar();
	}
	
	public List<Huespedes> buscarHuespedes(String id){ 
		return this.huespeddao.buscarId(id);
	}
	
	public void actualizarh(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		this.huespeddao.actualizarH(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
	public void elimnarh(Integer idReserva) {
		this.huespeddao.eliminar(idReserva);
	}
	
}
