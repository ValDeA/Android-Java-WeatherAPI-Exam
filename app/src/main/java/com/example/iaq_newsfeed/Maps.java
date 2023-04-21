package com.example.iaq_newsfeed;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class Maps extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    RelativeLayout rl = findViewById(R.id.rl_bottom);

    BottomSheetBehavior<RelativeLayout> bottomSheetBehavior = BottomSheetBehavior.from(rl);


    new Thread(new Runnable() {
      @Override
      public void run() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            try {
              Thread.sleep(3000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
          }
        });
      }
    }).start();
  }
}