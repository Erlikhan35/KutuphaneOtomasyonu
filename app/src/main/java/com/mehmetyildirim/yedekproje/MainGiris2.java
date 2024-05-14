package com.mehmetyildirim.yedekproje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainGiris2 extends AppCompatActivity {

    EditText txtokulno,txtparola;
    VeriTabani db;
    CheckBox chkhatirla;
    String getkullaniciadi,getParola;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giris2);
       preferences=this.getSharedPreferences("com.mehmetyildirim.yedekproje", Context.MODE_PRIVATE);

        txtokulno=findViewById(R.id.girisokulno);
        chkhatirla=findViewById(R.id.Checkhatirla);

        txtparola=findViewById(R.id.girisparola);
        db=new VeriTabani(this);
        getkullaniciadi=preferences.getString("kullaniciadi",null);
        getParola=preferences.getString("kullaniciparola",null);
        if (!TextUtils.isEmpty(getkullaniciadi)&&!TextUtils.isEmpty(getParola)) {
            txtokulno.setText(getkullaniciadi);
            txtparola.setText(getParola);
        }
        else {
            Toast.makeText(getApplicationContext(), "Kayıt yok", Toast.LENGTH_SHORT).show();
        }

    }
    public void Btn_giris2(View view){
        try {
            Intent giris4=new Intent(MainGiris2.this,AnaSayfa.class);
            startActivity(giris4);
            finish();
        }
        catch (Exception e)
        {
            Toast.makeText(MainGiris2.this, e.toString(), Toast.LENGTH_LONG).show();
            System.out.println(e.toString());
            Log.e("gırıs", e.toString());
        }

    }

    public void deneme(View view)
    {
        try { String kullaniciAdi = txtokulno.getText().toString().trim();
            String parola = txtparola.getText().toString().trim();
            if (!TextUtils.isEmpty(kullaniciAdi) && !TextUtils.isEmpty(parola))
            {


            if (!kullaniciAdi.isEmpty() && !parola.isEmpty()) {
                if(chkhatirla.isChecked()){
                    editor =preferences.edit();
                    editor.putString("kullaniciadi", kullaniciAdi);
                    editor.putString("kullaniciparola", parola);
                    editor.apply();
                    Toast.makeText(MainGiris2.this, "Kullanıcı Girisi otomatik kaydedildi", Toast.LENGTH_SHORT).show();

                }

                boolean girisBasarili = db.kullaniciGirisi(kullaniciAdi, parola);
                if (girisBasarili) {

                    Toast.makeText(MainGiris2.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    // Burada giriş başarılı olduğunda yapılacak işlemleri gerçekleştir

                    Intent giris3 = new Intent(MainGiris2.this, AnaSayfa.class);
                    startActivity(giris3);
                    finish();
                } else {
                    Toast.makeText(MainGiris2.this, "Kullanıcı adı veya parola hatalı", Toast.LENGTH_SHORT).show();
                }
            }

            } else {
                Toast.makeText(MainGiris2.this, "Kullanıcı adı ve parola boş olamaz", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
             Toast.makeText(MainGiris2.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            System.out.println(e.toString());

        }
    }
}