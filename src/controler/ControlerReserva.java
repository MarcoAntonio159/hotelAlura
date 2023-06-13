package controler;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import DAO.ReservaDAO;
import factory.ConnectionFactory;
import model.Reservas;

public class ControlerReserva {

	private ReservaDAO reservadao;

	public ControlerReserva() {
		Connection con = new ConnectionFactory().conectar(); 
		this.reservadao = new ReservaDAO(con);
	}
	public void guardar(Reservas reservas) {
		this.reservadao.guardar(reservas);
	}
	
	public List<Reservas> mostrar(){
		return this.reservadao.mostrar();
	}
	
	public List<Reservas> buscar(String id){
		return this.reservadao.buscarId(id);
	}
	
	public void actulizarReserva(LocalDate fechaE, LocalDate fechaS, String valor, String formaPago, Integer id) {
		this.reservadao.actualizar(fechaE, fechaS, valor, formaPago, id);
	}
	
	public void eliminar(Integer id) {
		this.reservadao.eliminarReserva(id);
	}
}
