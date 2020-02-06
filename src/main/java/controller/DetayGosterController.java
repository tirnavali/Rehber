package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.DataModel;
import model.Kisi;

public class DetayGosterController {
    private int kisiIdentity;
    private Kisi kisi;

    @FXML
    private TextField kisiId, kisiAd, kisiSoyad, kisiIkinciAd, kisiIsTelefon, kisiCepTelefon, kisiEposta;

    @FXML
    private ImageView kisiFoto;

    @FXML
    private AnchorPane detayGoruntulePane;


    public void initialize(){
        System.out.println("Detay göster contoller çalışıyor");
        System.out.println("Kisi identitiy "+kisiIdentity);

    }


    public Boolean setKisiId(int kisiId) {
        this.kisiIdentity = kisiId;
        this.kisiId.setText(Integer.toString(kisiId));
        if(kisiId != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void setKisi(Kisi kisi){
        this.kisi = kisi;
        kisiId.setText(String.valueOf(kisi.getId()));
        kisiAd.setText(kisi.getAd());
        kisiIkinciAd.setText(kisi.getAd_2());
        kisiSoyad.setText(kisi.getSoyad());
        kisiIsTelefon.setText(kisi.getTelefon());
        kisiCepTelefon.setText(kisi.getCepTelefon());
        kisiEposta.setText(kisi.getEposta());

    }
}
