package com.mehmetyildirim.yedekproje;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class VeriTabani extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_ktp3";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_NAME = "tbl_yazar";
    private static final String Yazar="Yazar";
    private static final String Biyografi ="Biyografi";
    private static final String COLUMN_IMAGE = "image_data";
    private static final String TABLE_NAME2 = "tbl_kitap";
    private static final String Kitapadi="Kitapadi";
    private static final String Yazaradi="Yazaradi";
    private static final String Yayinevi="Yayinevi";
    private static final String Sayfasayisi ="Sayfasayisi";
    private  static final String Kategori="Kategori";
    private static final String TABLE_NAME5="tbl_kullanici";
    private static final String Kullaniciad="Kullaniciad";
    private static final String Kullaniciparola="Kullaniciparola";









    SQLiteDatabase sqLiteDatabase;
    public VeriTabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+ Yazar +" TEXT, "+ Biyografi +" TEXT,"+COLUMN_IMAGE+" BLOB)");
        db.execSQL("CREATE TABLE " + TABLE_NAME2 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + Kitapadi + " TEXT, " + Yazaradi + " TEXT, " + Yayinevi + " TEXT, " + Sayfasayisi + " TEXT, " + Kategori +" TEXT )");
      //  db.execSQL("CREATE TABLE "+ TABLE_NAME3 +"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+ Yazar +" TEXT, "+ Biyografi +" TEXT,"+COLUMN_IMAGE+" BLOB)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME5 + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+ Kullaniciad +" TEXT," + Kullaniciparola +" TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);

        onCreate(db);
    }
    public  boolean kekle(String kullaniciad,String kullaniciparola){
        try {
            SQLiteDatabase db6=this.getWritableDatabase();
            ContentValues cv3=new ContentValues();
            cv3.put(Kullaniciad, kullaniciad.trim());
            cv3.put(Kullaniciparola, kullaniciparola.trim());
            db6.insert(TABLE_NAME5, null, cv3);
            return true;
        }
        catch (Exception e)
        {
            System.out.println("kullanıcıad:"+e.toString());
        }

        return false;
    }
    public boolean kullaniciGirisi(String kullaniciad, String kullaniciparola) {
        SQLiteDatabase db = this.getReadableDatabase();


        String[] columns = {"*"};
        String selection = "Kullaniciad = ? AND Kullaniciparola = ?";
        String[] selectionArgs = {kullaniciad, kullaniciparola};
        Cursor cursor = db.query(TABLE_NAME5, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();


        return count > 0;
    }


    public Boolean kitapEkle(String kitapadi, String yazaradi,String yayinevi,String sayfasayisi,String kategori) {
        try {
            SQLiteDatabase db1 = this.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put(Kitapadi, kitapadi.trim());
            cv1.put(Yazaradi, yazaradi.trim());
            cv1.put(Yayinevi, yayinevi.trim());
            cv1.put(Sayfasayisi, sayfasayisi.trim());
            cv1.put(Kategori, kategori.trim());

            db1.insert(TABLE_NAME2, null, cv1);

            return true;
        }
        catch (Exception e)
        {
            System.out.println("kitap hata:"+e.toString());

        }

        return null;
    }

    public Boolean yazarEkle(String yazar,String biyografi,byte[] imageData) {
        try {
            SQLiteDatabase db5 = this.getWritableDatabase();
            ContentValues cv5 = new ContentValues();
            cv5.put(COLUMN_IMAGE, imageData);
            cv5.put(Yazar, yazar.trim());

            cv5.put(Biyografi, biyografi.trim());

            db5.insert(TABLE_NAME, null, cv5);

            return true;
        }
        catch (Exception e)
        {
            System.out.println("kitap hata:"+e.toString());

        }

        return null;
    }
    public byte[] getImageData() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {COLUMN_IMAGE};
            Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // Gereksiz sütunu almaz, null döndürür
                // Sütunlarınıza uygun olarak gerekli veriyi alın
                int imageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
                byte[] imageData = cursor.getBlob(imageIndex);
                Log.d("Sütun Adı", "COLUMN_IMAGE: " + COLUMN_IMAGE);
                cursor.close();
                return null;
            }
        } catch (Exception e) {
            Log.e("VeriTabani", "Resim alma hatası: " + e.toString());
        }
        return null;
    }
    public List<String> Yazarlistele() {
        List<String> yazarlar = new ArrayList<>();
        SQLiteDatabase db5 = this.getWritableDatabase();
        String[] sutunlar = {Yazar, Biyografi, COLUMN_IMAGE};
        Cursor cr = db5.query(TABLE_NAME, sutunlar, null, null, null, null, null);
        while (cr.moveToNext()) {

            String yazar = cr.getString(0);
            String biyografi = cr.getString(1);

            byte[] yazarFoto = cr.getBlob(2);

            String yazarBilgisi = yazar + "-" + biyografi+"-"+yazarFoto;

            yazarlar.add(yazarBilgisi);
        }
        return yazarlar;
    }
    public Boolean Yazarsil(String yazar){
        try {
            SQLiteDatabase DB1 = this.getWritableDatabase();
            ContentValues cv4 = new ContentValues();
            cv4.put(Yazar, yazar.trim());
            String selection = yazar + " = ?";
            String[] selectionArgs = {yazar};
            int silsatir = DB1.delete(TABLE_NAME, selection, selectionArgs);
            if (silsatir > 0) {
                Log.i("tag", "yazar silindi");
                return true;
            } else {
                Log.i("tag", "yazar silinmedi");
                return false;
            }
        }
        catch (Exception e)
        {
            Log.e("tag", "Yazar silme işlemi sırasında hata oluştu: " + e.toString());
            return false;
        }

    }
    public List<String> Yazarara(String yazar) {
        List<String> aramaSonucu = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Yazar, Biyografi, COLUMN_IMAGE};
        String selection = Yazar + " LIKE ?";
        String[] selectionArgs = {"%" + yazar + "%"};
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Yazar adını al
                    int yazarIndex = cursor.getColumnIndex(Yazar);
                    String yazarAdi = cursor.getString(yazarIndex);
                    // Biyografiyi al
                    int biyografiIndex = cursor.getColumnIndex(Biyografi);
                    String biyografi = cursor.getString(biyografiIndex);
                    // Resmi al
                    int imageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
                    byte[] imageData = cursor.getBlob(imageIndex);

                    // Yazar adını, biyografiyi ve resmi kullanarak istediğiniz işlemi yapın
                    // Örneğin, listeye ekleyebilir veya başka bir işlem yapabilirsiniz

                    aramaSonucu.add(yazarAdi);
                    aramaSonucu.add(biyografi);
                } while (cursor.moveToNext());
            } else {
                Log.d("Yazarara", "Yazar bulunamadı.");
            }
        } catch (Exception e) {
            Log.e("Yazarara", "Arama hatası: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return aramaSonucu;
    }


    public  List<String> Kitaplistele(){
        List<String>  kitaplar=new ArrayList<>();
        SQLiteDatabase db1=this.getWritableDatabase();
        String[] sutunlar={Kitapadi,Yazaradi,Yayinevi,Sayfasayisi,Kategori};
        Cursor cr=db1.query(TABLE_NAME2, sutunlar, null, null, null, null, null);
        while (cr.moveToNext()){

            String kitapBilgisi = cr.getString(0) + "-" + cr.getString(1) + "-" + cr.getString(2);
            kitaplar.add(kitapBilgisi);
        }
        return kitaplar;
    }

    public Boolean Kitapguncelle(String kitapadi, String yazaradi,String yayinevi,String sayfasayisi,String kategori){
        try {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues cv3 = new ContentValues();
            cv3.put(Kitapadi, kitapadi.trim());
            cv3.put(Yazaradi, yazaradi.trim());
            cv3.put(Yayinevi, yayinevi.trim());
            cv3.put(Sayfasayisi, sayfasayisi.trim());
            cv3.put(Kategori, kategori.trim());

            int guncel = DB.update(TABLE_NAME2, cv3, Kitapadi + "=?", new String[]{kitapadi.trim()});
            if(guncel>0){
                Log.i("tag","kitap güncellendi");
                return true;
            }else{
                Log.i("tag","kitap güncellenemedi");
                return false;
            }


        }catch (Exception e)
        {
            Log.e("tag", "Kitap güncelleme işlemi sırasında hata oluştu: " + e.toString());
            return false;
        }


    }
    public Boolean Kitapsil(String kitapadi){
        try {
            SQLiteDatabase DB1 = this.getWritableDatabase();
            ContentValues cv4 = new ContentValues();
            cv4.put(Kitapadi, kitapadi.trim());
            String selection = Kitapadi + " = ?";
            String[] selectionArgs = {kitapadi};
            int silsatir = DB1.delete(TABLE_NAME2, selection, selectionArgs);
            if (silsatir > 0) {
                Log.i("tag", "kitap silindi");
                return true;
            } else {
                Log.i("tag", "kitap silinmedi");
                return false;
            }
        }
        catch (Exception e)
        {
            Log.e("tag", "Kitap silme işlemi sırasında hata oluştu: " + e.toString());
            return false;
        }

    }


    public List<String> KitapAra(String kitapadi) {
        List<String> aramaSonucu = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Kitapadi,Yazaradi,Yayinevi,Sayfasayisi,Kategori};
        String selection = Kitapadi + " LIKE ?";
        String[] selectionArgs = {"%" + kitapadi + "%"};
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME2, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(Kitapadi);
                int columnIndex2 = cursor.getColumnIndex(Yazaradi);
                int columnIndex3 = cursor.getColumnIndex(Yayinevi);
                int columnIndex4 = cursor.getColumnIndex(Sayfasayisi);
                int columnIndex5=cursor.getColumnIndex(Kategori);

                do {
                    String kitapAdi = cursor.getString(columnIndex); // Kitap adını al
                    String yazaradi = cursor.getString(columnIndex2);
                    String yayinevi = cursor.getString(columnIndex3);
                    String sayfasayisi = cursor.getString(columnIndex4);
                    String kategori=cursor.getString(columnIndex5);

                    aramaSonucu.add(kitapAdi);
                    aramaSonucu.add(yazaradi);
                    aramaSonucu.add(yayinevi);
                    aramaSonucu.add(sayfasayisi);
                    aramaSonucu.add(kategori);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Veritabanı", "Arama hatası: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return aramaSonucu;
    }
    public List<String> romanKitaplariniListele() {
        List<String> romanKitaplar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Kitapadi, Yazaradi, Yayinevi, Sayfasayisi,Kategori};
        String selection = Kategori + " = ?";
        String[] selectionArgs = {"Roman"};
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME2, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range")   String kitapAdi = cursor.getString(cursor.getColumnIndex(Kitapadi));
                    @SuppressLint("Range") String yazarAdi = cursor.getString(cursor.getColumnIndex(Yazaradi));
                    @SuppressLint("Range") String yayinevi = cursor.getString(cursor.getColumnIndex(Yayinevi));
                    @SuppressLint("Range")  String sayfaSayisi = cursor.getString(cursor.getColumnIndex(Sayfasayisi));

                    String kitapBilgisi = kitapAdi + "-" + yazarAdi + "-" + yayinevi + "-" + sayfaSayisi;

                    romanKitaplar.add(kitapBilgisi);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Veritabanı", "Roman kitapları listeleme hatası: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return romanKitaplar;
    }
    public List<String> mangaKitaplariniListele() {
        List<String> romanKitaplar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Kitapadi, Yazaradi, Yayinevi, Sayfasayisi,Kategori};
        String selection = Kategori + " = ?";
        String[] selectionArgs = {"Manga"};
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME2, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range")   String kitapAdi = cursor.getString(cursor.getColumnIndex(Kitapadi));
                    @SuppressLint("Range") String yazarAdi = cursor.getString(cursor.getColumnIndex(Yazaradi));
                    @SuppressLint("Range") String yayinevi = cursor.getString(cursor.getColumnIndex(Yayinevi));
                    @SuppressLint("Range")  String sayfaSayisi = cursor.getString(cursor.getColumnIndex(Sayfasayisi));

                    String kitapBilgisi = kitapAdi + "-" + yazarAdi + "-" + yayinevi + "-" + sayfaSayisi;
                    // Oluşturulan string'i listeye ekle
                    romanKitaplar.add(kitapBilgisi);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Veritabanı", "Roman kitapları listeleme hatası: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return romanKitaplar;
    }
    public List<String> bilimselKitaplariniListele() {
        List<String> romanKitaplar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Kitapadi, Yazaradi, Yayinevi, Sayfasayisi,Kategori}; // İlgilenilen sütunlar
        String selection = Kategori + " = ?"; // Kategoriye göre seçim kriteri
        String[] selectionArgs = {"Bilimsel"}; // Roman kategorisi için argümanlar
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME2, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range")   String kitapAdi = cursor.getString(cursor.getColumnIndex(Kitapadi));
                    @SuppressLint("Range") String yazarAdi = cursor.getString(cursor.getColumnIndex(Yazaradi));
                    @SuppressLint("Range") String yayinevi = cursor.getString(cursor.getColumnIndex(Yayinevi));
                    @SuppressLint("Range")  String sayfaSayisi = cursor.getString(cursor.getColumnIndex(Sayfasayisi));

                    String kitapBilgisi = kitapAdi + "-" + yazarAdi + "-" + yayinevi + "-" + sayfaSayisi;
                    // Oluşturulan string'i listeye ekle
                    romanKitaplar.add(kitapBilgisi);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Veritabanı", "Roman kitapları listeleme hatası: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return romanKitaplar;
    }






}

