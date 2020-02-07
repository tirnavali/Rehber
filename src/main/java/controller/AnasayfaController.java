package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
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


public class AnasayfaController {
    private ObservableList<Kisi> anaEkrankisiler = null;
    @FXML
    TableView<Kisi> kisilerTablosu;
    @FXML
    TableColumn<Kisi, String> kisiAd, kisiSoyad, kisiTelefon, kisiBirimAdi;

    @FXML
    private ImageView fotograf ;

    @FXML
    private Button button1, ekle;

    @FXML
    ProgressBar uprogressBar;
    @FXML
    VBox anasayfaVbox;

    @FXML
    public void initialize(){
        System.out.println("Controller initialize methodu çalıştı...");
        uprogressBar.setProgress(1);
        Image foto = new Image(getClass().getResourceAsStream("/images/person.png"));
        fotograf.setImage(foto);


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
                                            System.out.println("Kişi "+kisi);
                                        }
                                    }
                                }
                        );
                        return row;
                    }
                }
        );



        //this.ListInformation();
        //ObservableList<Kisi> mylist = FXCollections.observableList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC));



//        kisiAd.setCellValueFactory(cellData -> cellData.getValue().adProperty());
//        kisiSoyad.setCellValueFactory(cellData -> cellData.getValue().soyadProperty());
//        kisiTelefon.setCellValueFactory(cellData -> cellData.getValue().telefonProperty());
//        kisiBirimAdi.setCellValueFactory(cellData -> cellData.getValue().epostaProperty());

        //kisilerTablosu.setItems(anaEkrankisiler);

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

            }
        });
        uprogressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();




    }
    public void detayGoster(){
        Kisi secilen =  kisilerTablosu.getSelectionModel().getSelectedItem();
        System.out.println("Detaylar gösteriliyor.."+secilen.toString());
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

    public void ekle(){
        System.out.println("Ekle çalışıyor");
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
    public void listInformation(){
        System.out.println("list information methodu çalıştı...");


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

    public void refreshTable(){
        System.out.println("çalışıyor refresher");
        this.listInformation();
        kisilerTablosu.refresh();
    }



    class GetAllPeopleTask extends Task{
        @Override
        protected ObservableList call() throws Exception {
            System.out.println("Task çalıştı");

            return FXCollections.observableArrayList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC));
        }
    }



}
