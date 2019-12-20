package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DataModel;
import model.Kisi;

import java.io.IOException;


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

    public void initialize(){
        System.out.println("Controller initialize methodu çalıştı...");
        uprogressBar.setProgress(1);
        Image foto = new Image(getClass().getResourceAsStream("/images/person.png"));
        fotograf.setImage(foto);


        //this.ListInformation();
        //ObservableList<Kisi> mylist = FXCollections.observableList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC));



//        kisiAd.setCellValueFactory(cellData -> cellData.getValue().adProperty());
//        kisiSoyad.setCellValueFactory(cellData -> cellData.getValue().soyadProperty());
//        kisiTelefon.setCellValueFactory(cellData -> cellData.getValue().telefonProperty());
//        kisiBirimAdi.setCellValueFactory(cellData -> cellData.getValue().epostaProperty());

        //kisilerTablosu.setItems(anaEkrankisiler);
        System.out.println("ANA ekrana kisiler ekleniyor....");




    }

    public void ekle(){
        System.out.println("Ekle çalışıyor");
        try{
            Parent kisiEkleParent =
                    FXMLLoader.load(getClass().getResource("/fxml/kisiEkle-2.fxml"));
            Scene scene = new Scene(kisiEkleParent,600,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

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
                    Thread.sleep(4000);
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
            return FXCollections.observableArrayList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC));
        }
    }



}
