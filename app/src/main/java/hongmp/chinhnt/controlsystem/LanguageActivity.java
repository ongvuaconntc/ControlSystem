package hongmp.chinhnt.controlsystem;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import hongmp.chinhnt.controlsystem.net.Configuration;

public class LanguageActivity extends AppCompatActivity {

    private Button btnVie;
    private Button btnEng;
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        btnVie = (Button) findViewById(R.id.vie_lang);

        btnEng = (Button) findViewById(R.id.eng_lang);
        btnVie.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                setLocale("vi");
            }
        });

        btnEng.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                setLocale("en");
            }
        });
    }


    public void setLocale(String lang){
        myLocale = new Locale(lang);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        android.content.res.Configuration configuration = resources.getConfiguration();
        configuration.locale = myLocale;
        resources.updateConfiguration(configuration,displayMetrics);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
