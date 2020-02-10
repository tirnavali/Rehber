package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** Birim sınıfı.
 ** Veri tabanına yazılmak üzere hazırlanmış olan entitiy (varlık). ***/

public class Birim {
    private SimpleIntegerProperty id;
    private SimpleStringProperty ad;

    public Birim(){
        this.id = new SimpleIntegerProperty();
        this.ad = new SimpleStringProperty();
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

    public String getAd() {
        return ad.get();
    }

    public SimpleStringProperty adProperty() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad.set(ad);
    }

    @Override
    public String toString() {
        return id.get()+" - "+ad.get();
    }
}
