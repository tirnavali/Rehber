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
        String output = ""+id.get();
        if (output.length() == 1) {
            output = "00".concat(output);
        } else if (output.length() == 2) {
            output = "0".concat(output);
        }
        return output+" - "+ad.get();
    }
}
