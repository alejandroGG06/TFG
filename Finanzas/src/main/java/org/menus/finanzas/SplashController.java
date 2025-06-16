package org.menus.finanzas;

//@author AlejandroGpublic

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class SplashController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    public void initialize() {
        Task<Void> tareaCarga = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Simulación de carga
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(15);  // simula carga
                    updateProgress(i, 100);
                }

                // Intento de conexión a la base de datos
                try {
                    EntityManager em = Persistence.createEntityManagerFactory("FinanzasPU").createEntityManager();
                    em.createQuery("SELECT 1").getSingleResult();  // simple prueba
                    em.close();
                } catch (Exception e) {
                    throw new RuntimeException("Error al conectar con la base de datos", e);
                }

                return null;
            }
        };

        tareaCarga.setOnSucceeded(e -> {
            abrirVentanaPrincipal();
        });

        tareaCarga.setOnFailed(e -> {
            System.err.println("Error durante la carga: " + tareaCarga.getException());
            Platform.exit();
        });

        progressBar.progressProperty().bind(tareaCarga.progressProperty());

        Thread hilo = new Thread(tareaCarga);
        hilo.setDaemon(true);
        hilo.start();
    }

    private void abrirVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Hello-view.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("/css/Login.css").toExternalForm());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ahorros");
            stage.setScene(scene);
            stage.show();

                // Cerrar la pantalla de carga
                Stage splashStage = (Stage) progressBar.getScene().getWindow();
                splashStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
