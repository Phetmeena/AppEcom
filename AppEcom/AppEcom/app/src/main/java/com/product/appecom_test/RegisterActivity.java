package com.product.appecom_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.product.appecom_test.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Contants.User);
                String id = mDatabase.push().getKey();
                String username = binding.edtUsername.getText().toString();
                String firstName = binding.edtFirstName.getText().toString();
                String lastName = binding.edtLastName.getText().toString();
                String password = binding.edtPassword.getText().toString();
                String comfirmPass = binding.edtConfirmPassword.getText().toString();
                String phone = binding.edtPhoneNumber.getText().toString();
                String address = binding.edtAddress.getText().toString();
                String brithday = binding.txtDateOfBirth.getText().toString();


                if (!username.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() &&
                        !password.isEmpty() && !comfirmPass.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !brithday.isEmpty()) {
                    if (password.equals(comfirmPass)) {
                        String user = "";
                        Gender gender = null;

                        if (binding.radioMale.isChecked()) {
                            gender = Gender.MALE;
                        } else {
                            gender = Gender.FEMALE;
                        }

                        if (binding.radioAdmin.isChecked()) {
                            user = "SALESMAN";
                        } else {
                            user = "CUSTOMER";
                        }

                        Person person = new Person(id, username, password, firstName, lastName, phone, gender, address, brithday, "", "", user);
                        mDatabase.child(id).setValue(person).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Successfully!!!", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(RegisterActivity.this, "Vui lòng nhập password và xác nhận giống nhau!!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Không được để trống !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}