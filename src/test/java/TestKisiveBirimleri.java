import model.DataModel;
import model.KisininBirimleri;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class TestKisiveBirimleri {

    public static void main(String[] args) {
        KisininBirimleri kb = new KisininBirimleri();
        kb.setId(4);
        kb.setBirim_adi("Uzay yolu");
        kb.setBaslama_tar("2009-09-09");
        kb.setBitis_tar("2010-10-10");

        DataModel dataModel = DataModel.getInstance();
        dataModel.openConn();
        ArrayList<KisininBirimleri> birimler = DataModel.getInstance().findKisiBirimler(6);
        System.out.println("birimler.size() :"+birimler.size());
        System.out.println(birimler.toString());
        kb.toString();
    }
}
