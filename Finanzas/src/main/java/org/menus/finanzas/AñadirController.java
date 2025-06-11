package org.menus.finanzas;

//@author AlejandroGpublic

import BBDD.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private  TextField fechaPicker;

    @FXML
    private TextField inicialField;

    @FXML
    private final CategoriaService categoriaService = new CategoriaService();

    @FXML
    private ChoiceBox mesfield;

    @FXML
    public void initialize() {
        tipoField.getItems().addAll("Fijo", "Variable");
        tipoField.setValue("Fijo");

        mesfield.getItems().addAll("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
        mesfield.setValue("seleccione un mes");
    }



    @FXML
    private void guardarCategoria() {
        String nombre = nombreField.getText().trim();
        String tipo = tipoField.getValue().toString();
        String inicialStr = inicialField.getText().trim();
        String fecha = fechaPicker.getText().trim();
        String meses = mesfield.getValue().toString();

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
            nueva.setMes(meses);

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
