package andrewjf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import andrewjf.Controllers.Server;
import andrewjf.Helpers.Utils;
import andrewjf.Models.Store;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;

// Dominant (60%): Dark Navy Blue #0B0F33
// Accent (10%): Cyan Blue #1994f2
// Danger: Red  #fc3737
// White: #fff4f4

public class MainApp extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        Server server = new Server();
        new Thread(() -> {
            try {
                int port = 8080;
                while (true) {
                    try {
                        server.start(port);
                        System.out.println("Server started on port " + port);
                        break;
                    } catch (Exception e) {
                        System.out.println("Port " + port + " may already be in use, trying next port...");
                        port++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        launch(args);

        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage s) throws IOException {
        stage=s;
        setRoot("main","Game Store");
    }

    static void setRoot(String fxml) throws IOException {
        setRoot(fxml,stage.getTitle());
    }

    public static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        scene.getStylesheets().addAll(MainApp.class.getResource("/styles/Styles.css").toExternalForm());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest( event -> showSavingDialog());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }




    /**
     * Show a dialog to save the file
     * 
     */
    private static void showSavingDialog() {
        // Create a new stage for the dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setFullScreen(true);
        dialog.setFullScreenExitHint("");

        // Hide x button
        dialog.initStyle(javafx.stage.StageStyle.UNDECORATED);

        // Make dialog transparent
        dialog.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(-1.0);
        progressIndicator.setStyle("-fx-progress-color: #1994f2;");
        progressIndicator.setMaxSize(35, 35);


        StackPane dialogPane = new StackPane();
        dialogPane.setStyle("-fx-background-color: transparent;");
        dialogPane.getChildren().add(progressIndicator);

        Scene dialogScene = new Scene(dialogPane, 120, 70);
        dialogScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        dialog.setScene(dialogScene);
        dialog.setOnCloseRequest(event -> {
            // Consume the event to prevent closing
            event.consume();
        });
        dialog.show();


        new Thread(() -> {
            try {
                ArrayList<SellableProducts> products = Store.getInstance().getProducts();
                String msg = Utils.saveToFile(products.toArray(new SellableProducts[0]));
                System.out.println(msg);
                Thread.sleep(1500);

                if (msg.equals("File Saved!")) {
                    javafx.application.Platform.runLater(() -> {
                        dialog.close();
                        stage.close();
                    });
                } else {
                    // File not saved, show warning to use, don't close
                    warningDialog(msg);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void warningDialog(String message) {
        // Create a new stage for the dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setTitle("Warning");
        dialog.setFullScreen(true);
        dialog.setFullScreenExitHint("");

        // Hide x button
        dialog.initStyle(javafx.stage.StageStyle.UNDECORATED);

        // Make dialog transparent
        dialog.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(-1.0);
        progressIndicator.setStyle("-fx-progress-color: #fc3737;");
        progressIndicator.setMaxSize(35, 35);
    }
}
