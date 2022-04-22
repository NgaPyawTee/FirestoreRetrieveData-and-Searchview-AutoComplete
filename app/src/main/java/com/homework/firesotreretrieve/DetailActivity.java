package com.homework.firesotreretrieve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView title, description, priority, food, drink, token;
    Button btnDelete;
    public static final String SNAPSHOT_ID = "ID";
    public static final String SUBMIT_NAME = "NAME";
    private CollectionReference collRef = FirebaseFirestore.getInstance().collection("Notebook");

    private String snapshotID;
    private String submitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        priority = findViewById(R.id.priority);
        food = findViewById(R.id.food);
        drink = findViewById(R.id.drink);
        token = findViewById(R.id.token_tv);

        btnDelete = findViewById(R.id.delete_btn);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        snapshotID = intent.getStringExtra("ID");
        submitName = intent.getStringExtra("NAME");

        if (submitName == null) {
            NofilterData();
        } else {
            SubmitData();
        }
    }

    private void SubmitData() {
        collRef.whereEqualTo("title", submitName)
                .get()
                .addOnSuccessListener(this, new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot : list) {
                            String strTitle = documentSnapshot.getString("title");
                            String strDescription = documentSnapshot.getString("description");
                            String strPriority = documentSnapshot.getString("priority");
                            String strFood = documentSnapshot.getString("food");
                            String strDrink = documentSnapshot.getString("drink");
                            String strToken = documentSnapshot.getString("token");

                            String id = documentSnapshot.getId();

                            title.setText(strTitle);
                            description.setText(strDescription);
                            priority.setText(strPriority);
                            food.setText(strFood);
                            drink.setText(strDrink);
                            token.setText(strToken);

                            btnDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    collRef.document(id).delete();
                                    finish();
                                }
                            });
                        }


                    }
                });
    }

    private void NofilterData() {
        collRef.document(snapshotID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String strTitle = documentSnapshot.getString("title");
                        String strDescription = documentSnapshot.getString("description");
                        String strPriority = documentSnapshot.getString("priority");
                        String strFood = documentSnapshot.getString("food");
                        String strDrink = documentSnapshot.getString("drink");
                        String strToken = documentSnapshot.getString("token");

                        title.setText(strTitle);
                        description.setText(strDescription);
                        priority.setText(strPriority);
                        food.setText(strFood);
                        drink.setText(strDrink);
                        token.setText(strToken);

                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                collRef.document(snapshotID).delete();
                                finish();
                            }
                        });
                    }
                });
    }
}