package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent parent;

    @FXML
    private TextField idField;

    @FXML
    private TextField passField;

    @FXML
    private TextField userField;

    @FXML
    void login(ActionEvent event) throws IOException {

        if(userField.getText().isEmpty()||idField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Login data is empty !");
        }else if (idField.getText().equals("00")&&userField.getText().equals("root")&&passField.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Root logged in!");
            crf(event);
        }else if (idField.getText().equals("0011")&&userField.getText().equals("dr.usama")&&passField.getText().equals("dr.usama1212")){

            crf(event);
        }
        else if (idField.getText().equals("0012")&&userField.getText().equals("mousa")&&passField.getText().equals("mousa12")){

            crf(event);
        }
        else if (idField.getText().equals("0013")&&userField.getText().equals("youssef")&&passField.getText().equals("youssef")){
            crf(event);
        }
        else{
            JOptionPane.showMessageDialog(null,"Login data is not valid !");
        }
    }

    void crf(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("certificate.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
