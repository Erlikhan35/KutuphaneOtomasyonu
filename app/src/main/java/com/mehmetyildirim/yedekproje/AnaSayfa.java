package com.mehmetyildirim.yedekproje;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.view.GravityCompat;


import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mehmetyildirim.yedekproje.databinding.ActivityAnaSayfaBinding;

public class AnaSayfa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAnaSayfaBinding binding;
    VeriTabani db;
    EditText txtkitapad, txtyazarad, txtyayinevi, txtsayfasayisi,txtkategori;
    MenuItem menuItem;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAnaSayfaBinding.inflate(getLayoutInflater());


        db = new VeriTabani(AnaSayfa.this);
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAnaSayfa.toolbar);
        txtkitapad = findViewById(R.id.Txt_ktpad);
        txtyazarad = findViewById(R.id.Txt_yazarad);
        txtyayinevi = findViewById(R.id.Txt_yayinevi);
        txtsayfasayisi = findViewById(R.id.Txt_syfsayi);
        txtkategori=findViewById(R.id.Txt_kategori);



        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_kitaplarim, R.id.nav_ayar, R.id.nav_istek)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_ana_sayfa);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        binding.navView.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int nav_kitaplarim=R.id.nav_kitaplarim;
        final int nav_ayar=R.id.nav_ayar;
        final int nav_harita=R.id.nav_istek;
        final int nav_cikis=R.id.nav_cikis;

        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.nav_kitaplarim) {

            Intent intentKitaplarim = new Intent(AnaSayfa.this, Kitaplistesi.class);
            startActivity(intentKitaplarim);
        } else if (menuItemId == R.id.nav_ayar) {

            Intent intentAyarlar = new Intent(AnaSayfa.this, MainGiris2.class);
            startActivity(intentAyarlar);
        } else if (menuItemId == R.id.nav_istek) {

            Intent intentIstekler = new Intent(AnaSayfa.this, MainGiris.class);
            startActivity(intentIstekler);
        }

        item.setChecked(true);

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.ana_sayfa, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(AnaSayfa.this, R.id.nav_host_fragment_content_ana_sayfa);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void kitapekle(View view) {

        db.kitapEkle(txtkitapad.getText().toString(), txtyazarad.getText().toString(), txtyayinevi.getText().toString(), txtsayfasayisi.getText().toString(),txtkategori.getText().toString());
        Toast.makeText(AnaSayfa.this, "Kitap eklendi", Toast.LENGTH_SHORT).show();

    }


    public void kitaplarimListegit(View view) {

        try {
            Intent menuKitapgit = new Intent(AnaSayfa.this, Kitaplistesi.class);
            startActivity(menuKitapgit);
            finish();
        } catch (Exception e) {
            System.out.println("hata:" + e.toString());
        }


    }
    public void Kitapguncelle2(View view){
        try {
            boolean isSuccess =  db.Kitapguncelle(txtkitapad.getText().toString(), txtyazarad.getText().toString(), txtyayinevi.getText().toString(), txtsayfasayisi.getText().toString(),txtkategori.getText().toString());

            if (isSuccess) {
                Toast.makeText(AnaSayfa.this, "Kitap güncellendi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AnaSayfa.this, "Kitap güncelleme başarısız", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(AnaSayfa.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void kitapSilme(View view){
        try {
            boolean isSuccess2 =  db.Kitapsil(txtkitapad.getText().toString());

            if (isSuccess2) {
                Toast.makeText(AnaSayfa.this, "Kitap silindi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AnaSayfa.this, "Kitap silme başarısız", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(AnaSayfa.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public  void Yazargit(View view){
        try {
            Intent yazar = new Intent(AnaSayfa.this, Yazarlar.class);
            startActivity(yazar);
            finish();
        } catch (Exception e) {
            System.out.println("hata:" + e.toString());
        }
    }


}





