package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import model.Birim;
import model.DataModel;
import model.Kisi;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


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
    private Label fotografYoluLabel, kisiEkleBilgiLabel;

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



        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        });

        /**Kaydet tuşuna basıldığında form alanlarındaki veriyi örnekleyerek veri tabanına kaydeder.*/
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(inputAd.getText().isBlank() || inputSoyad.getText().isBlank() ||
                    inputIsTelefon.getText().isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Yetersiz veri hatası");
                    alert.setHeaderText("Yeterli veri girmediniz!");
                    alert.setContentText("Lütfen asgari olarak (Ad, Soyad, İş Telefonu, Birim Bilgisi) alanlarına veri giriniz.");
                    Optional<ButtonType> result = alert.showAndWait();

                } else {
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

                    bilgiEkraniGuncelle("Kayıt Başarılı...");





                } //end else


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

    /**Kişi ekleme sayfası fotoğraf seçme tuşu
     * Fotoğraf images klasörüne kopyalanıyor..
     * Fakat kullanıcı vazgeçerse silinmesi lazım.
     * */
    @FXML
    private void fotografSec(){

        File initialDirectory = new File(System.getProperty("user.home"));
        File curdir           = new File(System.getProperty("user.dir"));
        System.out.println("CURDIR ___ "+curdir.toString());
        File newPhotoName = new File(curdir.toString()+"\\src\\main\\resources\\images\\"+LocalDate.now().toString()+"-"+ UUID.randomUUID().toString()+".jpg");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lütfen bir fotoğraf seçiniz!");

        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File selectedPhoto =
        fileChooser.showOpenDialog(kisiEklePane.getScene().getWindow()); // kök pencereye ulaştık

        fotografYoluLabel.setText(selectedPhoto.toString());
        fotografYoluLabel.setTooltip(new Tooltip(selectedPhoto.toString()));
        System.out.println("Fotograf yeni dosya adi :"+newPhotoName);


        try(InputStream in = new BufferedInputStream(new FileInputStream(selectedPhoto));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(newPhotoName))){
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer))>0){
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**Verilen bilgiyi bilgilendirme label ekranında 2.5 saniye göserip kaybolur.*/
    public void bilgiEkraniGuncelle(String bilgi) {
        kisiEkleBilgiLabel.setText(bilgi);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500), actionEvent1 -> kisiEkleBilgiLabel.setText("")
        ));
        timeline.play();
    }
}
