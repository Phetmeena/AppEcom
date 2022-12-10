package com.product.appecom_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.product.appecom_test.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseReference databaseReference;
    List<Person> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child(Contants.User);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                try {
                                    Person infor = ds.getValue(Person.class);
                                    list.add(infor);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            int dem = 0;
                            for (Person person : list) {
                                if (person.getUserName().equals(binding.edtUsername.getText().toString())
                                        && person.getPassword().equals(binding.edtPassword.getText().toString())) {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("id", person.getId());
                                    intent.putExtra("name", person.getUserName());
                                    intent.putExtra("type", person.getUserType());
                                    startActivity(intent);
                                    break;
                                } else {
                                    dem++;
                                }
                            }

                            if (dem == list.size()) {
                                Toast.makeText(LoginActivity.this, "Vui lòng nhập lại tài khoản mật khẩu !!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}