package org.menus.finanzas;

//@author AlejandroGpublic

import BBDD.Usuario;
import BBDD.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CrearUserController {

    @FXML
    TextField nombre;
    @FXML
    TextField correo;
    @FXML
    TextField contraseña;
    @FXML
    TextField monto;

    @FXML
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        crearUsuario();
    }

    @FXML
    public void crearUsuario(){
        String nombreStr = nombre.getText().trim();
        String correoStr = correo.getText().trim();
        String contra= contraseña.getText().trim();
        String montoStr = monto.getText().trim();


        try {
            Usuario user = new Usuario();
            user.setNombre(nombreStr);
            user.setEmail(correoStr);
            user.setPassword(contra);
            user.setMonto(Integer.parseInt(montoStr));


            usuarioService.crearUsuario(user);
            mostrarAlerta("creado","Usuario creado correctamente");


            Stage stage = (Stage) nombre.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);  // Usamos ERROR para mostrar el mensaje de error
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
