package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteConfig;


public class DataModel {
    public static final String DB_NAME= "rehber.db";
    public static final String DB_PATH ="jdbc:sqlite:C:\\Users\\62542\\IdeaProjects\\Rehber\\src\\";
    public static final String DB_FULL_PATH = DB_PATH+DB_NAME;
    public static final String COLLATE_NOCASE = " COLLATE NOCASE";
    public static final int    ORDER_BY_ASC =1;
    public static final int    ORDER_BY_DESC=2;
    public static final int    ORDER_BY_NONE=3;

    public static final String TABLE_KISILER = "kisler";
    public static final String COLUMN_KISILER_ID = "id";
    public static final String COLUMN_KISILER_AD = "ad";
    public static final String COLUMN_KISILER_AD2 = "ad_2";
    public static final String COLUMN_KISILER_SOYAD = "soyad";
    public static final String COLUMN_KISILER_TELEFON = "telefon";
    public static final String COLUMN_KISILER_CEP_TEL = "cepTelefon";
    public static final String COLUMN_KISILER_EPOSTA = "eposta";
    public static final String COLUMN_KISILER_FOTO = "fotograf";
    public static final String FIND_KISI = "SELECT * FROM "+TABLE_KISILER+" WHERE "+COLUMN_KISILER_ID+" = ?";

    public static final String SELECT_KISILER="SELECT * FROM "+TABLE_KISILER;
    public static final String QUERY_KISI="SELECT * FROM "+TABLE_KISILER+" WHERE "+COLUMN_KISILER_AD+" = ? AND "+COLUMN_KISILER_SOYAD+" = ?"+COLLATE_NOCASE;
    public static final String INSERT_KISI="INSERT INTO "+TABLE_KISILER+" ( "+COLUMN_KISILER_AD+","+COLUMN_KISILER_SOYAD+","+COLUMN_KISILER_AD2+","+COLUMN_KISILER_TELEFON+
            ","+COLUMN_KISILER_CEP_TEL+","+COLUMN_KISILER_EPOSTA+","+COLUMN_KISILER_FOTO+" ) VALUES (?,?,?,?,?,?,?)";


    public static final String TABLE_BIRIMLER = "birimler";
    public static final String COLUMN_BIRIM_ID = "id";
    public static final String COLUMN_BIRIM_AD = "birim_adi";
    public static final String FIND_BIRIM = "SELECT * FROM "+TABLE_BIRIMLER+" WHERE "+COLUMN_BIRIM_ID+" = ?";
    public static final String QUERY_BIRIM = "SELECT * FROM "+TABLE_BIRIMLER+" WHERE "+COLUMN_BIRIM_AD+" = ? "+COLLATE_NOCASE;
    public static final String INSERT_BIRIM = "INSERT INTO "+TABLE_BIRIMLER+" ( "+COLUMN_BIRIM_AD+" ) VALUES (?)";
    public static final String DELETE_BIRIM = "DELETE FROM "+TABLE_BIRIMLER+" WHERE "+COLUMN_BIRIM_ID+" = ?";

