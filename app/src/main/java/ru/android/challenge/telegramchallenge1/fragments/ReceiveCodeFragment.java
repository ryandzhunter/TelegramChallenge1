package ru.android.challenge.telegramchallenge1.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ru.android.challenge.telegramchallenge1.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCodeReceiveListener} interface
 * to handle interaction events.
 * Use the {@link ReceiveCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiveCodeFragment extends Fragment {

    private OnCodeReceiveListener mListener;
    private TextView noteTextWithPhone;
    private EditText codeEdit;
    private CountDownTimer timer;
    private TextView callMeWithTimer;
    private String callMe;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReceiveCodeFragment.
     */
    public static ReceiveCodeFragment newInstance() {
        return new ReceiveCodeFragment();
    }

    public ReceiveCodeFragment() {
    }

    public void resetInstance(String phone) {
        noteTextWithPhone.setText(Html.fromHtml(String.format(getResources().getString(R.string.sms_note_receive), phone)));
        timer = new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {
                String formattedTime = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                callMeWithTimer.setText(String.format(callMe, formattedTime));
            }

            public void onFinish() {
                callMeWithTimer.setText("done!");
            }
        };
        timer.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_code, container, false);
        noteTextWithPhone = (TextView) view.findViewById(R.id.noteTextWithPhone);
        codeEdit = (EditText) view.findViewById(R.id.codeEdit);
        callMeWithTimer = (TextView) view.findViewById(R.id.callMeWithTimer);
        Button invalidNumberButton = (Button) view.findViewById(R.id.invalidNumberButton);
        invalidNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWrongButtonPressed();
            }
        });

        return view;
    }


    public void onWrongButtonPressed() {
        if (mListener != null) {
            timer.cancel();
            mListener.onWrongNumber();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callMe = getResources().getString(R.string.call_me);
            mListener = (OnCodeReceiveListener) activity;
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
    public interface OnCodeReceiveListener {
        void onCodeReceive(String code);
        void onWrongNumber();
    }
}
