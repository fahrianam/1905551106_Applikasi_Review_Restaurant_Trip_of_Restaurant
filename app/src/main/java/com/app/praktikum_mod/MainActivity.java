package com.app.praktikum_mod;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList idList;
    private ArrayList namaList;
    private ArrayList alamatList;
    private ArrayList jenisList;
    private ArrayList fasilitasList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Button button;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idList = new ArrayList<>();
        namaList = new ArrayList<>();
        alamatList = new ArrayList<>();
        jenisList = new ArrayList<>();
        fasilitasList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        button = findViewById(R.id.addRes);
        noData = findViewById(R.id.noData);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), button);
                dropDownMenu.getMenuInflater().inflate(R.menu.dropdown_menu, dropDownMenu.getMenu());
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().toString().equals("Add Restaurant")){
                            Intent intent = new Intent(MainActivity.this, AddRestaurant.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(MainActivity.this, AddReview.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });

        getData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RestaurantViewAdapter(idList, namaList, alamatList, jenisList, fasilitasList);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() != 0){
            noData.setVisibility(View.GONE);
        }
    }

    @SuppressLint("Recycle")
    protected void getData(){
        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.getAllRestaurant();
        cursor.moveToFirst();
        for(int count=0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);
            idList.add(cursor.getInt(0));
            namaList.add(cursor.getString(1));
            alamatList.add(cursor.getString(2));
            jenisList.add(cursor.getString(3));
            fasilitasList.add(cursor.getString(4));
        }
    }
}