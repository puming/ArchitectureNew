package com.pm.an.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.common.widget.AppBar;
import com.common.widget.BannerView;
import com.common.widget.ImageTextCell;
import com.pm.an.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        AppBar appBar = root.findViewById(R.id.appBar);
        appBar.showAppbarMenuIcon(true).setAppbarMenuIcon(R.drawable.ic_setting);
        BannerView bannerView = root.findViewById(R.id.banner);
        ImageTextCell optimalAppItemView = root.findViewById(R.id.image_text);
        optimalAppItemView.setIcon(R.mipmap.ic_launcher);
//        optimalAppItemView.setTitle("标题");
        ArrayList<String> list = new ArrayList<>(4);
        list.add("http://pic44.nipic.com/20140723/18505720_094503373000_2.jpg");
        list.add("http://pic44.nipic.com/20140723/18505720_094503373000_2.jpg");
        list.add("http://pic44.nipic.com/20140723/18505720_094503373000_2.jpg");
        list.add("http://pic44.nipic.com/20140723/18505720_094503373000_2.jpg");
        bannerView.addData(list);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}