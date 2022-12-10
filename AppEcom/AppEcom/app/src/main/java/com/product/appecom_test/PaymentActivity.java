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
import com.product.appecom_test.databinding.ActivityPaymentBinding;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;
    List<Payment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseReference mDatabasePayment = FirebaseDatabase.getInstance().getReference(Contants.Payment);

        mDatabasePayment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        try {
                            Payment infor = ds.getValue(Payment.class);
                            list.add(infor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    List<Payment> list1 = new ArrayList<>();
                    for (Payment payment : list) {
                        if (payment.getId_user().equals(getIntent().getStringExtra("id"))) {
                            list1.add(payment);
                        }
                    }

                    AdapterPayment adapterProduct = new AdapterPayment(getApplicationContext(), list1);
                    binding.recyclerPayment.setAdapter(adapterProduct);
                    binding.recyclerPayment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}