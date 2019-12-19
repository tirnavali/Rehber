package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Birim;
import model.DataModel;
import model.Kisi;

public class KisiEkleController {

    @FXML
    DialogPane kisiEklePane;
    @FXML
    private ComboBox<Birim> birimComboBox;
    @FXML
    private TextField inputAd, inputSoyad, inputAd2, inputIsTelefon, inputCepTelefon, inputEposta;

    /* Fonksiyonlarda kullanılan parametlereler */
    private Birim secilenBirim = null;



    public void initialize(){
        System.out.println("Kişi ekle controller çalışıyor.");
        ObservableList observableList = FXCollections.observableList(DataModel.getInstance().getAllBirimler(DataModel.ORDER_BY_ASC));
        birimComboBox.setItems(observableList);

        Button cancel = (Button) kisiEklePane.lookupButton(ButtonType.CANCEL);
        cancel.setText("Vazgeç");
        Button apply = (Button)kisiEklePane.lookupButton(ButtonType.APPLY);
        apply.setText("Kaydet");
        apply.setDisable(false);

        /*Kaydet tuşuna basıldığında form alanlarındaki veriyi örnekleyerek veri tabanına kaydeder.*/
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Kisi yeniKisi = new Kisi();
                yeniKisi.setAd(inputAd.getText());
                yeniKisi.setSoyad(inputSoyad.getText());
                yeniKisi.setAd_2(inputAd2.getText());
                yeniKisi.setTelefon(inputIsTelefon.getText());
                yeniKisi.setCepTelefon(inputCepTelefon.getText());
                yeniKisi.setEposta(inputEposta.getText());
                yeniKisi.setFotograf("");

                /*buradan devam*/


            }
        });
    }

    @FXML
    public void comboBoxAction(){
        System.out.println("İçerik -- "+birimComboBox.getValue());
        this.secilenBirim = birimComboBox.getSelectionModel().getSelectedItem();
    }
}
