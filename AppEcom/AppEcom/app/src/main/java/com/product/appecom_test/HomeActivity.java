package com.product.appecom_test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    ArrayList<Image> list;
    List<Product> productList;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    RecyclerView recyclerView;
    CircleIndicator indicator;
    DrawerLayout mDrawerLayout;
    String userType;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.viewpager);
        list = new ArrayList<>();
        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        Toolbar toolbar = findViewById(R.id.tool_bar1);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionbar.setTitle("Trang chủ");

        Intent intent = getIntent();
        if (intent != null) {
            userType = intent.getStringExtra("type");
            if (!userType.equals("SALESMAN")) {
                findViewById(R.id.fabAdd).setVisibility(View.GONE);
            } else {
                hideItem();
            }
        }

        findViewById(R.id.fabAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Intent intent = null;
                        switch (menuItem.getItemId()) {
                            case R.id.menu_home:
                                mDrawerLayout.closeDrawers();
                                return true;
                            case R.id.menu_cart:
                                intent = new Intent(getApplicationContext(), CartActivity.class);
                                intent.putExtra("id", getIntent().getStringExtra("id"));
                                intent.putExtra("type", userType);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return true;
                            case R.id.menu_shop:
                                intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                intent.putExtra("id", getIntent().getStringExtra("id"));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return true;

                            case R.id.menu_filter:
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                                LayoutInflater inflater = getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.dialog_filter, null);
                                dialogBuilder.setView(dialogView);
                                AlertDialog alertDialog = dialogBuilder.create();
                                ImageView imgQuan = dialogView.findViewById(R.id.quan);
                                ImageView imgAo = dialogView.findViewById(R.id.ao);
                                ImageView imgVay = dialogView.findViewById(R.id.vay);
                                ImageView imgYem = dialogView.findViewById(R.id.yem);

                                imgAo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Product);

                                        mDatabase1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    productList.clear();
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        Product infor = ds.getValue(Product.class);
                                                        productList.add(infor);
                                                    }

                                                    List<Product> productList1 = new ArrayList<>();

                                                    for (Product product : productList) {
                                                        if (product.getType().equals("Áo") && !productList1.contains(product)) {
                                                            productList1.add(product);
                                                        }
                                                    }

                                                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), productList1, getIntent().getStringExtra("id"), getIntent().getStringExtra("name"), userType);
                                                    recyclerView.setAdapter(adapterProduct);
                                                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                                                    mDrawerLayout.closeDrawers();
                                                    alertDialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });

                                imgQuan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Product);

                                        mDatabase1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                productList.clear();
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        Product infor = ds.getValue(Product.class);
                                                        productList.add(infor);
                                                    }

                                                    List<Product> productList1 = new ArrayList<>();
                                                    productList1.clear();
                                                    for (Product product : productList) {
                                                        if (product.getType().equals("Quần") && !productList1.contains(product)) {
                                                            productList1.add(product);
                                                        }
                                                    }

                                                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), productList1, getIntent().getStringExtra("id"), getIntent().getStringExtra("name"), userType);
                                                    recyclerView.setAdapter(adapterProduct);
                                                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                                                    mDrawerLayout.closeDrawers();
                                                    alertDialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });

                                imgVay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Product);

                                        mDatabase1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    productList.clear();
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        Product infor = ds.getValue(Product.class);
                                                        productList.add(infor);
                                                    }

                                                    List<Product> productList1 = new ArrayList<>();

                                                    for (Product product : productList) {
                                                        if (product.getType().equals("Váy") && !productList1.contains(product)) {
                                                            productList1.add(product);
                                                        }
                                                    }

                                                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), productList1, getIntent().getStringExtra("id"),
                                                            getIntent().getStringExtra("name"), userType);
                                                    recyclerView.setAdapter(adapterProduct);
                                                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                                                    mDrawerLayout.closeDrawers();
                                                    alertDialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });


                                imgYem.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Product);

                                        mDatabase1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    productList.clear();
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        Product infor = ds.getValue(Product.class);
                                                        productList.add(infor);
                                                    }

                                                    List<Product> productList1 = new ArrayList<>();

                                                    for (Product product : productList) {
                                                        if (product.getType().equals("Yếm") && !productList1.contains(product)) {
                                                            productList1.add(product);
                                                        }
                                                    }

                                                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), productList1, getIntent().getStringExtra("id"), getIntent().getStringExtra("name"), userType);
                                                    recyclerView.setAdapter(adapterProduct);
                                                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                                                    mDrawerLayout.closeDrawers();
                                                    alertDialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });

                                alertDialog.show();
                                return true;
                            case R.id.menu_exit:
                                finish();
                                return true;
                        }
                        return true;

                    }
                });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Contants.Image);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Image infor = ds.getValue(Image.class);
                        list.add(infor);
                    }

                    ViewPagerAdapter adapter = new ViewPagerAdapter(HomeActivity.this, list);
                    viewPager.setAdapter(adapter);

                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == list.size() - 1) {
                                currentPage = 0;
                            }
                            viewPager.setCurrentItem(currentPage++, true);
                        }
                    };

                    timer = new Timer(); // This will create a new Thread
                    timer.schedule(new TimerTask() { // task to be scheduled
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, DELAY_MS, PERIOD_MS);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Product);

        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    productList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Product infor = ds.getValue(Product.class);
                        productList.add(infor);
                    }

                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), productList, getIntent().getStringExtra("id"), getIntent().getStringExtra("name"), userType);
                    recyclerView.setAdapter(adapterProduct);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void hideItem() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.menu_filter).setVisible(false);
        nav_Menu.findItem(R.id.menu_cart).setVisible(false);
        nav_Menu.findItem(R.id.menu_shop).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Product);

        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    productList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Product infor = ds.getValue(Product.class);
                        productList.add(infor);
                    }

                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), productList, getIntent().getStringExtra("id"), getIntent().getStringExtra("name"), userType);
                    recyclerView.setAdapter(adapterProduct);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}