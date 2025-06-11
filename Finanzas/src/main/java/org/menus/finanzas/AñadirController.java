package org.menus.finanzas;

//@author AlejandroGpublic

import BBDD.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class AñadirController {

    private PrincipalController principalController;

    public void setPrincipalController(PrincipalController controller) {
        this.principalController = controller;
    }



    @FXML
    private TextField nombreField;

    @FXML
    private ChoiceBox tipoField;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextField inicialField;

    @FXML
    private final CategoriaService categoriaService = new CategoriaService();
    @FXML
    public void initialize() {
        tipoField.getItems().addAll("Fijo", "Variable");
        tipoField.setValue("Fijo");

    }

    @FXML
    private void guardarCategoria() {
        String nombre = nombreField.getText().trim();
        String tipo = tipoField.getValue().toString();
        String inicialStr = inicialField.getText().trim();
        LocalDate fecha = fechaPicker.getValue();

        if (nombre.isEmpty() || tipo.isEmpty() || inicialStr.isEmpty() || fecha == null) {
            mostrarAlerta("campos incompletos", "los campos no deben estar vacios");
            return;
        }

        try {
            int inicial = Integer.parseInt(inicialStr);
            Usuario usuario = UsuarioSesion.getUsuariosesion();

            if (usuario == null) {
                return;
            }

            Categoria nueva = new Categoria();
            nueva.setNombre(nombre);
            nueva.setTipo(tipo);
            nueva.setInicial(inicial);
            nueva.setFecha(fecha);
            nueva.setUsuario(usuario);

            categoriaService.crearCategoria(nueva);
            if (principalController != null) {
                principalController.initialize();
            }

            Stage stage = (Stage) nombreField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Formato incorrecto", "el formato debe ser numerico");
        }
    }


    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);  // Usamos ERROR para mostrar el mensaje de error
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
