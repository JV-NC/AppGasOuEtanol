package br.com.jvn.appgaseta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.jvn.appgaseta.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void onUserInteraction() {
        finish();
    }
}