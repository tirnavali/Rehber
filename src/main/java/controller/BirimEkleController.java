package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Birim;
import model.DataModel;

import java.sql.SQLException;

public class BirimEkleController {

    @FXML
    private TextField birimAdiTextField;
    @FXML
    private Label informationLabel;
    @FXML
    private ListView<Birim> birimlerListView;


    @FXML
    public void initialize(){
        informationLabel.setText("Controller yüklendi ve çalışmalara başladı.");
        birimlerListView.setItems(FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC)));
    }

    @FXML
    public void birimSil(){
        Birim birim = birimlerListView.getSelectionModel().getSelectedItem();
        int sonuc = DataModel.getInstance().deleteBirim(birim);
        informationLabel.setText("Silinen kayıt sayısı : "+sonuc);
        birimlerListView.setItems(FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC)));
    }

    @FXML
    public void birimEkle(){

        Birim yeniBirim = new Birim();
        yeniBirim.setAd(birimAdiTextField.getText());
        informationLabel.setText("Birim ekleniyor..."+yeniBirim.toString());
        try{
            int sonuc = DataModel.getInstance().insertBirim(yeniBirim.getAd());
            informationLabel.setText("Yeni verinin id'si: "+sonuc);
            birimlerListView.setItems(FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC)));
        } catch (SQLException e) {
            e.printStackTrace();
            informationLabel.setText("Veritabanına yazılamadı!");
        }

    }


}
