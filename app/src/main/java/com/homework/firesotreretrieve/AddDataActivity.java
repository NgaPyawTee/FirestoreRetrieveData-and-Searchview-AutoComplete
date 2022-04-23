package com.homework.firesotreretrieve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddDataActivity extends AppCompatActivity {
    EditText edtTitle, edtDescription, edtPriority, edtFood, edtDrink, edtToken;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        edtPriority = findViewById(R.id.edt_priority);
        edtFood = findViewById(R.id.edt_food);
        edtDrink = findViewById(R.id.edt_drink);
        edtToken = findViewById(R.id.token);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });
    }

    private void SaveData() {
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        String priority = edtPriority.getText().toString();
        String food = edtFood.getText().toString();
        String drink = edtDrink.getText().toString();
        String token = edtToken.getText().toString();

        FirebaseFirestore.getInstance().collection("Notebook")
                .add(new Tutorial(title,description,priority,food,drink,token))
                .addOnSuccessListener(this, new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                    }
                });
    }
}