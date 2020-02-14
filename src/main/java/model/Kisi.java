package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** Kişi sınıfı.
 ** Veri tabanına yazılmak üzere hazırlanmış olan entitiy (varlık). ***/
public class Kisi {
    private SimpleIntegerProperty id;
    private SimpleStringProperty ad;
    private SimpleStringProperty soyad;
    private SimpleStringProperty ad_2;
    private SimpleStringProperty telefon;
    private SimpleStringProperty eposta;
    private SimpleStringProperty cepTelefon;
    private SimpleStringProperty fotograf;

    public Kisi() {
        this.id = new SimpleIntegerProperty();
        this.ad = new SimpleStringProperty();
        this.soyad = new SimpleStringProperty();
        this.ad_2 = new SimpleStringProperty();
        this.telefon = new SimpleStringProperty();
        this.eposta = new SimpleStringProperty();
        this.cepTelefon = new SimpleStringProperty();
        this.fotograf = new SimpleStringProperty();
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

    public String getSoyad() {
        return soyad.get();
    }

    public SimpleStringProperty soyadProperty() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad.set(soyad);
    }

    public String getAd_2() {
        return ad_2.get();
    }

    public SimpleStringProperty ad_2Property() {
        return ad_2;
    }

    public void setAd_2(String ad_2) {
        this.ad_2.set(ad_2);
    }

    public String getTelefon() {
        return telefon.get();
    }

    public SimpleStringProperty telefonProperty() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon.set(telefon);
    }

    public String getEposta() {
        return eposta.get();
    }

    public SimpleStringProperty epostaProperty() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta.set(eposta);
    }

    public String getCepTelefon() {
        return cepTelefon.get();
    }

    public SimpleStringProperty cepTelefonProperty() {
        return cepTelefon;
    }

    public void setCepTelefon(String cepTelefon) {
        this.cepTelefon.set(cepTelefon);
    }

    public String getFotograf() {
        return fotograf.get();
    }

    public SimpleStringProperty fotografProperty() {
        return fotograf;
    }

    public void setFotograf(String fotograf) {
        this.fotograf.set(fotograf);
    }

    @Override
    public String toString() {
        return "Kisi{" +
                "id=" + id +
                ", ad=" + ad +
                ", soyad=" + soyad +
                ", ad_2=" + ad_2 +
                ", telefon=" + telefon +
                ", eposta=" + eposta +
                ", cepTelefon=" + cepTelefon +
                ", fotograf=" + fotograf +
                '}';
    }
}
