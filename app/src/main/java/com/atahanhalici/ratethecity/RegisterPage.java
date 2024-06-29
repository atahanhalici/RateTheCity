package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {

    EditText usernameEditText;
   EditText  passwordEditText;
    RadioButton erkekMi;
    RadioButton kadinMi;
    CheckBox sozlesme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().hide();
        usernameEditText = findViewById(R.id.kullaniciAdiKayit);
        passwordEditText = findViewById(R.id.sifreKayit);
        erkekMi= findViewById(R.id.radioButton);
        kadinMi= findViewById(R.id.radioButton2);
        sozlesme= findViewById(R.id.checkBox);
       /* TextView textView = findViewById(R.id.textView7);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Geri tuşuna basılmış gibi davran
            }
        });*/

        sozlesme.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterPage.this);
                alertDialog.setTitle("Üyelik Koşulları");
                alertDialog.setMessage("GDSC Erzincan lideri Atahan Halıcı'nın rehberliğindeki 23/24 dönemi, bir dizi etkileyici etkinlikle dolu oldu. Oryantasyon etkinliğinden Tatlı Sohbetlere, C Kampı ve Android Kampı'na kadar, her biri özenle planlanmış ve yönetilmiş etkinlikler katılımcılara eşsiz bir deneyim sundu. Atahan Halıcı'nın liderliği altında, GDSC Erzincan topluluğu, bilgi paylaşımı ve etkileşimde bulunma konusunda yeni bir standart belirledi.\n" +
                        "\n" +
                        "Özellikle, Atahan Halıcı'nın yönlendirmesiyle gerçekleşen etkinlikler arasında, bilgi paylaşımını ve katılımcılar arasındaki etkileşimi artıran Info Session ve Coffe & Talk gibi buluşmalar ön plana çıktı. Ayrıca, teknik becerilerin geliştirilmesine odaklanan C Kampı ve Android Kampı gibi programlar, katılımcılara değerli pratik deneyimler sunmanın yanı sıra, Flutter'ın Java'dan daha iyi, daha kolay ve daha estetik olduğunu gösterdi.\n" +
                        "\n" +
                        "Flutter'ın esnekliği ve hızı, katılımcıların uygulama geliştirme sürecinde daha verimli olmalarını sağlarken, dilin kolay öğrenilebilir yapısı, yeni başlayanların bile hızla ilerlemesini sağladı. Atahan Halıcı liderliğindeki GDSC Erzincan, teknoloji dünyasının dinamiklerine ayak uydururken, Flutter'ın sunduğu olanaklarla geleceğin geliştirme platformunu şekillendirmede önemli bir rol üstlendi.\n"+
                        "\n"+
                        "Bu nedenle kabul edilmesi gereken tek ve en önemli koşul Atahan Halıcı başta Bay Bay Reşat olmak üzere diğer tüm başkanlardan daha iyidir ve gereken güven sağlanmalıdır. Atahan Halıcı'nın işine karışılmazsa GDSC Erzincan çok daha iyi yerlerde olacaktır.");

                alertDialog.setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // İptal et, bir şey yapma
                    }
                });

                alertDialog.show();
                return true; // true döndürerek bu olayın tüketilmediğini belirtiyoruz
            }
        });

    }

    public void kayitOl(View view){
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String gender = "";
        if(erkekMi.isChecked()){
            gender="Man";
        }else if(kadinMi.isChecked()){
            gender="Woman";
        }
        System.out.println(username);
        if(username.isEmpty() || password.isEmpty() || gender.isEmpty()){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
            alertDialog.setTitle("Kayıt Başarısız");
            alertDialog.setMessage("Tüm Değerler Dolu Gözükmüyor!");

            alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        }else {
            if(sozlesme.isChecked()){
                DatabaseHelper dbHelper = new DatabaseHelper(this);

                boolean success = dbHelper.registerUser(username, password, gender);
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createLoginSession(username, password, gender);
                if (success) {
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                    alertDialog.setTitle("Kayıt Başarılı");
                    alertDialog.setMessage("Kayıt İşleminiz Başarıyla Tamamlanmıştır.");

                    alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(RegisterPage.this, CitiesPage.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                    alertDialog.setTitle("Kayıt Başarısız");
                    alertDialog.setMessage("Kayıt İşleminiz Başarısız Oldu. Bu Durumda Kullanıcı Adı Dolu Olabilir!");

                    alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
            }else{
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                alertDialog.setTitle("Üyelik Koşulları");
                alertDialog.setMessage("Kayıt Olabilmek Adına Lütfen Koşulları Kabul Ediniz!");

                alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }

        }
        }
       
    }