    public static final String TABLE_KISI_X_BIRIM = "kisi_birim_calisir";
    public static final String COLUMN_KISI_X_BIRIM_ID= "id";
    public static final String COLUMN_KISI_X_BIRIM_KISI_ID="kisi_id";
    public static final String COLUMN_KISI_X_BIRIM_BIRIM_ID="birim_id";
    public static final String COLUMN_KISI_X_BIRIM_GOREV_BASLAMA_TARIHI="gorev_baslangic_tarihi";
    public static final String COLUMN_KISI_X_BIRIM__GORER_BITIS_TARIHI="gorev_bitis_tarihi";
    /**select kisi_birim_calisir.id, birim_adi, gorev_baslangic_tarihi, gorev_bitis_tarihi from kisi_birim_calisir left join birimler on birimler.id = kisi_birim_calisir.birim_id*/
    public static final String FIND_KISI_X_BIRIM = "SELECT "+ TABLE_KISI_X_BIRIM+"."+COLUMN_KISI_X_BIRIM_ID+"," + COLUMN_BIRIM_AD +","+ COLUMN_KISI_X_BIRIM_GOREV_BASLAMA_TARIHI+","+
            COLUMN_KISI_X_BIRIM__GORER_BITIS_TARIHI+" FROM "+TABLE_KISI_X_BIRIM+"LEFT JOIN "+TABLE_BIRIMLER+" ON "+TABLE_BIRIMLER+"."+COLUMN_BIRIM_ID+" = "+
            TABLE_KISI_X_BIRIM+"."+COLUMN_KISI_X_BIRIM_BIRIM_ID+ " WHERE "+COLUMN_KISI_X_BIRIM_BIRIM_ID+" = ?";
    public static final String QUERY_KISI_X_BIRIM = "SELECT * FROM "+TABLE_KISI_X_BIRIM+" WHERE "+COLUMN_KISI_X_BIRIM_BIRIM_ID+" = ?  AND "+COLUMN_KISI_X_BIRIM_KISI_ID+" = ? AND "+COLUMN_KISI_X_BIRIM_GOREV_BASLAMA_TARIHI+" = ?";
    public static final String INSERT_KISI_X_BIRIM="INSERT INTO "+TABLE_KISI_X_BIRIM+" ( "+COLUMN_KISI_X_BIRIM_KISI_ID+","+COLUMN_KISI_X_BIRIM_BIRIM_ID+","+
            COLUMN_KISI_X_BIRIM_GOREV_BASLAMA_TARIHI+","+COLUMN_KISI_X_BIRIM__GORER_BITIS_TARIHI+" ) VALUES (?,?,?,?)";

//    public static final String FIND_KISI_BIRIMLER =  "SELECT "+ TABLE_KISI_X_BIRIM+"."+COLUMN_KISI_X_BIRIM_ID+", " + COLUMN_BIRIM_AD +", "+ COLUMN_KISI_X_BIRIM_GOREV_BASLAMA_TARIHI+","+
//            COLUMN_KISI_X_BIRIM__GORER_BITIS_TARIHI+" FROM "+TABLE_KISI_X_BIRIM+" LEFT JOIN "+TABLE_BIRIMLER+" ON "+TABLE_BIRIMLER+"."+COLUMN_BIRIM_ID+" = "+
//            TABLE_KISI_X_BIRIM+"."+COLUMN_KISI_X_BIRIM_BIRIM_ID+ " WHERE "+TABLE_KISI_X_BIRIM+"."+COLUMN_KISI_X_BIRIM_KISI_ID+" = ?";
    public static final String FIND_KISI_BIRIMLER = "select kisi_birim_calisir.id, birim_adi, gorev_baslangic_tarihi, gorev_bitis_tarihi from kisi_birim_calisir left join birimler "
    +"on birimler.id = kisi_birim_calisir.birim_id where kisi_birim_calisir.kisi_id = ? ";



    private Connection conn;

    private PreparedStatement selectAllKisiler;
    private PreparedStatement findKisi;
    private PreparedStatement findBirim;
    private PreparedStatement findKisiXBirim;
    private PreparedStatement findKisiBirimler;
    private PreparedStatement queryKisi;
    private PreparedStatement queryBirim;
    private PreparedStatement queryKisiXBirim;
    private PreparedStatement insertKisiXBirim;
    private PreparedStatement insertBirim;
    private PreparedStatement deleteBirim;
    private PreparedStatement insertKisi;

    private static DataModel dataModel = new DataModel();

    private DataModel(){
    }

    /**Veritabanı nesnesinin biricik örneğini döndüren method.*/
    public static DataModel getInstance(){
        return dataModel;
    }

    /** Veritabanı bağlantısını başlat.*/
    public boolean openConn(){

        try{
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn             = DriverManager.getConnection(DB_FULL_PATH,config.toProperties());
            findKisi         = conn.prepareStatement(FIND_KISI);
            findKisiBirimler = conn.prepareStatement(FIND_KISI_BIRIMLER);
            queryKisi        = conn.prepareStatement(QUERY_KISI);
            queryBirim       = conn.prepareStatement(QUERY_BIRIM);
            queryKisiXBirim  = conn.prepareStatement(QUERY_KISI_X_BIRIM);
            insertBirim      = conn.prepareStatement(INSERT_BIRIM, Statement.RETURN_GENERATED_KEYS);
            insertKisi       = conn.prepareStatement(INSERT_KISI,Statement.RETURN_GENERATED_KEYS);
            insertKisiXBirim = conn.prepareStatement(INSERT_KISI_X_BIRIM, Statement.RETURN_GENERATED_KEYS);
            deleteBirim      = conn.prepareStatement(DELETE_BIRIM, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Bağlantı kuruldu -> " +getClass().getName());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Bağlantı yapılamadı -> "+getClass().getName());
            return false;
        }
    }

