package controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Kisi;
import model.KisininBirimleri;

import java.util.ArrayList;


public class DetayGosterController {
    private int kisiIdentity;
    private Kisi kisi;



    @FXML
    private TextField informationScreen;

    @FXML
    private TableView<KisininBirimleri> kisininBirimleriTable;
    @FXML
    private TableColumn<KisininBirimleri, Number> kisi_birim_id_col;
    @FXML
    private TableColumn<KisininBirimleri, String> birim_adi_col;
    @FXML
    private TableColumn<KisininBirimleri, String> birim_bas_t_col,
            birim_bit_t_col;
    @FXML
    Button taskCalistir;


    @FXML
    private TextField kisiId, kisiAd, kisiSoyad, kisiIkinciAd, kisiIsTelefon, kisiCepTelefon, kisiEposta;

    @FXML
    private ImageView kisiFoto;

    @FXML
    private AnchorPane detayGoruntulePane;

    @FXML
    public void initialize(){
        System.out.println("Detay göster contoller İNİTİALİZE FONKSİYONU **********************************************************************************");


        kisi_birim_id_col.setCellValueFactory(cellData ->  cellData.getValue().idProperty());
        birim_adi_col.setCellValueFactory(cellData -> cellData.getValue().birim_adiProperty());
        birim_bas_t_col.setCellValueFactory(cellData -> cellData.getValue().baslama_tarProperty());
        birim_bit_t_col.setCellValueFactory(cellData -> cellData.getValue().bitis_tarProperty());

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

    public void setKisiAndKisiyeAitBirimler(Kisi kisi, ArrayList<KisininBirimleri> kisiyeAitBirimler){
        System.out.println("setKisiAndKisiyeAitBirimler FONKSİYONU **********************************************************************************");
        this.kisi = kisi;
        this.kisininBirimleriTable.setItems(FXCollections.observableList(kisiyeAitBirimler));
        kisiId.setText(String.valueOf(kisi.getId()));
        kisiAd.setText(kisi.getAd());
        kisiIkinciAd.setText(kisi.getAd_2());
        kisiSoyad.setText(kisi.getSoyad());
        kisiIsTelefon.setText(kisi.getTelefon());
        kisiCepTelefon.setText(kisi.getCepTelefon());
        kisiEposta.setText(kisi.getEposta());

    }

}
