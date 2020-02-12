package controller;


import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Birim;
import model.DataModel;

import java.sql.SQLException;

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
                ListCell<Birim> cell = new TabloHucreleri();
                TextField editTxtField = new TextField();

                cell.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)){
                            Birim yeniBirim = new Birim();
                            yeniBirim.setId(cell.getItem().getId());
                            yeniBirim.setAd(editTxtField.getText());

                            System.out.println("Hücreden alınan birim : " + yeniBirim.toString());
                            cell.commitEdit(yeniBirim);
                            cell.setText(yeniBirim.getAd());
                            //cell.setGraphic(new ListCell<>());
                           ListCell<Birim> listCell = new ListCell<>();
                           listCell.setItem(yeniBirim);
                           cell.setGraphic(listCell);
                           birimlerListView.refresh();
                           //cell.setGraphic(new TabloHucreleri());
                        } else if(event.getCode().equals(KeyCode.ESCAPE)){
                            System.out.println("ESCAPE BASILDI");
                            cell.cancelEdit();
                            cell.setGraphic(new TabloHucreleri());
                            birimlerListView.refresh();
                        }
                    }
                });

                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2 && (!cell.isEmpty())) {
//                            System.out.println(cell.getItem().toString());
                            String birim_adi = birimlerListView.getSelectionModel().getSelectedItem().getAd();
                            cell.startEdit();
                            //cell.setText(birim_adi);
                            editTxtField.setText(birim_adi);
                            cell.setGraphic(editTxtField);
                        }
                    }
                });
                return cell;
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

            setText(birim == null ? "" : birim.getAd());
        }
    }
}
