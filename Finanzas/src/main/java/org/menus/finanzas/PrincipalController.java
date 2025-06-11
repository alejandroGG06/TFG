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
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    private ChoiceBox<YearMonth> meses;

    Usuario user = UsuarioSesion.getUsuariosesion();
    CategoriaService categoriaService = new CategoriaService();

    private final Locale localeES = new Locale("es");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", localeES);

    @FXML
    public void initialize() {
        user = UsuarioSesion.getUsuariosesion();

        cargarMeses();
        configurarEventoCombo();

        if (meses.getValue() != null) {
            cargarDatos();
            mostrargastado();
            mostrarCategorias();
        }

        mostrarSalario();
    }
    private void cargarMeses() {
        List<YearMonth> listaMeses = categoriaService.obtenerMesesUnicos(user);
        meses.setItems(FXCollections.observableArrayList(listaMeses));

        meses.setConverter(new StringConverter<YearMonth>() {
            @Override
            public String toString(YearMonth mes) {
                if (mes == null) return "";
                return mes.format(DateTimeFormatter.ofPattern("MMMM", new Locale("es")));
            }

            @Override
            public YearMonth fromString(String string) {
                return YearMonth.parse(string, DateTimeFormatter.ofPattern("MMMM", new Locale("es")));
            }
        });

        if (!listaMeses.isEmpty()) {
            meses.getSelectionModel().selectFirst();
            UsuarioSesion.setMesSeleccionado(formatearMes(meses.getValue()));
        }
    }



    private void configurarEventoCombo() {
        meses.setOnAction(e -> {
            YearMonth seleccionado = meses.getValue();
            if (seleccionado != null) {
                UsuarioSesion.setMesSeleccionado(formatearMes(seleccionado));
                cargarDatos();
                mostrargastado();
                mostrarCategorias();
            }
        });
    }

    private void cargarDatos() {
        YearMonth mesSeleccionado = meses.getValue();
        if (mesSeleccionado == null) return;

        Map<String, Integer> datos = categoriaService.obtenerGastosPorMes(user, mesSeleccionado);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey() + " (€" + entry.getValue() + ")", entry.getValue()));
        }

        pieChart.setData(pieData);
        pieChart.setLabelsVisible(false);
        pieChart.setTitle("Gastos por Categoría – " + formatearMes(mesSeleccionado));
    }

    private void mostrarSalario() {
        if (user != null) {
            salario_inici.setText(user.getMonto() + "€");
        } else {
            salario_inici.setText("No disponible");
        }
    }

    private void mostrargastado() {
        YearMonth mes = meses.getValue();
        if (mes == null) return;

        int gasto = categoriaService.obtenerGastoTotalDelMes(user, mes);
        gastadototal.setText(" "+gasto +" €");
    }

    private void mostrarCategorias() {
        YearMonth mesSeleccionado = meses.getValue();
        if (mesSeleccionado == null) return;

        Map<String, String> categorias = categoriaService.categoriassPorMes(user, mesSeleccionado);

        gastosBox.getChildren().clear();

        for (Map.Entry<String, String> entry : categorias.entrySet()) {
            Label label = new Label(entry.getKey() + ": " + entry.getValue());
            label.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 16px; -fx-text-fill: WHITE;");
            gastosBox.getChildren().add(label);
        }
    }

    private String formatearMes(YearMonth mes) {
        return mes.format(formatter);
    }

    // Métodos para abrir otras ventanas (sin cambios) ...


    @FXML
    public void abrirvista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("crearcategoria.fxml"));
            Parent root = loader.load();

            AñadirController a = loader.getController();
            a.setPrincipalController(this);

            root.getStylesheets().add(getClass().getResource("/css/crear.css").toExternalForm());
            Scene nuevaEscena = new Scene(root);
            Stage nuevaVentana = new Stage();
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