    /**Veri tabanında bulunan hazır tüm bağlantıları kapat.*/
    public void closeConn(){
        try{
            if(insertKisiXBirim != null ) {
                insertKisiXBirim.close();
            }
            if(insertBirim != null) {
                insertBirim.close();
            }
            if(insertKisi != null){
                insertKisi.close();
            }
            if(selectAllKisiler != null) {
                selectAllKisiler.close();
            }
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanı kapatılamadı");
        }
    }

    public int deleteBirim(Birim birim){
        try{
            deleteBirim.setInt(1, birim.getId());
            int sonuc =  deleteBirim.executeUpdate();
            return  sonuc;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /** Kişi id'si ile tüm ait olunan birimleri bul */
    public ArrayList<KisininBirimleri> findKisiBirimler(int kisiId){
        System.out.println("DATAMODELİM KİSİ İD' ALDIM : "+kisiId);
        ArrayList<KisininBirimleri> kisiyeAitBirimler = new ArrayList<>();
        KisininBirimleri kb;
        try{
            kb = new KisininBirimleri();
            findKisiBirimler.setInt(1, kisiId);
            ResultSet rs = findKisiBirimler.executeQuery();
            while(rs.next()){
                System.out.println("Kayıt bilgisi var");
                kb.setId(rs.getInt(COLUMN_KISI_X_BIRIM_ID));
                kb.setBirim_adi(rs.getString(COLUMN_BIRIM_AD));
                kb.setBaslama_tar(rs.getString(COLUMN_KISI_X_BIRIM_GOREV_BASLAMA_TARIHI));
                kb.setBitis_tar(rs.getString(COLUMN_KISI_X_BIRIM__GORER_BITIS_TARIHI));
                System.out.println("Yazılmadan önce kişiyeait bilgiler : "+kb.toString());
                kisiyeAitBirimler.add(kb);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kişinin birimleri bulunamıyor!");
            kisiyeAitBirimler = null;
        } catch(NullPointerException n) {
            n.printStackTrace();

        }
        return kisiyeAitBirimler;
    }

    /** Kişi id' ile kişi nesnesini bul*/
     public Kisi findKisi(int kisiId) {
        Kisi kisi;
        if (kisiId <=0){
            System.out.println("Boş kişi döndürülüyor");
            return kisi = null;

        } else {
            try {
                kisi = new Kisi();
                findKisi.setInt(1,kisiId);
                ResultSet  resSet = findKisi.executeQuery();
                resSet.next();
                kisi.setId(resSet.getInt(COLUMN_KISILER_ID)); //id
                kisi.setAd(resSet.getString(COLUMN_KISILER_AD)); //ad
                kisi.setAd_2(resSet.getString(COLUMN_KISILER_AD2));//ad2
                kisi.setSoyad(resSet.getString(COLUMN_KISILER_SOYAD));//soyad
                kisi.setCepTelefon(resSet.getString(COLUMN_KISILER_CEP_TEL));//ceptel
                kisi.setTelefon(resSet.getString(COLUMN_KISILER_TELEFON));//iştel
                kisi.setEposta(resSet.getString(COLUMN_KISILER_EPOSTA));//eposta
                kisi.setFotograf(resSet.getString(COLUMN_KISILER_FOTO));//foto
                return kisi;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Kişi bulunamıyor! ");
                return kisi = null;
            }
        }

    }

    public void insertKisiXBirim(String ad, String soyad, String ad2, String telefon, String cepTelefon,
                                 String eposta, String fotograf, String birimAdi, String gorevBas, String gorevBit){
        try{
            conn.setAutoCommit(false);
            int kisiId = insertKisi(ad,soyad,ad2,telefon,cepTelefon,eposta,fotograf);
            int birimId = insertBirim(birimAdi);
            insertKisiXBirim.setInt(1,kisiId);
            insertKisiXBirim.setInt(2,birimId);
            insertKisiXBirim.setString(3, gorevBas);
            insertKisiXBirim.setString(4,gorevBit);
            //Aynı kişi aynı birimde aynı zamanda işe başlayamaz. Onun kontrollerini yapıyoruz.
            queryKisiXBirim.setInt(1,birimId);
            queryKisiXBirim.setInt(2,kisiId);
            queryKisiXBirim.setString(3,gorevBas);
            ResultSet resultSet = queryKisiXBirim.executeQuery();
            if (resultSet.next()) {
                System.out.println("Böyle bir birim kişi ilişkisi veri tabanında var");
                throw new Exception("Veri yazılmıyor..") ;
            }

            int affectedRows = insertKisiXBirim.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                throw new SQLException("TÜM VERİ GİRİŞİ YAPILAMADI");
            }


        } catch (Exception e) {
            System.out.println("Insert kişi exception" + e.getMessage());
            try {
                System.out.println("Rollback yapılıyor");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("ROLLBACK 2 has runned");
            }
        } finally {
            try{
                System.out.println("Resetting default commit behaviour");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit behaviour ");
            }
        }
    }


    private int insertKisi (String ad, String soyad, String ad2, String telefon, String ceptelefon, String ePosta, String fotograf) throws SQLException {
        queryKisi.setString(1,ad);
        queryKisi.setString(2, soyad);
        ResultSet resultSet = queryKisi.executeQuery();
        if(resultSet.next()) {
            System.out.println("Böyle bir kayıt veri tabanında var.");
            return resultSet.getInt(1);

        } else {
            insertKisi.setString(1,ad);
            insertKisi.setString(2,soyad);
            insertKisi.setString(3,ad2);
            insertKisi.setString(4,telefon);
            insertKisi.setString(5,ceptelefon);
            insertKisi.setString(6,ePosta);
            insertKisi.setString(7,fotograf);

            int affectedRows = insertKisi.executeUpdate();

            if(affectedRows !=1) {
                throw new SQLException("Veri yazılamadı -- insertKisi");
            }
            ResultSet generatedKeys = insertKisi.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Kisi id alınamadı");
            }
        }
    }


    public int insertBirim(String birim_adi) throws SQLException{
        queryBirim.setString(1,birim_adi);
        ResultSet resultSet = queryBirim.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        } else {
            //insert into birimler
            insertBirim.setString(1,birim_adi);
            int afffectedRows = insertBirim.executeUpdate();

            if (afffectedRows != 1) {
                throw new SQLException("Birim yazılamadı");
            }
            ResultSet generatedKeys = insertBirim.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Birim id alınamadı");
            }
        }
    }

