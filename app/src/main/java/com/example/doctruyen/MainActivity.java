package com.example.doctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.doctruyen.adapter.adapterTruyen;
import com.example.doctruyen.adapter.adapterchuyenmuc;
import com.example.doctruyen.adapter.adapterthongtin;
import com.example.doctruyen.database.databasedoctruyen;
import com.example.doctruyen.model.TaiKhoan;
import com.example.doctruyen.model.Truyen;
import com.example.doctruyen.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView,listViewNew,listViewThongTin;
    DrawerLayout drawerLayout;

    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArraylist;

    adapterTruyen adapterTruyen;

    ArrayList<chuyenmuc> chuyenmucArrayList;

    ArrayList<TaiKhoan> taiKhoanArrayList;

    databasedoctruyen databasedoctruyen;

    adapterchuyenmuc adapterchuyenmuc;
    adapterthongtin adapterthongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasedoctruyen = new databasedoctruyen(this);

        // Nh???n d??? li???u ??? m??n ????ng nh???p g???i
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanquyen",0);
        int idd = intentpq.getIntExtra("idd",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        AnhXa();
        ActionBar();
        ActionViewFlipper();

        // B???t s??? ki???n click item
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ManNoiDung.class);

                String tent = TruyenArraylist.get(position).getTenTruyen();
                String noidungt = TruyenArraylist.get(position).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);
                startActivity(intent);
            }
        });

        // b???t click item cho listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // ????ng b??i
                if (position == 0) {
                    if (i == 3) {
                        Toast.makeText(MainActivity.this, "B???n kh??ng c?? quy???n ????ng b??i", Toast.LENGTH_SHORT).show();
                        Log.e("????ng b??i : ", "B???n kh??ng c?? quy???n");
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this,ManAdmin.class);
                        //G???i id t??i kho???n qua m??n admin
                        intent.putExtra("Id",idd);
                        startActivity(intent);
                    }
                }
                // N???u v??? tr?? ???n v??o l?? th??ng tin th?? s??? chuy???n qua m??n th??ng tin app
                else if(position == 1){
                    Intent intent = new Intent(MainActivity.this,ManThongTin.class);
                    startActivity(intent);
                }
                else if(position == 2){
                    finish();
                }
            }
        });

        HwAds.init(this);

        // Obtain BannerView configured in the XML layout file.
        BannerView bottomBannerView = findViewById(R.id.hw_banner_view);
        AdParam adParam = new AdParam.Builder().build();
        bottomBannerView.loadAd(adParam);

        // Add BannerView through coding.
        BannerView topBannerView = new BannerView(this);
        topBannerView.setAdId("testw6vs28auh3");
        topBannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_SMART);
        topBannerView.loadAd(adParam);


    }

    // Thanh action v???i toolbar
    private void ActionBar() {
        // H??m h??? tr??? toolbar
        setSupportActionBar(toolbar);

        // set n??t cho actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // T???o icon cho toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        // b???t s??? ki???n click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    // Ph????ng th???c cho ch???y qu???ng c??o v???i ViewFlipper
    private void ActionViewFlipper() {
        // M???ng ch???a t???m ???nh cho qu???ng c??o
        ArrayList<String> mangquangcao = new ArrayList<>();
        // Add ???nh v??o m???ng
        mangquangcao.add("https://toplist.vn/images/800px/rua-va-tho-230179.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/deo-chuong-cho-meo-230180.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/cu-cai-trang-230181.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg");

        // Th???c hi???n v??ng l???p for g??n ???nh v??o ImageView, r???i t??? imgView l??n app
        for (int i=0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            // S??? d???ng h??m th?? vi???n Picasso
            Picasso.get().load(mangquangcao.get(i)).into(imageView);

            // Ph????ng th???c ch???nh t???m h??nh v???a khung qu???ng c??o
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            // Th??m ???nh t??? imageview v??o ViewFlipper
            viewFlipper.addView(imageView);
        }
        // Thi???t b??? t??? ?????ng ch???y cho viewFlipper ch???y trong 4 gi??y
        viewFlipper.setFlipInterval(4000);
        // run auto viewFlipper
        viewFlipper.setAutoStart(true);

        // G???i Animation cho v??o v?? ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);

        // G???i Animation v??o viewFlipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);

    }

    // Ph????ng th???c ??nh x???
    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewNew = findViewById(R.id.listViewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArraylist = new ArrayList<>();

        Cursor cursor1 = databasedoctruyen.getData1();
        while (cursor1.moveToNext()){

            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArraylist.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(),TruyenArraylist);
            listViewNew.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();

        // Th??ng tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));

        adapterthongtin = new adapterthongtin(this,R.layout.navigation_thong_tin,taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        // chuy??n m???c
        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("????ng b??i",R.drawable.ic_baseline_post_add_24));
        chuyenmucArrayList.add(new chuyenmuc("Th??ng tin",R.drawable.ic_baseline_face_24));
        chuyenmucArrayList.add(new chuyenmuc("????ng xu???t",R.drawable.ic_baseline_login_24));

        adapterchuyenmuc = new adapterchuyenmuc(this,R.layout.chuyenmuc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);

    }

    // N???p m???t menu t??m ki???m v??o ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // N???u click v??o icon t??m ki???m chuy???n qua icon t??m ki???m
        switch (item.getItemId()) {
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this, ManScan.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}