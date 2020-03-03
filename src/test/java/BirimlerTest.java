import model.Birim;
import model.DataModel;

import java.util.List;

public class BirimlerTest {
    public static void main(String[] args) {
        DataModel dataModel = DataModel.getInstance();
        dataModel.openConn();

        List<Birim> birimler = DataModel.getInstance().getAllBirimler(1);

       for (Birim birim : birimler) {
           System.out.println(birim);
       }

       dataModel.closeConn();
    }
}
