package com.example.jennifer.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


public class EditFragment extends Fragment {
    private static final String ACTIVITY_NAME = "EditFragment";
    String id;
    int resultCode;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_ITEM_ID = "item_id";
   // private static final String ARG_PARAM2 = "param2";

    /*// TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/


    public EditFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("EditFragment", "In onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("EditFragment", "In onCreateView()");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        String day = getArguments().getString("dayofweek");
        String time = getArguments().getString("time");
        String temp = getArguments().getString("temperature");
        id = getArguments().getString("id");
        Log.i(ACTIVITY_NAME, "id " + id);
        resultCode = Integer.parseInt(id);
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBarEdit);
        progressBar.setVisibility(View.INVISIBLE);
        ((EditText)view.findViewById(R.id.dayOfWeekEdit)).setText(day);
        ((EditText)view.findViewById(R.id.timeEdit)).setText(time);
        ((EditText)view.findViewById(R.id.temperatureEdit)).setText(temp);
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "delete button clicked");
                msgListener.onMessageSelected(resultCode);

                getActivity().setResult(resultCode);
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("EditFragment", "In onAttach()");
        if (context instanceof OnMessageSelectedListener) {
            this.msgListener = (OnMessageSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        msgListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    OnMessageSelectedListener msgListener;

    public interface OnMessageSelectedListener {
        void onMessageSelected(int position);

    }
}
