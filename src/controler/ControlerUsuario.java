package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Usuarios;
import views.Login;
import views.MenuUsuario;

public class ControlerUsuario implements ActionListener{

	private Login vista;
	
	public ControlerUsuario(Login vista) {
		this.vista=vista;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String nombre = vista.getNombre();
		String contrasena = vista.getContrasena();
		
		if(Usuarios.validarUsuario(nombre, contrasena)) {
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true); 
			vista.dispose();
		}else {
			JOptionPane.showMessageDialog(vista, "¡Usuario o Contraseña no válidos!");
		}
	}
	
}
