package com.example.jennifer.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jennifer on 2017-12-06.
 */

public class InstructionsFragment extends Fragment {
    public InstructionsFragment(){

    }
    @Override
    public void onCreate(Bundle savedOnInstance){
        super.onCreate(savedOnInstance);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.instructions_fragment, container, false);
        Button closeButton = (Button) rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return rootView;
    }
}
