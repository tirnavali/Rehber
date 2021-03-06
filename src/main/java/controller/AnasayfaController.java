package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.DataModel;
import model.Kisi;
import model.KisininBirimleri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**Anasayfa kontrolcüsü.*/
public class AnasayfaController {
    private ObservableList<Kisi> anaEkrankisiler = null;
    @FXML
    TableView<Kisi> kisilerTablosu;
    @FXML
    TableColumn<Kisi, String> kisiAd, kisiSoyad, kisiTelefon, kisiBirimAdi;
    @FXML
    TableColumn<KisininBirimleri, Number> idCol;
    @FXML
    TableColumn<KisininBirimleri, String>birimCol, basTcol, bitTcol;

    @FXML
    TableView<KisininBirimleri> kisininBirimleriTablosu;

    @FXML
    private ImageView fotograf ;

    @FXML
    private Button button1, ekle, silButton, guncelleButton, fotoDegistirButton;

    @FXML
    ProgressBar uprogressBar;
    @FXML
    VBox anasayfaVbox;

    @FXML
    TextField adTextField, soyadTextField, ad2TextField, isTelTextField, cepTelTextField, epostaTextField;

    private Task<ObservableList<Kisi>> task = new GetAllPeopleTask();


    @FXML
    public void initialize(){
//        System.out.println("Controller initialize methodu çalıştı...");
        uprogressBar.setProgress(1);
        Image foto = new Image(getClass().getResourceAsStream("/images/person.png"));
        fotograf.setImage(foto);

        //Soldaki listede seçili olmayan birisi varsa sil, güncelle ve fotodeğiştir tuşlarını kapat.

        kisilerTablosu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null){
                silButton.setDisable(true);
                guncelleButton.setDisable(true);
                fotoDegistirButton.setDisable(true);
            }
        });



        ArrayList<KisininBirimleri> kisiyeAitBirimler = null;

        // kısa Yoldan yazılmış satıra tıklama fonksiyonu
//        kisilerTablosu.setRowFactory(tv ->
//            {
//                TableRow<Kisi> row = new TableRow<Kisi>();
//                row.setOnMouseClicked(event -> {
//                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                        Kisi kisi = row.getItem();
//                        System.out.println("Kişi "+ kisi);
//                    }
//                });
//                return row;
//            });

        //* Bu Fonksiyon uzun yoldan aksiyon eklemeyi gösteriyor. Kısa yol için lambda kullanılıyor
        //  Burada Callback 2 parametre alıyor. Birincisi Callback ateşleyenin kim olduğu
        //  ikinicisi Callback sonucu ne döneceği
        //  Callback bir interface olduğu için call methodu ile çalışmamız gerektiğini söylüyor.
        //  call methodu altına yeni bir TableRow tanımlıyoruz. Olay tetikleyiciyi bu fonksiyon içinde
        //  gerçekleştiriyoruz ki istediğimiz olay tetiklenince CallBack bize dönüş yapsın.
        // */

        kisilerTablosu.setRowFactory(
                new Callback<TableView<Kisi>, TableRow<Kisi>>() {
                    @Override
                    public TableRow<Kisi> call(TableView<Kisi> param) {
                        TableRow<Kisi> row = new TableRow<>();
                        row.setOnMouseClicked(
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        if(event.getClickCount() == 2 && (!row.isEmpty())){
                                            Kisi kisi = row.getItem();
                                            detayGoster();
//                                            System.out.println("Kişi "+kisi);
                                        } else if(event.getClickCount() == 1 && (!row.isEmpty())){

                                            /** Listeden bir eleman seçilirse tuşları canlandır.*/
                                            silButton.setDisable(false);
                                            fotoDegistirButton.setDisable(false);
                                            guncelleButton.setDisable(false);

                                            Kisi kisi = DataModel.getInstance().findKisi(row.getItem().getId());
                                            ArrayList<KisininBirimleri> mylist = DataModel.getInstance().findKisiBirimler(kisi.getId());

                                            adTextField.setText(kisi.getAd());
                                            soyadTextField.setText(kisi.getSoyad());
                                            ad2TextField.setText(kisi.getAd_2());
                                            cepTelTextField.setText(kisi.getCepTelefon());
                                            isTelTextField.setText(kisi.getTelefon());
                                            epostaTextField.setText(kisi.getEposta());
                                            kisininBirimleriTablosu.setItems(FXCollections.observableList(mylist));
                                            idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                                            birimCol.setCellValueFactory(cellData -> cellData.getValue().birim_adiProperty());
                                            basTcol.setCellValueFactory(cellData -> cellData.getValue().baslama_tarProperty());
                                            bitTcol.setCellValueFactory(cellData -> cellData.getValue().bitis_tarProperty());

                                        }
                                    }
                                }
                        );
                        return row;
                    }
                }
        );


        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                kisiAd.setCellValueFactory(cellData -> cellData.getValue().adProperty());
                kisiSoyad.setCellValueFactory(cellData -> cellData.getValue().soyadProperty());
                kisiTelefon.setCellValueFactory(cellData -> cellData.getValue().telefonProperty());
                kisiBirimAdi.setCellValueFactory(cellData -> cellData.getValue().epostaProperty());
                anaEkrankisiler = task.getValue();
                kisilerTablosu.setItems(anaEkrankisiler);
                kisilerTablosu.refresh();
