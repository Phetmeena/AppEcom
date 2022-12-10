package com.product.appecom_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.product.appecom_test.databinding.ActivityCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        productList = new ArrayList<>();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Cart);

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                        if (product.getCheck_id().equals(getIntent().getStringExtra("id"))) {
                            productList1.add(product);
                        }
                    }

                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), productList1, getIntent().getStringExtra("id"), getIntent().getStringExtra("name"), getIntent().getStringExtra("type"));
                    binding.recyclerCart.setAdapter(adapterProduct);
                    binding.recyclerCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}