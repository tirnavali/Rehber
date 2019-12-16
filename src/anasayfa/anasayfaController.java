package anasayfa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    TableView<Kisi> kisilerTablosu;
    @FXML
    TableColumn<Kisi, String> kisiAd, kisiSoyad, kisiTelefon, kisiBirimAdi;

    @FXML
    private ImageView fotograf ;

    @FXML
    private Button button1;

    public void initialize(){
        Image foto = new Image(getClass().getResourceAsStream("/resources/images/person.png"));
        fotograf.setImage(foto);

        //this.ListInformation();
        ObservableList<Kisi> mylist = FXCollections.observableList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC));

        kisiAd.setCellValueFactory(cellData -> cellData.getValue().adProperty());
        kisiSoyad.setCellValueFactory(cellData -> cellData.getValue().soyadProperty());
        kisiTelefon.setCellValueFactory(cellData -> cellData.getValue().telefonProperty());
        kisiBirimAdi.setCellValueFactory(cellData -> cellData.getValue().epostaProperty());

        kisilerTablosu.setItems(mylist);

    }
    public void ListInformation(){
        System.out.println("list information methodu çalıştı");
        Task<ObservableList<Kisi>> task = new GetAllPeopleTask();
        new Thread(task).start();

        kisilerTablosu.setItems(task.getValue());
       kisiAd.setCellValueFactory(new PropertyValueFactory<>("ad"));





        System.out.println("list information methodu bitti");
    }

    class GetAllPeopleTask extends Task{
        @Override
        protected ObservableList call() throws Exception {
            return FXCollections.observableArrayList(DataModel.getInstance().getAllKisiler(DataModel.ORDER_BY_ASC));
        }
    }



}
