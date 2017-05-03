package com.example.ivonneortega.the_news_project.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.ivonneortega.the_news_project.R;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch mSwitch_theme;

    public static final String THEME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSwitch_theme = (Switch) findViewById(R.id.switch_theme);
        mSwitch_theme.setOnCheckedChangeListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(SettingsActivity.THEME,"DEFAULT"); //Initial value of the String is "Hello"
        if(str.equals("dark"))
        {
            mSwitch_theme.setChecked(true);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId())
        {
            case R.id.switch_theme:
                if(isChecked)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(THEME,"dark");
                    editor.commit();
                }
                else
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(THEME,"light");
                    editor.commit();
                }
                break;
        }
    }
}
