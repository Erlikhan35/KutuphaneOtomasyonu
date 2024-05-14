package com.mehmetyildirim.yedekproje;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Kitaplistesi extends AppCompatActivity {
    ListView listView;
    TextView hatatxt;
    EditText txtktpadara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitaplistesi);
        listView=findViewById(R.id.Listektp);
        txtktpadara=findViewById(R.id.Txt_ktpadguncel);
        String[] arraySpinner = new String[] {
                "Roman", "Manga", "Bilimsel", "A-Z göre sırala"
        };
        Spinner s = (Spinner) findViewById(R.id.spinnersiralama);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);



    }
    VeriTabani veriTabani=new VeriTabani(Kitaplistesi.this);
        public  void listelekitap(View view){
            try {
                List<String> kitaplar = veriTabani.Kitaplistele();
                if (kitaplar != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, android.R.id.text1, kitaplar);
                    listView.setAdapter(adapter);
                } else {

                    Toast.makeText(Kitaplistesi.this, "liste boş",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(Kitaplistesi.this, e.toString(), Toast.LENGTH_LONG).show();
                Log.e("listele", e.toString());


            }

    }
    public void kitaparama(View view){
        try {
            String kitapAdi = txtktpadara.getText().toString();
            List<String> aramaSonucu = veriTabani.KitapAra(kitapAdi);

            if (aramaSonucu != null && !aramaSonucu.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, aramaSonucu);
                listView.setAdapter(adapter);
                Toast.makeText(Kitaplistesi.this, "Arama başarılı", Toast.LENGTH_SHORT).show();
            } else {
                listView.setAdapter(null);
                Toast.makeText(Kitaplistesi.this, "Arama sonucu bulunamadı", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(Kitaplistesi.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    public void kitapsiralama2(View view) {
        try {
            Spinner spinner = (Spinner) findViewById(R.id.spinnersiralama);
            String secilenKategori = spinner.getSelectedItem().toString();

            switch (secilenKategori) {
                case "Roman":
                    try {
                        List<String> romanKitaplar = veriTabani.romanKitaplariniListele();
                        if (romanKitaplar != null && !romanKitaplar.isEmpty()) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, romanKitaplar);
                            listView.setAdapter(adapter);
                        } else {
                            listView.setAdapter(null);
                            Toast.makeText(Kitaplistesi.this, "Roman kitapları bulunamadı", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Kitaplistesi.this, "Roman kitapları listeleme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Kitapsiralama", "Roman kitapları listeleme hatası", e);
                    }
                    break;
                case "Manga":
                    try {
                        List<String> mangaKitaplar = veriTabani.mangaKitaplariniListele();
                        if (mangaKitaplar != null && !mangaKitaplar.isEmpty()) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mangaKitaplar);
                            listView.setAdapter(adapter);
                        } else {
                            listView.setAdapter(null);
                            Toast.makeText(Kitaplistesi.this, "Manga kitapları bulunamadı", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Kitaplistesi.this, "Manga kitapları listeleme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Kitapsiralama", "Manga kitapları listeleme hatası", e);
                    }
                    break;
                case "Bilimsel":
                    try {
                        List<String> bilimselKitaplar = veriTabani.bilimselKitaplariniListele();
                        if (bilimselKitaplar != null && !bilimselKitaplar.isEmpty()) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, bilimselKitaplar);
                            listView.setAdapter(adapter);
                        } else {
                            listView.setAdapter(null);
                            Toast.makeText(Kitaplistesi.this, "Bilimsel kitaplar bulunamadı", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Kitaplistesi.this, "Bilimsel kitaplar listeleme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Kitapsiralama", "Bilimsel kitaplar listeleme hatası", e);
                    }
                    break;
                case "A-Z göre sırala":

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(Kitaplistesi.this, "Kitapları sıralama hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Kitapsiralama", "Kitapları sıralama hatası", e);
        }
    }
}
