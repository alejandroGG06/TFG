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

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }


    @FXML
    public void crearUsuario() {
        String nombreStr = nombre.getText().trim();
        String correoStr = correo.getText().trim();
        String contra = contraseña.getText().trim();
        String montoStr = monto.getText().trim();

        // Validar campos vacíos



        try {

            if (nombreStr.isEmpty() || correoStr.isEmpty() || contra.isEmpty() || montoStr.isEmpty()) {
                mostrarAlerta("Campos vacíos", "Por favor, completa todos los campos.");
                return;
            }

            // Validar contraseña
            if (contra.length() < 8) {
                mostrarAlerta("Contraseña inválida", "La contraseña debe tener al menos 8 caracteres.");
                return;
            }

            // Validar correo (formato simple)
            if (!correoStr.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                mostrarAlerta("Correo inválido", "Introduce un correo electrónico válido.");
                return;
            }

            int monto = Integer.parseInt(montoStr);

            Usuario user = new Usuario();
            user.setNombre(nombreStr);
            user.setEmail(correoStr);
            user.setPassword(contra);
            user.setMonto(monto);

            usuarioService.crearUsuario(user);

            mostrarAlerta("Éxito", "Usuario creado correctamente.");

            // Cerrar ventana actual
            Stage stage = (Stage) nombre.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
