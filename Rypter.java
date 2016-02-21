package olegshan.com.facebook.rypter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Bors on 21.11.2015.
 */
public class Rypter extends Application {

    String labelText;
    File file;
    Label nameLabel;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Rypter 1.0");

        Label label = new Label("Rypter");
        label.setFont(new Font("Times New Roman", 35));
        Label fileLabel = new Label("Choose the file");
        Label keyLabel = new Label("Enter the key");
        nameLabel = new Label("");
        nameLabel.setTextFill(Color.GREEN);

        final TextField keyInput = new TextField();
        keyInput.setPromptText("key");

        Button chooseButton = new Button("Choose...");
        chooseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                file = fileChooser.showOpenDialog(null);
                nameLabel.setText(file.getName());
            }
        });

        Button goButton = new Button("        Go        ");
        goButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    crypt(file, keyInput.getText());
                }catch (Exception e){}
                okWindow();
                nameLabel.setText("");
                keyInput.setText("");
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40,0,0,30));
        grid.setVgap(25);
        grid.setHgap(10);

        GridPane.setConstraints(label, 0,0);
        GridPane.setConstraints(fileLabel, 0,1);
        GridPane.setConstraints(nameLabel, 0,3);
        GridPane.setConstraints(keyLabel, 0,2);
        GridPane.setConstraints(chooseButton, 1,1);
        GridPane.setConstraints(keyInput, 1,2);
        GridPane.setConstraints(goButton, 0,4);

        grid.getChildren().addAll(label, fileLabel, nameLabel, keyLabel, chooseButton, keyInput, goButton);
        Scene scene = new Scene(grid, 350, 300);
        stage.setScene(scene);
        stage.show();
    }

    public static void crypt(File file, String key) throws Exception {
        String outName = null;
        FileInputStream inputStream = new FileInputStream(file);
        if (file.getName().substring(file.getName().lastIndexOf(".")).equals(".crypted")){
            outName = file.getName().substring(0, file.getName().lastIndexOf("."));
        }else outName = file.getName() + ".crypted";

        FileOutputStream outputStream = new FileOutputStream(file.getParent() + "\\" + outName);
        byte[] buffer1 = new byte[inputStream.available()];
        byte[] buffer2 = new byte[inputStream.available()];
        int j = 0;

        while (inputStream.available() > 0){
            inputStream.read(buffer1);
        }

        for (int i = 0; i < buffer1.length; i++){
            buffer2[i] = (byte) (buffer1[i] ^ key.charAt(j));
            j++;
            if (j == key.length()) j = 0;
        }

        outputStream.write(buffer2);

        inputStream.close();
        outputStream.close();
    }

    public static void okWindow(){
        final Stage stage = new Stage();
        stage.setTitle("Done!");
        stage.setMinWidth(250);
        Label label = new Label("Crypted successfully!");
        Button button = new Button("     Ok     ");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });


        VBox vb = new VBox();
        vb.setPadding(new Insets(30, 50, 50, 50));
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(label, button);
        Scene scene = new Scene(vb, 200, 100);
        stage.setScene(scene);
        stage.show();
    }
}