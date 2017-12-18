package com.example.jennifer.finalproject;

import android.app.Activity;
import android.os.Bundle;

public class InstructionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        InstructionsFragment instructions = new InstructionsFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container1, instructions).commit();
    }
}
