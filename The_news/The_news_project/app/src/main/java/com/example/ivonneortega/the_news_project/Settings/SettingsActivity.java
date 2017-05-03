package com.example.ivonneortega.the_news_project.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.ivonneortega.the_news_project.R;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch mSwitch_theme, mSwitch_notification;

    public static final String THEME = "theme";
    public static final String NOTIFICATION = "notification";
    public static final int TRUE = 0;
    public static final int FALSE = 1;
    public ImageView mBack;
    private boolean mStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
//        setContentView(R.layout.activity_settings);

        mSwitch_theme = (Switch) findViewById(R.id.switch_theme);
        mSwitch_notification = (Switch) findViewById(R.id.switch_notification);
        mSwitch_theme.setOnCheckedChangeListener(this);
        mSwitch_notification.setOnCheckedChangeListener(this);
        mBack = (ImageView) findViewById(R.id.back_toolbar);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(SettingsActivity.THEME,"DEFAULT");
        if(str.equals("dark"))
        {
            mSwitch_theme.setChecked(true);
        }

        int notification = sharedPreferences.getInt(NOTIFICATION,TRUE);
        if(notification==TRUE)
            mSwitch_notification.setChecked(true);
        else
            mSwitch_notification.setChecked(false);


    }

    public void setTheme()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(SettingsActivity.THEME,"DEFAULT"); //Initial value of the String is "Hello"
        if(str.equals("dark"))
        {
            setTheme(R.style.DarkTheme);
            setContentView(R.layout.activity_settings);
            findViewById(R.id.root_toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkTheme));
        }
        else
        {
            setContentView(R.layout.activity_settings);

        }
        mStartActivity=true;

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

            case R.id.switch_notification:
                if(isChecked)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(NOTIFICATION,TRUE);
                    editor.commit();
                }
                else
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(NOTIFICATION,FALSE);
                    editor.commit();
                }
        }
    }
}
