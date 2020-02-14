package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** Kişinin birimleri sınıfı.
 ** Veri tabanına yazılmak üzere hazırlanmış olan entitiy (varlık). ***/
public class KisininBirimleri {
    private SimpleIntegerProperty id;
    private SimpleStringProperty birim_adi;
    private SimpleStringProperty baslama_tar;
    private SimpleStringProperty bitis_tar;

    public KisininBirimleri() {
        this.id = new SimpleIntegerProperty();
        this.birim_adi = new SimpleStringProperty();
        this.baslama_tar = new SimpleStringProperty() ;
        this.bitis_tar = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getBirim_adi() {
        return birim_adi.get();
    }

    public SimpleStringProperty birim_adiProperty() {
        return birim_adi;
    }

    public void setBirim_adi(String birim_adi) {
        this.birim_adi.set(birim_adi);
    }

    public String getBaslama_tar() {
        return baslama_tar.get();
    }

    public SimpleStringProperty baslama_tarProperty() {
        return baslama_tar;
    }

    public void setBaslama_tar(String baslama_tar) {
        this.baslama_tar.set(baslama_tar);
    }

    public String getBitis_tar() {
        return bitis_tar.get();
    }

    public SimpleStringProperty bitis_tarProperty() {
        return bitis_tar;
    }

    public void setBitis_tar(String bitis_tar) {
        this.bitis_tar.set(bitis_tar);
    }

    @Override
    public String toString() {
        return "KisininBirimleri{" +
                "id=" + id +
                ", birim_adi=" + birim_adi +
                ", baslama_tar=" + baslama_tar +
                ", bitis_tar=" + bitis_tar +
                '}';
    }
}
