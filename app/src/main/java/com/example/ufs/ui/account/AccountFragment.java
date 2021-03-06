package com.example.ufs.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Context ctx = getActivity().getApplicationContext();

        TextView fName = (TextView) view.findViewById(R.id.accountFirstName);
        TextView lName = (TextView) view.findViewById(R.id.accountLastName);
        TextView email = (TextView) view.findViewById(R.id.accountEmail);
        TextView isStudent = (TextView) view.findViewById(R.id.tv_account_isStudent);

        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        fName.setText(sp.getUserFName());
        lName.setText(sp.getUserLName());
        email.setText(sp.getUserEmail());
        isStudent.setText(sp.getIsStudent() ? "Student" : "Restaurant");

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, LoginActivity.class);
                startActivity(i);

                // When logging out update shared preferences
                SharedPreferences sharedPref = ctx.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putBoolean("isLoggedIn", false);
                editor.putInt("userId", -1);
                editor.putString("userFName", "");
                editor.putBoolean("isStudent", false);

                editor.apply();
            }
        });


        return view;

    }
}