//                System.out.println("ANA EKRAN KİSİLER --- "+anaEkrankisiler.toString());

            }
        });
        uprogressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
    /**Anasayfadaki sol tablodan seçilen kişiyi siler.*/
    @FXML
    public void kisiyiSil(){
        System.out.println("Kişi sil");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kişi Silme İşlemi");
        alert.setHeaderText("Silme işlemi!");
        alert.setContentText("Bu kişiyi veritabanından silmek istiyor musunuz?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Kisi kisi = kisilerTablosu.getSelectionModel().getSelectedItem();
            int silinenKayitSay =  DataModel.getInstance().deleteKisi(kisi);
            System.out.println(silinenKayitSay+ " - kayıt silindi!");
            kisilerTablosu.setItems(FXCollections.observableList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC)));
            kisilerTablosu.getSelectionModel().clearSelection();
            clearDetailedFields();
        } else {
            System.out.println("silinmedi");
            // ... user chose CANCEL or closed the dialog
        }
    }
    /**Anasayfadaki sol tablodan seçilen kişiyi günceller.*/
    @FXML
    public void kaydiGuncelle(){
        Kisi kisi = new Kisi();
        kisi.setId(kisilerTablosu.getSelectionModel().getSelectedItem().getId());
        kisi.setAd(adTextField.getText());
        kisi.setAd_2(ad2TextField.getText());
        kisi.setSoyad(soyadTextField.getText());
        kisi.setCepTelefon(cepTelTextField.getText());
        kisi.setTelefon(isTelTextField.getText());
        kisi.setEposta(epostaTextField.getText());

        //DataModel.getInstance().updateKisi(kisi);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kişi Güncellendi");
        alert.setHeaderText("Kişinin bilgilerini başarıyla güncellediniz!");
        alert.setContentText("Tebrikler!");

        Optional<ButtonType> result = alert.showAndWait();


    }
    /**Anasayfadaki sol tablodan seçilen kişinin resmini değiştirir.*/
    @FXML
    public void fotoDegistir(){
        kisilerTablosu.getSelectionModel().clearSelection();
        System.out.println("Değişen foto : ****");
    }
    /**Toolbardaki ekle butonuna bağlıdır. VT'ye yeni birim ekleme sayfasını açar.*/
    @FXML
    public void birimEkle(){
        //FXML aittir Parent aittir Scene aittir Stage
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/birimEkle.fxml"));
            Parent birimEkle = loader.load();
            Scene birimEkleScene = new Scene(birimEkle);
            Stage birimEkleStage = new Stage();
            birimEkleStage.initOwner(anasayfaVbox.getScene().getWindow());
            birimEkleStage.initModality(Modality.APPLICATION_MODAL);
            birimEkleStage.setScene(birimEkleScene);
            birimEkleStage.setTitle("Yeni Birim Ekle");
            birimEkleStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("BirimEkle FXML Bulunamıyor!");
        }
    }

    /**Anasayfa soldaki tabloda seçilen kişiye ait detayları göserir. */
    public void detayGoster(){
        Kisi secilen =  kisilerTablosu.getSelectionModel().getSelectedItem();
//        System.out.println("Detaylar gösteriliyor.."+secilen.toString());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detayGoruntule.fxml"));

            Parent detayGoster = loader.load();
            // Statik olarak yüklersen controller ayarı yapamazsın dikkatli ol.
//            Parent detayGoster =
//                    FXMLLoader.load(getClass().getResource("/fxml/detayGoruntule.fxml"));
            Scene scene = new Scene(detayGoster, 600, 800);
            Stage detayStage = new Stage();
            detayStage.initOwner(anasayfaVbox.getScene().getWindow());
            detayStage.setScene(scene);
            detayStage.initModality(Modality.APPLICATION_MODAL);

            DetayGosterController  dgController = loader.<DetayGosterController>getController();
            Kisi kisi = DataModel.getInstance().findKisi(secilen.getId());
            ArrayList<KisininBirimleri> kisiyeAitBirimler = DataModel.getInstance().findKisiBirimler(secilen.getId());

            dgController.setKisiAndKisiyeAitBirimler(kisi, kisiyeAitBirimler);
            detayStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Detay Görüntüle FXML Yüklenemedi");

        }
    }
    /**Toolbardaki Kişi ekle butonuna bağlıdır. VT'ye yeni kişi ekleme sayfasını açar.*/
    @FXML
    public void ekle(){
//        System.out.println("Ekle çalışıyor");
        try{
            Parent kisiEkleParent =
                    FXMLLoader.load(getClass().getResource("/fxml/kisiEkle-2.fxml"));
            Scene scene = new Scene(kisiEkleParent,600,300);
            Stage ekleStage = new Stage();
            ekleStage.initOwner(anasayfaVbox.getScene().getWindow());
            ekleStage.setScene(scene);
            ekleStage.initModality(Modality.APPLICATION_MODAL);
            ekleStage.showAndWait();

        } catch (IOException e) {
            System.out.println("FXML Yüklenemedi");
            e.printStackTrace();
        }

    }
    /**Anasayfa soldaki menüyü task olarak yükler.*/
    public void listInformation(){

        Task<ObservableList<Kisi>> task = new GetAllPeopleTask();

        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                kisiAd.setCellValueFactory(cellData -> cellData.getValue().adProperty());
                kisiSoyad.setCellValueFactory(cellData -> cellData.getValue().soyadProperty());
                kisiTelefon.setCellValueFactory(cellData -> cellData.getValue().telefonProperty());
                kisiBirimAdi.setCellValueFactory(cellData -> cellData.getValue().epostaProperty());
                anaEkrankisiler = task.getValue();
                kisilerTablosu.setItems(anaEkrankisiler);
                System.out.println("ANA EKRAN KİSİLER --- "+anaEkrankisiler.toString());
                try{
                    Thread.sleep(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        uprogressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
    /**kişinin ayrıntılı bilgilerini gösteren alanları temizler*/
    private void clearDetailedFields(){
        adTextField.clear();
        ad2TextField.clear();
        soyadTextField.clear();
        isTelTextField.clear();
        cepTelTextField.clear();
        epostaTextField.clear();
    }

    /**Anasayfa soldaki listeyi yeniler*/
    public void refreshTable(){
        this.listInformation();
        kisilerTablosu.refresh();
    }


    /**Anasayfa soldaki listeyi doldurmak için gerekli verileri sorgular.*/
    class GetAllPeopleTask extends Task{
        @Override
        protected ObservableList call() throws Exception {
//            System.out.println("Task çalıştı");

            return FXCollections.observableArrayList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC));
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            System.out.println("Task başarılı");


//            kisilerTablosu.setItems(FXCollections.observableArrayList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC)));
//            kisilerTablosu.refresh();
        }
    }



}
