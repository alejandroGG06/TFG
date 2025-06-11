package org.menus.finanzas;

//@author AlejandroGpublic

import BBDD.CategoriaService;
import BBDD.Usuario;
import BBDD.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class AhorroController {

    @FXML
    private VBox ahorroBox;
    private final Usuario usuario = UsuarioSesion.getUsuariosesion();
    private final CategoriaService categoriaService = new CategoriaService();

    @FXML
    public void initialize() {
        List<String> meses = categoriaService.obtenerMesesUnicos(usuario);
        int salario = usuario.getMonto();
        int ahorroTotal = 0;

        for (String mes : meses) {
            int gastoMes = categoriaService.obtenerGastoTotalDelMes(usuario, mes);
            int ahorroMes = salario - gastoMes;
            ahorroTotal += ahorroMes;

            Label label = new Label("💸 Ahorro en " + mes + ": " + ahorroMes + " €");
            label.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            ahorroBox.getChildren().add(label);
        }

        ahorroBox.getChildren().add(new Separator());

        Label total = new Label("💼 Total ahorrado al año: " + ahorroTotal + " €");
        total.setStyle("-fx-text-fill: #00ff99; -fx-font-size: 18px; -fx-font-weight: bold;");
        ahorroBox.getChildren().add(total);
    }
}
