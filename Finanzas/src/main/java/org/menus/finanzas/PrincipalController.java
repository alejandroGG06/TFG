package org.menus.finanzas;

//@author AlejandroGpublic

import BBDD.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.*;

public class PrincipalController {

    @FXML
    VBox gastosBox;
    @FXML
    private PieChart pieChart;

    @FXML
    Label salario_inici;

    @FXML
    Label gastadototal;

    @FXML
    private ImageView Ahorroimagen;


    @FXML
    private ChoiceBox<String> meses;

    Usuario user = UsuarioSesion.getUsuariosesion();
    CategoriaService categoriaService = new CategoriaService();


    @FXML
    public void initialize() {
        user = UsuarioSesion.getUsuariosesion();

        cargarMeses();            // Llena el ComboBox con los meses disponibles
        configurarEventoCombo();  // Configura la lógica de cambio de mes

        // ⚠️ Solo cargar datos si hay un mes seleccionado
        String mesSeleccionado = meses.getValue();
        if (mesSeleccionado != null) {
            UsuarioSesion.setMesSeleccionado(mesSeleccionado);
            cargarDatos();         // Carga datos del PieChart
            mostrargastado();      // Muestra el total gastado
            mostrarCategorias();   // Lista las categorías del mes
        }

        mostrarSalario(); // Siempre mostramos el salario
    }

    public void cargarDatos() {
        String mesSeleccionado = UsuarioSesion.getMesSeleccionado();
        Map<String, Integer> datos = categoriaService.obtenerGastosPorMes(user, mesSeleccionado);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey() + " (€" + entry.getValue() + ")", entry.getValue()));
        }

        pieChart.setData(pieData);
        pieChart.setTitle("Gastos por Categoría – " + mesSeleccionado);
    }

    private void mostrarSalario() {
        if (user != null) {
            salario_inici.setText(user.getMonto() + "€");
        } else {
            salario_inici.setText("No disponible");
        }
    }

    private void mostrargastado() {
        String mesSeleccionado = UsuarioSesion.getMesSeleccionado();
        int total = categoriaService.obtenerGastoTotalDelMes(user, mesSeleccionado);
        gastadototal.setText(total + "€");
    }


    private void cargarMeses() {
        List<String> listaMeses = categoriaService.obtenerMesesUnicos(user);

        if (!listaMeses.isEmpty()) {
            meses.setItems(FXCollections.observableArrayList(listaMeses));
            meses.getSelectionModel().selectFirst();
            String mesSeleccionado = meses.getValue();
            UsuarioSesion.setMesSeleccionado(mesSeleccionado);
            cargarDatos();
            mostrargastado();
            mostrarCategorias();
        }
    }

    private void configurarEventoCombo() {
        meses.setOnAction(e -> {
            String mesSeleccionado = meses.getValue();
            UsuarioSesion.setMesSeleccionado(mesSeleccionado);
            cargarDatos();
            mostrargastado();
            mostrarCategorias();
        });
    }

    private void mostrarCategorias() {
        String mesSeleccionado = UsuarioSesion.getMesSeleccionado();
        Map<String, String> categorias = categoriaService.categoriassPorMes(user, mesSeleccionado);

        gastosBox.getChildren().clear(); // Limpiar el VBox

        for (Map.Entry<String, String> entry : categorias.entrySet()) {
            String nombre = entry.getKey();
            String valor = entry.getValue(); // Aquí es el mes (fecha), pero puedes cambiarlo al monto si prefieres

            Label label = new Label(nombre + ": " + valor);
            label.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 16px; -fx-text-fill: WHITE;");
            gastosBox.getChildren().add(label);
        }
    }



    @FXML
    public void abrirvista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("crearcategoria.fxml"));
            Parent root = loader.load();

            AñadirController a = loader.getController();
            a.setPrincipalController(this);

            root.getStylesheets().add(getClass().getResource("/css/crear.css").toExternalForm());
            Scene nuevaEscena = new Scene(root);
            Stage nuevaVentana = new Stage();  // nueva ventana

            nuevaVentana.setScene(nuevaEscena);
            nuevaVentana.setTitle("Nueva Vista");
            nuevaVentana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void abrirVentanaAhorro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Ahorro.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("/css/Ahorro.css").toExternalForm());


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ahorros");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
