package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class certificate {

    private Stage stage;
    private Scene scene;
    private Parent parent;

    @FXML
    private TextField certf;

    @FXML
    private TextField yourKey;

    @FXML
    void submet(ActionEvent event) {
        RSA r1=new RSA();
        String key=yourKey.getText();

        if(yourKey.getText().isEmpty()){

            JOptionPane.showMessageDialog(null,"Enter key to certified !");

        }else if(!certf.getText().isEmpty()){

           int response= JOptionPane.showConfirmDialog(null,"No need to certificate \nClear field ?","Alert!!",JOptionPane.YES_NO_CANCEL_OPTION);

           if(response==JOptionPane.YES_OPTION){

               certf.clear();

           }else if(response==JOptionPane.NO_OPTION){

               JOptionPane.showMessageDialog(null,"Can't submit with certificate inserted!!");

           }else if(response==JOptionPane.CANCEL_OPTION){

               certf.setText("Don't type anything here!!, pleas clear me..");
           }
        }else {

            String kr[] = r1.hash_enc(key);
            certf.setText(kr[0]+kr[1]);

        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
