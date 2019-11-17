package com.pm.an.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pm.an.R;
import com.pm.player.AudioActivity;
import com.pm.player.VideoActivity;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final Button textView = root.findViewById(R.id.btn_goto_video);
        textView.setOnClickListener(v -> startActivity(new Intent(getContext(), VideoActivity.class)));
        root.findViewById(R.id.btn_goto_audio).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AudioActivity.class));
        });
        dashboardViewModel.getText().observe(this, s -> {
        });
        return root;
    }
}