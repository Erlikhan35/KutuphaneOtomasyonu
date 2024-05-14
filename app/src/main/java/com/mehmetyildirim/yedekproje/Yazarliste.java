package com.mehmetyildirim.yedekproje;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Yazarliste extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazarliste);

        listView=findViewById(R.id.listView);
        img=findViewById(R.id.imageView4);

    }
    ImageView img;

    ListView listView;
    VeriTabani veriTabani=new VeriTabani(Yazarliste.this);

    public void yzlistele(View view){
        try {
            // Kullanıcı tarafından girilen yazar ismini alın
            EditText yazarAdiEditText = findViewById(R.id.txtara);
            String yazarAdi = yazarAdiEditText.getText().toString().trim();

            // Yazarın bilgilerini veritabanından al
            List<String> yazarBilgileri = veriTabani.Yazarara(yazarAdi);

            // Eğer yazar bilgileri bulunamadıysa
            if (yazarBilgileri == null || yazarBilgileri.isEmpty()) {
                Toast.makeText(Yazarliste.this, "Yazar bulunamadı.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Yazar bilgilerini ayrıştır ve ekrandaki nesnelere yazdır
            String[] yazarBilgisiParcalari = yazarBilgileri.get(0).split("-");
            String yazarAdiFromDB = yazarBilgisiParcalari[0];
            String yazarBiyografiFromDB = yazarBilgisiParcalari[1];
            byte[] yazarFotoFromDB = yazarBilgisiParcalari[2].getBytes();

            // Yazar fotoğrafını göstermek için ImageView'ı güncelle
            if (yazarFotoFromDB != null && yazarFotoFromDB.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(yazarFotoFromDB, 0, yazarFotoFromDB.length);
                img.setImageBitmap(bitmap);
            } else {
                img.setImageResource(R.drawable.okullogo); // Varsayılan bir resim ayarlayın
            }

            // Yazar ismini EditText'e yazdır
            yazarAdiEditText.setText(yazarAdiFromDB);

            // Yazar biyografisini TextView'e yazdır
            TextView yazarBiyografiTextView = findViewById(R.id.txt_bio);
            yazarBiyografiTextView.setText(yazarBiyografiFromDB);

        } catch (Exception e) {
            Toast.makeText(Yazarliste.this, "Hata oluştu: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("yzlistele", e.toString());
        }
    }





}