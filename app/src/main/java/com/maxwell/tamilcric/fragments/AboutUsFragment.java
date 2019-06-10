package com.maxwell.tamilcric.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maxwell.tamilcric.R;

public class AboutUsFragment extends Fragment {

    View view;
ImageView iv_playstore,iv_appstore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_about_us,container,false);

        iv_playstore=(ImageView)view.findViewById(R.id.imageview_google_play);
        iv_appstore=(ImageView)view.findViewById(R.id.imageview_app_store);

        iv_playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.maxwell.tamilcric&hl=en"));
                startActivity(browserIntent);
            }
        });
        iv_appstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/tamilcric/id1460644373?ls=1&mt=8"));
                startActivity(browserIntent);
            }
        });

        return view;
    }
}
