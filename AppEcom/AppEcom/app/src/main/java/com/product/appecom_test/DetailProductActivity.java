package com.product.appecom_test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.product.appecom_test.databinding.ActivityDetailProductBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    ActivityDetailProductBinding binding;
    Product product;
    String id;
    List<Comment> list = new ArrayList<>();
    List<Product> listCart = new ArrayList<>();
    int soluong = 0;
    String id_cart = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Comment);
        if (intent != null) {
            product = (Product) intent.getSerializableExtra("object");
            id = intent.getStringExtra("id");
            try {
                id_cart = intent.getStringExtra("id_cart");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Glide.with(getApplicationContext()).load(product.getBigImageUrl()).error(R.mipmap.ecom_logo).into(binding.imgBigImageProduct);

            Glide.with(getApplicationContext()).load(product.getSmallImageUrl()).error(R.mipmap.ecom_logo).into(binding.imgSmallImageProduct);

            binding.txtProductName.setText(product.getName());
            binding.txtProductPrice.setText(product.getPrice() + " VND");
            binding.txtProductCount.setText("Số lượng: " + product.getCount());
            binding.txtProductInformation.setText(product.getInformation());
            binding.toolBarTitle.setText(product.getName());
        }

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Contants.Cart);
                product.setCheck_id(id);
                String id_cart = mDatabase.push().getKey();
                product.setId_cart(id_cart);
                mDatabase.child(id_cart).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DetailProductActivity.this, "Successfully!!!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
            }
        });

        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailProductActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_payment, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCancelable(false);
                TextView txtName = dialogView.findViewById(R.id.txtNameSp);
                TextView txtGiaSp = dialogView.findViewById(R.id.txtGiaSp);
                EditText edtDiaChi = dialogView.findViewById(R.id.edtDiaChi);
                EditText edtSDT = dialogView.findViewById(R.id.edtSDT);
                TextView txtSoLuong = dialogView.findViewById(R.id.txtSL);
                ImageView imgTang = dialogView.findViewById(R.id.imgTang);
                ImageView imgGiam = dialogView.findViewById(R.id.imgGiam);
                TextView txtThanhTien = dialogView.findViewById(R.id.money);
                Button btnPayment = dialogView.findViewById(R.id.btnMuaHang);
                Button btnCancel = dialogView.findViewById(R.id.btnCancelPay);

                txtName.setText("Sản phẩm: " + product.getName());
                txtGiaSp.setText("Giá: " + product.getPrice() + " VND");
                txtThanhTien.setText("Thành tiền: " + product.getPrice() + " VND");
                imgTang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        soluong = Integer.parseInt(txtSoLuong.getText().toString()) + 1;
                        long money = soluong * product.getPrice();

                        txtSoLuong.setText(soluong + "");
                        txtThanhTien.setText("Thành tiền: " + money + " VND");
                    }
                });

                imgGiam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        soluong = Integer.parseInt(txtSoLuong.getText().toString()) - 1;

                        if (soluong < 1) {
                            soluong = 1;
                        }

                        long money = soluong * product.getPrice();

                        txtSoLuong.setText(soluong + "");
                        txtThanhTien.setText("Thành tiền: " + money + " VND");
                    }
                });

                btnPayment.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (!edtSDT.getText().toString().isEmpty() && !edtDiaChi.getText().toString().isEmpty()) {
                            DatabaseReference mDatabasePayment = FirebaseDatabase.getInstance().getReference(Contants.Payment);
                            String id_pay = mDatabasePayment.push().getKey();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            Payment payment = new Payment(id_pay, product.getName(), edtDiaChi.getText().toString(), edtSDT.getText().toString(),
                                    Integer.parseInt(txtSoLuong.getText().toString()) * product.getPrice(), product.getSmallImageUrl(), id, dtf.format(now), Integer.parseInt(txtSoLuong.getText().toString()));

                            mDatabasePayment.child(id_pay).setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(DetailProductActivity.this, "Successfully!!!", Toast.LENGTH_SHORT).show();
                                        Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(intent1);
                                        try {
                                            if (!id_cart.equals("")) {

                                                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Cart);
                                                mDatabase1.child(product.getId_cart()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            //  Toast.makeText(DetailProductActivity.this, "Successfully Deleted !!!", Toast.LENGTH_SHORT).show();
                                                            //    finish();
                                                        }
                                                    }
                                                });

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(DetailProductActivity.this, "Vui lòng nhập đủ thông tin !!!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Comment infor = ds.getValue(Comment.class);
                        list.add(infor);
                    }

                    List<Comment> commentList = new ArrayList<>();

                    for (Comment comment : list) {
                        if (comment.getProduct().equals(product.getId())) {
                            commentList.add(comment);
                        }
                    }

                    AdapterComment adapterComment = new AdapterComment(getApplicationContext(), commentList);
                    binding.rclComment.setAdapter(adapterComment);
                    binding.rclComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.fabComment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailProductActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_comment, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCancelable(false);
                Button btnBinhLuan = dialogView.findViewById(R.id.btnComment);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);
                EditText edtBinhLuan = dialogView.findViewById(R.id.edtComment);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                btnBinhLuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = mDatabase1.push().getKey();
                        Comment comment = new Comment(id, edtBinhLuan.getText().toString(), product.getId(), dtf.format(now), getIntent().getStringExtra("name"));
                        mDatabase1.child(id).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    alertDialog.dismiss();
                                }
                            }
                        });
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();


            }
        });

    }
}