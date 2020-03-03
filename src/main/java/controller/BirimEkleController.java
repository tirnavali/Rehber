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
    private Button ekleButton, deleteButton;



    @FXML
    public void initialize() {
        /** Sayfa açılırken konfigürasyon ayarları ...*/
        ekleButton.setDisable(true);
        /** Sayfa açılırken konfigürasyon ayarları ...*/
        informationLabel.setText("BİRİM Controller yüklendi ve çalışmalara başladı.");

        birimlerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                deleteButton.setDisable(false);

            } else {

                deleteButton.setDisable(true);
            }
        });

        birimAdiTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank() || newValue.isEmpty()) {
                ekleButton.setDisable(true);
            } else {
                ekleButton.setDisable(false);
            }
        });


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
                            /**Yeni birim güncellemesi veri tabanına yazılıyor.*/
                            try {
                                DataModel.getInstance().updateBirim(yeniBirim.getId(), yeniBirim.getAd());
                                informationLabel.setText("Veri düzenlendi!");
                                birimlerListView.setItems(FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC)));
                            } catch (SQLException e) {
                                e.printStackTrace();
                                informationLabel.setText("Veri düzenlenirken VT. Problemi oluştu.!");
                            }

                            /**Yeni birim güncellemesi veri tabanına yazılıyor.*/

//                            System.out.println("Hücreden alınan birim : " + yeniBirim.toString());
                            cell.commitEdit(yeniBirim);
//                            cell.setText(yeniBirim.getAd());
                            cell.setGraphic(new ListCell<>());
//                           ListCell<Birim> listCell = new ListCell<>();
//                           listCell.setItem(yeniBirim);
                           //cell.setGraphic(listCell);
                           //birimlerListView.refresh();
//                           cell.setGraphic(new TabloHucreleri());
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
    /**Seçili birimi düzenleyen fonk.*/
    @FXML
    public void duzenle(){
        informationLabel.setText(birimlerListView.getSelectionModel().getSelectedItem().toString());
    }
    /**Seçili birimi silen fonk.*/
    @FXML
    public void birimSil(){
        Birim birim = birimlerListView.getSelectionModel().getSelectedItem();
        int sonuc = DataModel.getInstance().deleteBirim(birim);
        informationLabel.setText("Silinen kayıt sayısı : "+sonuc);
        birimlerListView.setItems(FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC)));
    }
    /**Yeni birim ekleme fonks.*/
    @FXML
    public void birimEkle(){
        Birim yeniBirim = new Birim();
        yeniBirim.setAd(birimAdiTextField.getText());
        informationLabel.setText("Birim ekleniyor..."+yeniBirim.toString());
            /**Ekleme başarılı olursa...*/
        try{
            int sonuc = DataModel.getInstance().insertBirim(yeniBirim.getAd());
            informationLabel.setText("Yeni verinin id'si: "+sonuc);
            birimlerListView.setItems(FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC)));
            birimAdiTextField.clear();
            /**Ekleme başarısız olursa...*/
        } catch (SQLException e) {
            e.printStackTrace();
            informationLabel.setText("Veritabanına yazılamadı!");
        }

    }
    /**Birim düzenleme sınıfı. Liteye Çift tıklayınca aktif olur.*/
    class TabloHucreleri extends ListCell<Birim> {
        @Override
        protected void updateItem(Birim birim, boolean empty) {
            super.updateItem(birim, empty);
            setText(birim == null ? "" : birim.getAd());
        }
    }
}
