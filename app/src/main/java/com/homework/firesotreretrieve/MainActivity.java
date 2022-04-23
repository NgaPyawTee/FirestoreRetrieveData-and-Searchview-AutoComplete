package com.homework.firesotreretrieve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Empty;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private FirebaseFirestore db;
    private List<Tutorial> list;
    private List<String> idList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private FloatingActionButton floatingBtn;

    private String getSentName;
    private String sentName;
    private ArrayAdapter<String> arrayAdapter;
    private SearchView.SearchAutoComplete searchAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        adapter = new RecyclerAdapter(MainActivity.this, list);
        recyclerView.setAdapter(adapter);

        adapter.setListener(new RecyclerAdapter.Listener() {
            @Override
            public void onClick(int position) {
                String s = idList.get(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.SNAPSHOT_ID, s);
                startActivity(intent);
            }
        });

        floatingBtn = findViewById(R.id.floating_btn);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddDataActivity.class));
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.TRANSPARENT);
        searchAutoComplete.setTextColor(Color.GREEN);
        searchAutoComplete.setThreshold(1);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_dark);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getSentName = (String) adapterView.getItemAtPosition(i);
                searchAutoComplete.setText(getSentName);
                sentName = getSentName;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               /* String[] splitLetter = query.split(" ");
                StringBuilder b = new StringBuilder();
                for (String name : splitLetter) {
                    b.append(name.substring(0, 1).toUpperCase() + name.substring(1, name.length()) + " ");
                }
                String captailLetters = String.valueOf(b).trim(); */
                searchAutoComplete.setText("");
                if (nameList.contains(getSentName)){
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(DetailActivity.SUBMIT_NAME, sentName);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Check the search name again", Toast.LENGTH_SHORT).show();
                }
                
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onStart() {
        super.onStart();
        RetrieveData();
    }

    private void RetrieveData() {
        db.collection("Notebook").orderBy("token")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        idList.clear();
                        nameList.clear();
                        for (QueryDocumentSnapshot qs : value) {
                            Tutorial data = qs.toObject(Tutorial.class);
                            data.setId(qs.getId());
                            idList.add(data.getId());
                            list.add(data);
                            nameList.add(data.getTitle());
                        }
                        adapter.notifyDataSetChanged();
                        searchAutoComplete.setAdapter(arrayAdapter);
                    }
                });
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
    }
}

