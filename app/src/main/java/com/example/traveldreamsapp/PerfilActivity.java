package com.example.traveldreamsapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;

public class PerfilActivity extends AppCompatActivity {

    private TextView tvName, tvSurname, tvEmail, tvPhone;
    private Button btnEditData, btnLogout;
    private ShapeableImageView profileImage;

    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_PERMISSION_READ_STORAGE = 1;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar SessionManager
        sessionManager = new SessionManager(this);

        // Referencias a los TextView
        tvName = findViewById(R.id.tv_name);
        tvSurname = findViewById(R.id.tv_full_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhone = findViewById(R.id.tv_phone);

        // Referencia al botón "Modificar Datos"
        btnEditData = findViewById(R.id.btn_edit_data);

        // Referencia al botón "Cerrar Sesión"
        btnLogout = findViewById(R.id.buttonLogout);

        // Imagen de perfil
        profileImage = findViewById(R.id.profile_image);

        // Cargar imagen guardada si existe
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUriString = sharedPreferences.getString("profileImageUri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            profileImage.setImageURI(imageUri);
        }

        // Click en imagen para abrir galería y cambiar imagen
        profileImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(PerfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PerfilActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_STORAGE);
            } else {
                openGallery();
            }
        });

        // Click en botón para editar datos
        btnEditData.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilActivity.this, EditDataActivity.class);
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("surname", tvSurname.getText().toString());
            intent.putExtra("email", tvEmail.getText().toString());
            intent.putExtra("phone", tvPhone.getText().toString());
            startActivityForResult(intent, 1);
        });

        // Click en botón para cerrar sesión
        btnLogout.setOnClickListener(v -> logout());
    }

    // Abrir galería para seleccionar imagen
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    // Manejo resultado de actividades (editar datos o seleccionar imagen)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            profileImage.setImageURI(selectedImage);

            // Guardar URI en SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profileImageUri", selectedImage.toString());
            editor.apply();
        }

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            tvName.setText(data.getStringExtra("name"));
            tvSurname.setText(data.getStringExtra("surname"));
            tvEmail.setText(data.getStringExtra("email"));
            tvPhone.setText(data.getStringExtra("phone"));
        }
    }

    // Manejo de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permiso denegado para leer el almacenamiento externo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para cerrar sesión
    private void logout() {
        // Limpiar la sesión usando SessionManager
        sessionManager.clearSession();

        // Redirigir al LoginActivity y limpiar el stack
        Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
