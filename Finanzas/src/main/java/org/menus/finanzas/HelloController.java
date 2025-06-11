package org.menus.finanzas;

import BBDD.Usuario;
import BBDD.UsuarioService;
import BBDD.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField user;

    @FXML
    private PasswordField contraseña;

    @FXML
    private Button inicio;
    @FXML
    private Button crearUser;


    private UsuarioService usuarioService;  // Instancia del servicio



    @FXML
    private void initialize() {

        usuarioService = new UsuarioService();
        // Agregar acción al botón


    }

    @FXML
    private void onLogin() {
        String usuario = user.getText();
        String contrasena = contraseña.getText();

        // Validamos las credenciales con el servicio
        Usuario u = usuarioService.validarCredenciales(usuario, contrasena);
        if (u != null) {
            // Si el login es correcto, mostramos la pantalla principal
            UsuarioSesion.setUsuariosesion(u);
            cargarPantallaPrincipal();
        } else {
            // Si las credenciales son incorrectas, mostramos una alerta
            mostrarAlerta("Error", "Usuario o contraseña incorrectos");
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);  // Usamos ERROR para mostrar el mensaje de error
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }



    @FXML
    private void crearUSer(){
        try {
            // Cargar la nueva escena (pantalla principal)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/menus/finanzas/UserAdd.fxml"));  // Ruta de la nueva pantalla
            Scene nuevaEscena = new Scene(loader.load());

            nuevaEscena.getStylesheets().add(getClass().getResource("/css/crear.css").toExternalForm());

            // Obtener la ventana actual (stage)
            Stage stage = new Stage();

            // Cambiar la escena
            stage.setScene(nuevaEscena);
            stage.setTitle("crear usuario");  // Título de la ventana
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla de creacion de usuario");
        }

    }

    private void cargarPantallaPrincipal() {
        try {
            // Cargar la nueva escena (pantalla principal)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/menus/finanzas/pantallaPrincipal.fxml"));  // Ruta de la nueva pantalla
            Scene nuevaEscena = new Scene(loader.load());

            nuevaEscena.getStylesheets().add(getClass().getResource("/css/Principal.css").toExternalForm());

            // Obtener la ventana actual (stage)
            Stage stage = (Stage) user.getScene().getWindow();

            // Cambiar la escena
            stage.setScene(nuevaEscena);
            stage.setTitle("Pantalla Principal");  // Título de la ventana
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla principal");
        }
    }
}