//    public int insertKisi(String ad, String soyad, String ad2, String telefon, String cepTel, String eposta, String foto) throws SQLException{
//        insertKisi.setString(1,ad);insertKisi.setString(2,soyad);
//
//
//    }

    public List<Birim> getAllBirimler(int orderStyle){
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_BIRIMLER);
        if(orderStyle != ORDER_BY_NONE) {
            if(orderStyle == ORDER_BY_ASC) {
                sb.append(" ORDER BY ");
                sb.append(COLUMN_BIRIM_AD);
                sb.append(" ASC");
            } else {
                sb.append(" ORDER BY ");
                sb.append(COLUMN_BIRIM_AD);
                sb.append(" DESC");
            }
        }
        try(Statement statement = conn.createStatement();
            ResultSet set       = statement.executeQuery(sb.toString())) {
            List<Birim> birimler = new ArrayList<>();
            while (set.next()){
                Birim birim = new Birim();
                birim.setId(set.getInt(COLUMN_BIRIM_ID));
                birim.setAd(set.getString(COLUMN_BIRIM_AD));
                birimler.add(birim);
            }
            return birimler;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Kisi> getAllKisiler(int orderType){
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_KISILER);
        if(orderType != ORDER_BY_NONE) {
            if(orderType == ORDER_BY_ASC) {
                sb.append(" ORDER BY ");
                sb.append(COLUMN_KISILER_AD);
                sb.append(" ASC");
            }else{
                sb.append(" ORDER BY ");
                sb.append(COLUMN_KISILER_AD);
                sb.append(" DESC");
            }
            System.out.println("LİSTE --- "+sb.toString());
        }

        try(Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery(sb.toString());
            List<Kisi> kisiler = new ArrayList<>();
            while (resultSet.next()) {
                Kisi kisi = new Kisi();
                kisi.setId(resultSet.getInt(COLUMN_KISILER_ID));
                kisi.setAd(resultSet.getString(COLUMN_KISILER_AD));
                //kisi.setAd_2(resultSet.getString(COLUMN_KISILER_AD2));
                kisi.setSoyad(resultSet.getString(COLUMN_KISILER_SOYAD));
                kisi.setTelefon(resultSet.getString(COLUMN_KISILER_TELEFON));
                //kisi.setCepTelefon(resultSet.getString(COLUMN_KISILER_CEP_TEL));
                kisi.setEposta(resultSet.getString(COLUMN_KISILER_EPOSTA));
                //kisi.setFotograf(resultSet.getString(COLUMN_KISILER_FOTO));
                kisiler.add(kisi);
            }
            System.out.println("LİSTE ----- "+kisiler.toString());
            return kisiler;

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kisiler sorgusunda bir hata oluştu");
            return null;
        }

    }
}
