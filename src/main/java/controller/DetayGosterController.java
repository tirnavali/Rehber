package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.DataModel;
import model.Kisi;
import model.KisininBirimleri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetayGosterController {
    private int kisiIdentity;
    private Kisi kisi;
    private ArrayList<KisininBirimleri> kisiyeAitBirimler;
    private ObservableList<KisininBirimleri> kisiyeAitBirimlerObsListe = null;

    @FXML
    private TableView<KisininBirimleri> kisininBirimleriTable;

    @FXML
    private TableColumn<KisininBirimleri, String> kisi_birim_id_col;
    @FXML
    private TableColumn<KisininBirimleri, String> birim_adi_col;
    @FXML
    private TableColumn<KisininBirimleri, String> birim_bas_t_col,
            birim_bit_t_col;


    @FXML
    private TextField kisiId, kisiAd, kisiSoyad, kisiIkinciAd, kisiIsTelefon, kisiCepTelefon, kisiEposta;

    @FXML
    private ImageView kisiFoto;

    @FXML
    private AnchorPane detayGoruntulePane;


    public void initialize(){
        System.out.println("Detay göster contoller çalışıyor");
        System.out.println("Kisi identitiy "+kisiIdentity);


        Task<ObservableList<KisininBirimleri>> task = new KisiyeAitBirimleriGetirTask();
        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                //kisi_birim_id_col.setCellValueFactory(cellData ->  cellData.getValue().idProperty());
                birim_adi_col.setCellValueFactory(cellData -> cellData.getValue().birim_adiProperty());
                birim_bas_t_col.setCellValueFactory(cellData -> cellData.getValue().baslama_tarProperty());
                birim_bit_t_col.setCellValueFactory(cellData -> cellData.getValue().bitis_tarProperty());
                kisiyeAitBirimlerObsListe = task.getValue();

                kisininBirimleriTable.setItems(kisiyeAitBirimlerObsListe);

            }
        });

        new Thread(task).start();

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
        this.kisiyeAitBirimler = kisiyeAitBirimler;
        this.kisi = kisi;
        kisiId.setText(String.valueOf(kisi.getId()));
        kisiAd.setText(kisi.getAd());
        kisiIkinciAd.setText(kisi.getAd_2());
        kisiSoyad.setText(kisi.getSoyad());
        kisiIsTelefon.setText(kisi.getTelefon());
        kisiCepTelefon.setText(kisi.getCepTelefon());
        kisiEposta.setText(kisi.getEposta());

    }

    public void setKisiyeAitBirimler(ArrayList<KisininBirimleri> kisiyeAitBirimler){
        this.kisiyeAitBirimler = kisiyeAitBirimler;
        KisininBirimleri kb =  kisiyeAitBirimler.get(0);
        kb.toString();

    }
    class KisiyeAitBirimleriGetirTask extends Task {
        private int kisiId;


        @Override
        protected Object call() throws Exception {
            System.out.println("##############"+kisiyeAitBirimler.toString());
            return FXCollections.observableList(kisiyeAitBirimler);
        }
    }
}
