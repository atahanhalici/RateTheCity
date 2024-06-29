package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserFilterActivity extends AppCompatActivity {
    private RelativeLayout panelLayout;
    private RelativeLayout titleLayout;
    String username, gender;
    EditText editText;
    RadioButton radioButton1,radioButton2,radioButton3;
    RadioGroup radioGroup;
    DatabaseHelper dbHelper;
    List<Map<String, String>> filteredUsers;
    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter);
        getSupportActionBar().hide();
        panelLayout = findViewById(R.id.panelLayout);
        titleLayout = findViewById(R.id.titleLayout);
        radioButton1 = findViewById(R.id.radioButtonOption1);
        radioButton2 = findViewById(R.id.radioButtonOption2);
        radioButton3 = findViewById(R.id.radioButtonOption3);
        radioGroup=findViewById(R.id.radioGroupOptions);
        editText = findViewById(R.id.editTextFilter);
       filteredUsers= new ArrayList<>();
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panelin görünürlüğünü değiştir
                if (panelLayout.getVisibility() == View.VISIBLE) {
                    panelLayout.setVisibility(View.GONE);
                } else {
                    panelLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    username="";
    gender="";
         dbHelper = new DatabaseHelper(this);

        radioButton3.setChecked(true);
        filteredUsers = dbHelper.filterUsers(username, gender);
        listView=findViewById(R.id.listview);
        arrayAdapter   = new ArrayAdapter(UserFilterActivity.this,android.R.layout.simple_list_item_1,filteredUsers.stream().map(userMap -> userMap.get("username")).collect(Collectors.toList()));
         listView.setAdapter(arrayAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Metin değişmeden önceki durum
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 username = s.toString();
                filteredUsers = dbHelper.filterUsers(username, gender);
                arrayAdapter   = new ArrayAdapter(UserFilterActivity.this,android.R.layout.simple_list_item_1,filteredUsers.stream().map(userMap -> userMap.get("username")).collect(Collectors.toList()));
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Metin değiştikten sonra yapılacak işlemler
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Seçilen RadioButton'un ID'sini kontrol edin ve işlemleri gerçekleştirin
                switch (checkedId) {
                    case R.id.radioButtonOption1:
                        gender="Man";
                        filteredUsers = dbHelper.filterUsers(username, gender);
                        arrayAdapter   = new ArrayAdapter(UserFilterActivity.this,android.R.layout.simple_list_item_1,filteredUsers.stream().map(userMap -> userMap.get("username")).collect(Collectors.toList()));
                        listView.setAdapter(arrayAdapter);
                        break;
                    case R.id.radioButtonOption2:
                        gender="Woman";
                        filteredUsers = dbHelper.filterUsers(username, gender);
                        arrayAdapter   = new ArrayAdapter(UserFilterActivity.this,android.R.layout.simple_list_item_1,filteredUsers.stream().map(userMap -> userMap.get("username")).collect(Collectors.toList()));
                        listView.setAdapter(arrayAdapter);
                        break;
                    case R.id.radioButtonOption3:
                        gender="";
                        filteredUsers = dbHelper.filterUsers(username, gender);
                        arrayAdapter   = new ArrayAdapter(UserFilterActivity.this,android.R.layout.simple_list_item_1,filteredUsers.stream().map(userMap -> userMap.get("username")).collect(Collectors.toList()));
                        listView.setAdapter(arrayAdapter);
                        break;
                    default:
                        // Hiçbir RadioButton seçilmediğinde yapılacak işlemler
                        break;
                }
            }
        });

        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Tıklanan bölge panelLayout'un dışında ise panelLayout'u kapat
                if (event.getAction() == MotionEvent.ACTION_DOWN && panelLayout.getVisibility() == View.VISIBLE) {
                    int[] location = new int[2];
                    panelLayout.getLocationOnScreen(location);
                    Rect rect = new Rect(location[0], location[1], location[0] + panelLayout.getWidth(), location[1] + panelLayout.getHeight());
                    if (!rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        panelLayout.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
    }
}
