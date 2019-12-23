package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Birim;
import model.DataModel;
import model.Kisi;

import java.io.File;
import java.time.LocalDate;

public class KisiEkleController {

    @FXML
    DialogPane kisiEklePane;
    @FXML
    private ComboBox<Birim> birimComboBox;
    @FXML
    private TextField inputAd, inputSoyad, inputAd2, inputIsTelefon, inputCepTelefon, inputEposta;
    @FXML
    private DatePicker dateBaslama, dateBitis;
    @FXML
    private Label fotografYoluLabel;

    /* Fonksiyonlarda kullanılan parametlereler */
    private Birim secilenBirim = null;
    private LocalDate baslangic;
    private LocalDate bitis;



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

                baslangic = dateBaslama.getValue();
                if(dateBitis != null) {
                    bitis = dateBitis.getValue();
                } else {
                    bitis = null;
                    System.out.println(bitis.toString());
                }

                DataModel.getInstance().insertKisiXBirim(yeniKisi.getAd(),yeniKisi.getSoyad(),yeniKisi.getAd_2(),yeniKisi.getTelefon(),yeniKisi.getCepTelefon(),
                        yeniKisi.getEposta(),yeniKisi.getFotograf(),secilenBirim.getAd(),dateBaslama.getValue().toString(),dateBitis.getValue().toString());



                /*buradan devam*/


            }
        });
    }

    /** Kişi ekle sayfasındaki combox verilerini gösterip,
     * seçilen veriyi değişkene atar.**/
    @FXML
    public void comboBoxAction(){
        System.out.println("İçerik -- "+birimComboBox.getValue());
        this.secilenBirim = birimComboBox.getSelectionModel().getSelectedItem();
    }

    /**Kişi ekleme sayfası fotoğraf seçme tuşu*/
    @FXML
    private void fotografSec(){

        File initialDirectory = new File(System.getProperty("user.home"));
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lütfen bir fotoğraf seçiniz!");

        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File selectedPhoto =
        fileChooser.showOpenDialog(stage);
        fotografYoluLabel.setText(selectedPhoto.toString());
        fotografYoluLabel.setTooltip(new Tooltip(selectedPhoto.toString()));

    }
}
