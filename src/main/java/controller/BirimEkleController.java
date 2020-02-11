package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Birim;
import model.DataModel;

import java.sql.SQLException;
import java.util.EventListener;

public class BirimEkleController {

    @FXML
    private TextField birimAdiTextField, duzenlenecekTextField;
    @FXML
    private Label informationLabel;
    @FXML
    private ListView<Birim> birimlerListView;



    @FXML
    public void initialize() {
        informationLabel.setText("Controller yüklendi ve çalışmalara başladı.");

        birimlerListView.setItems(FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC)));
        birimlerListView.setCellFactory(new Callback<ListView<Birim>, ListCell<Birim>>() {
            @Override
            public ListCell<Birim> call(ListView<Birim> param) {
                ListCell<Birim> row = new ListCell<>();

                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2 && (!row.isEmpty())){
                            System.out.println("Çalıiştı");
                        }
                    }
                });
                return row;
            }
        });

    }

    @FXML
    public void duzenle(){
        informationLabel.setText("Düzenleme  çalışıyor.");
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
    class TabloHucreleri extends ListCell<Birim> {
        @Override
        protected void updateItem(Birim birim, boolean empty) {
            super.updateItem(birim, empty);

            setText(birim.getAd());
        }
    }


}
