package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Birim;
import model.DataModel;
import model.Kisi;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/anasayfa.fxml"));
        primaryStage.setTitle("Telefon rehberi By Tırnavalı V. 1.00");
        primaryStage.setScene(new Scene(root, 740, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("Çalıştı");
        DataModel dataModel = DataModel.getInstance();
        dataModel.openConn();
//        dataModel.insertKisiXBirim("Ragıp","Hüseyinciler","","4456","5547895265","rgphsüyn@gmail.com","",
//                "Danışma","22-10-2001","");
//        dataModel.insertKisiXBirim("Akif","Yücedal","","2211","5341231212","akf@gmail.com","",
//                "Tutanak Dokümantasyon","22-10-2004","");
//        dataModel.insertKisiXBirim("Can","Tanol","","4457","5447885050","cantan@gmail.com","",
//                "Dergi Dokümantasyon","22-10-2010","");
//        dataModel.insertKisiXBirim("Faruk","Çamlıbel","","1111","5558455698","ahmtcandr@gmail.com","",
//                "Süreli Yayınlar","22-10-2001","");
//
////        dataModel.insertBirim("Süreli Yayınlar");
////        dataModel.insertBirim("İdari Büro");
////        dataModel.insertBirim("Danışma");
////        dataModel.insertBirim("Kataloglama");
////        dataModel.insertBirim("Dergi Dokümantasyon");
////        dataModel.insertBirim("Tutanak Dokümantasyon");
//
        for(Birim b : dataModel.getAllBirimler(1)){
            System.out.println(b.toString());
        }
//
        List<Kisi> kisiler =  dataModel.getAllKisiler(3);
        for(Kisi k : kisiler) {
            System.out.println(k.toString());
        }
    }

    @Override
    public void stop() throws Exception {
        DataModel.getInstance().closeConn();
        super.stop();
    }
}
