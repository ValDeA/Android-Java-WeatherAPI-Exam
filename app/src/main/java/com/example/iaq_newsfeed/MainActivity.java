package com.example.iaq_newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
  static final String TAG = "MainActivity";

  NewsfeedThread thread;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btnNewsfeed = findViewById(R.id.btn_Newsfeed);
    Button btnRun = findViewById(R.id.btn_run);
    Button btnMap = findViewById(R.id.btnMap);
    TextView tvNewsfeed = findViewById(R.id.tv_Newfeed);
    EditText etKeyword = findViewById(R.id.et_keyword);

    getAppKeyHash();

    btnRun.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        thread = new NewsfeedThread();
        thread.setKeyword(etKeyword.getText().toString());
        thread.start();
      }
    });
    btnNewsfeed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        tvNewsfeed.setText(thread.getRecvData());
      }
    });
    btnMap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), Maps.class);
        startActivity(intent);
      }
    });
  }

  private void getAppKeyHash() {
    try {
      PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
      for (Signature signature : info.signatures) {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA");
        md.update(signature.toByteArray());
        String something = new String(Base64.encode(md.digest(), 0));
        Log.d("yyg", "key: " + something);
      }
    } catch (Exception e) { // TODO Auto-generated catch block Log.e("name not found", e.toString()); } }

    }
  }
}