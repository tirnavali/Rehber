package anasayfa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.DataModel;
import model.Kisi;

import java.util.ArrayList;
import java.util.List;


public class anasayfaController {
    private ObservableList<Kisi> anaEkrankisiler = null;
    @FXML
    TableView<Kisi> kisilerTablosu;
    @FXML
    TableColumn<Kisi, String> kisiAd, kisiSoyad, kisiTelefon, kisiBirimAdi;

    @FXML
    private ImageView fotograf ;

    @FXML
    private Button button1;

    @FXML
    ProgressBar uprogressBar;

    public void initialize(){
        System.out.println("Controller initialize methodu çalıştı...");
        uprogressBar.setProgress(1);
        Image foto = new Image(getClass().getResourceAsStream("/resources/images/person.png"));
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
