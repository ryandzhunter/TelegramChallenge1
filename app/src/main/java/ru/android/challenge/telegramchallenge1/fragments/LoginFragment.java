package ru.android.challenge.telegramchallenge1.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ru.android.challenge.telegramchallenge1.CountryManager;
import ru.android.challenge.telegramchallenge1.CountrySelectionActivity;
import ru.android.challenge.telegramchallenge1.entities.Country;
import ru.android.challenge.telegramchallenge1.views.PhoneCodeEditText;
import ru.android.challenge.telegramchallenge1.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnPhoneNumberListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private static final String COUNTRY_NAME = "country_name";
    private static final String PHONE_CODE = "phone_code";


    private OnPhoneNumberListener mListener;
    private Context mContext;
    private Button countryButton;
    private EditText phoneCodeEdit;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        countryButton = (Button) view.findViewById(R.id.countryButton);
        phoneCodeEdit = (PhoneCodeEditText) view.findViewById(R.id.countryCode);
        EditText phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        onChangeCountry();
        phoneCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || s.charAt(0) != '+') {
                    phoneCodeEdit.setText("+" + s.toString());
                    phoneCodeEdit.setSelection(1);
                    return;
                } else {
                    onChangePhoneCode(s.toString().substring(1, s.length()));
                }
            }
        });

        phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String phoneNumber = phoneCodeEdit.getText().toString() + v.getText();
                onButtonPressed(phoneNumber);
                return true;
            }
        });
        Button countryListButton = (Button) view.findViewById(R.id.countryButton);
        mContext = getActivity();
        countryListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CountrySelectionActivity.class);
                getActivity().startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    private void onChangePhoneCode(String code) {
        if (code.length() == 0) {
            countryButton.setText(getString(R.string.choose_a_country));
            return;
        }
        ArrayList<Country> countries = CountryManager.getCountries(mContext);
        for (int i = countries.size() - 1; i >= 0; --i) {
            if (countries.get(i).getPhoneCode().equals(code)) {
                CountryManager.setCurrentCountry(countries.get(i));
                countryButton.setText(CountryManager.getCurrentCountry().getCountryName());
                return;
            }
        }
        countryButton.setText(getString(R.string.wrong_country_code));
    }

    public void onChangeCountry() {
        phoneCodeEdit.setText("+" + CountryManager.getCurrentCountry().getPhoneCode());
        countryButton.setText(CountryManager.getCurrentCountry().getCountryName());
    }

    public void onButtonPressed(String phone) {
        if (mListener != null) {
            mListener.onPhoneNumberChanging(phone);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPhoneNumberListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCodeReceiveListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPhoneNumberListener {
        void onPhoneNumberChanging(String phone);
    }

}
