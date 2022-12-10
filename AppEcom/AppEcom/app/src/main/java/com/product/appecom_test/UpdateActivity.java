package com.product.appecom_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.product.appecom_test.databinding.ActivityUpdateBinding;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;
    Product product;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    int SELECT_IMAGE = 123;
    int SELECT_IMAGE1 = 100;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if (intent != null) {
            product = (Product) intent.getSerializableExtra("object");

            binding.edtTenSPAdd.setText(product.getName());
            binding.edtDai.setText(product.getInformation());
            binding.edtGiaAdd.setText(product.getPrice() + "");
            binding.edtShoes.setText(product.getInfoShoes());
            binding.edtSLAdd.setText(product.getCount() + "");

            Glide.with(getApplicationContext()).load(product.getBigImageUrl()).error(R.mipmap.ecom_logo).into(binding.imgBig);
            Glide.with(getApplicationContext()).load(product.getSmallImageUrl()).error(R.mipmap.ecom_logo).into(binding.imgSmall);
            urlSmall = product.getSmallImageUrl();
            urlBig = product.getBigImageUrl();

        }

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.imgSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
            }
        });

        binding.imgBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE1);
            }
        });

        binding.btnAddPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnAddPro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Contants.Product);
                        HashMap map = new HashMap();
                        map.put("name", binding.edtTenSPAdd.getText().toString());
                        map.put("price", Integer.parseInt(binding.edtGiaAdd.getText().toString()));
                        map.put("information", binding.edtDai.getText().toString());
                        map.put("infoShoes", binding.edtShoes.getText().toString());
                        map.put("bigImageUrl", urlBig);
                        map.put("smallImageUrl", urlSmall);
                        map.put("count", Integer.parseInt(binding.edtSLAdd.getText().toString()));

                        mDatabase.child(product.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                                    UpdateActivity.this.finish();
                                } else {
                                    Toast.makeText(UpdateActivity.this, "Error !!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(Contants.Product);
                mDatabase1.child(product.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateActivity.this, "Successfully Deleted !!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });

    }

    String urlSmall = "", urlBig = "";

    private void uploadToFirebase(Uri uri) {
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext())
                                .load(uri.toString())
                                .error(R.mipmap.ecom_logo)
                                .into(binding.imgSmall);
                        urlSmall = uri.toString();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void uploadToFirebaseBig(Uri uri) {
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext())
                                .load(uri.toString())
                                .error(R.mipmap.ecom_logo)
                                .into(binding.imgBig);
                        urlBig = uri.toString();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    uploadToFirebase(selectedImage);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(UpdateActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECT_IMAGE1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    uploadToFirebaseBig(selectedImage);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(UpdateActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}