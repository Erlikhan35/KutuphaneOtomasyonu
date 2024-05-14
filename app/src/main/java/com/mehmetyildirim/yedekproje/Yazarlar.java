package com.mehmetyildirim.yedekproje;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Yazarlar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazarlar);
        db2 = new VeriTabani(Yazarlar.this);
        txtyzad=findViewById(R.id.Txt_yazar_ad);
        txtbio=findViewById(R.id.txt_bio);
        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }




    VeriTabani db2;
    private Uri imageUri;
    ImageView imageView;
    EditText txtyzad,txtbio;


    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);

                // Resmi byte dizisine dönüştür
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 25, baos);
                byte[] imageData = baos.toByteArray();

                // Resmi veritabanına eklemek için VeriTabani sınıfındaki yazarEkle metodunu çağır
                db2.yazarEkle(txtyzad.getText().toString(), txtbio.getText().toString(), imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void yazarekle(View view){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] byteArray = stream.toByteArray();
            db2.yazarEkle(txtyzad.getText().toString(),txtbio.getText().toString(),byteArray);
            Toast.makeText(Yazarlar.this, "Kitap eklendi", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e("yazarekleme", e.toString());
        }
    }
    public void yzliste(View view)
    {
        Intent yzgit=new Intent(Yazarlar.this,Yazarliste.class);
        startActivity(yzgit);
        finish();

    }
    public void yzsil(View view){
        try {
            boolean yzsilme=  db2.Yazarsil(txtyzad.getText().toString());
            if(yzsilme)
                Toast.makeText(Yazarlar.this, "Yazar silindi", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Yazarlar.this,"Yazar silinmedi",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(Yazarlar.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



}