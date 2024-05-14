package com.mehmetyildirim.yedekproje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainGiris extends AppCompatActivity {

    VeriTabani db;
    EditText edittxtokulno,editxtparola,txtparoladogrula,txtx2;

    TextView txtunuttum,txtgel;
    Button btnKayitol,btngirisgit;
    CheckBox hatirla;
  //  KayitSayfasi ky=new KayitSayfasi();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giris);
        db=new VeriTabani(this);
        edittxtokulno=findViewById(R.id.Edittextokul);
        editxtparola=findViewById(R.id.Edittextparola);
        //txtgel=findViewById(R.id.txtgelis);
        txtunuttum=findViewById(R.id.Textunuttum);
        btnKayitol=findViewById(R.id.BtnKayitGit);
        btngirisgit=findViewById(R.id.Buttongiris2);
        txtparoladogrula=findViewById(R.id.paroladogrula);
        hatirla=findViewById(R.id.bnihatirla);




        /*

        btnKayitol.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                try{
                String okulno=edittxtokulno.getText().toString();
                String parola=editxtparola.getText().toString();
                String parolatekrar=txtparoladogrula.getText().toString();
                if(TextUtils.isEmpty(okulno) || TextUtils.isEmpty(parola) || TextUtils.isEmpty(parolatekrar))
                {
                    Toast.makeText(MainGiris.this, "Luften bos alan bırakmayın.", Toast.LENGTH_SHORT).show();

                }
                else {
                    if(parola.equals(parolatekrar))
                    {
                        Boolean checkuser=db.checkokulno(okulno);
                        if(checkuser==false)
                        {
                            Boolean insert=db.veriEkle(okulno, parola);
                            if (insert==true){
                                Toast.makeText(MainGiris.this, "Kayıt yapıldı", Toast.LENGTH_SHORT).show();
                                Intent Anasayfa=new Intent(MainGiris.this,AnaSayfa.class);
                                startActivity(Anasayfa);
                                finish();
                            }else {
                                Toast.makeText(MainGiris.this, "Kayıt yapılmadı", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(MainGiris.this, "Bu okul numarası zaten kullanımda.", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(MainGiris.this, "Parolalar eşleşmiyor", Toast.LENGTH_SHORT).show();
                    }
                }
                }
                catch(Exception e)
                {
                    Toast.makeText(MainGiris.this, e.toString(),Toast.LENGTH_LONG).show();
                    System.out.println(e.toString());
                }


            }
        });

         */



        btngirisgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent giris2=new Intent(MainGiris.this,MainGiris2.class);
                startActivity(giris2);




            }
        });



    }
    public  void Btnkayit(View view)
    {
       try {
           String okulno=edittxtokulno.getText().toString();
           String parola=editxtparola.getText().toString();
           String parolatekrar=txtparoladogrula.getText().toString();
           if(TextUtils.isEmpty(okulno) || TextUtils.isEmpty(parola) || TextUtils.isEmpty(parolatekrar))
           {
               Toast.makeText(MainGiris.this, "Luften bos alan bırakmayın.", Toast.LENGTH_SHORT).show();

           }
           else{
           db.kekle(edittxtokulno.getText().toString(),editxtparola.getText().toString());
           Toast.makeText(MainGiris.this, "kullanıcı eklendı", Toast.LENGTH_SHORT).show();
           }
       }

       catch (Exception e){
            Log.e("kekleme", e.toString());
       }



    }
    public  void txtclick(View view){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822"); // E-posta formatı belirleme
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mehmetyildirim11.e@gmail.com"}); // Alıcı e-posta adresi
            intent.putExtra(Intent.EXTRA_SUBJECT, "Konu Başlığı"); // E-posta konu başlığı

            // Eğer e-posta uygulaması yoksa, web tarayıcısında Gmail'e yönlendir
            if (intent.resolveActivity(getPackageManager()) == null) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com"));
            }

            // Intent'i başlat
            startActivity(intent);
        }
        catch (Exception e){
            Log.e("txtclick:", e.toString());

        }
    }
    public void txtgelistirici(View view){
        try {

        }
        catch (Exception e){
            Log.e("txtgelis:", e.toString());
        }
    }
}