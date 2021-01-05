package com.karac.proteini;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddNewCerealDialog extends DialogFragment {
    private EditText cerealName;
    private EditText cerealPercent;
    private AddNewCerealListener listener;
    String mCerealName;
    String mCerealPercent;
    private boolean errorName = false;
    private boolean errorPercent = false;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_cereal, null);

        builder.setView(view)
                .setTitle("Dodaj novu")
                .setNegativeButton("otkazi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("sacuvaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_new_cereal();
                    }
                });

        cerealName = (EditText) view.findViewById(R.id.add_cereal_add_name);
        cerealPercent = (EditText) view.findViewById(R.id.add_cereal_add_percent);

        mCerealName = cerealName.getText().toString();
        if (mCerealName.length() == 0){
            showError(cerealName, "Morate uneti ime", 0);
        }
        mCerealPercent = cerealPercent.getText().toString();
        if (mCerealPercent.length() == 0){
            showError(cerealPercent, "Morate uneti procenat", 1);
        }




        cerealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCerealName = cerealName.getText().toString();
                if (mCerealName.equals("")){
                    showError(cerealName, "Morate uneti ime", 0);
                }else{
                    disableError(cerealName, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cerealPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCerealPercent = cerealPercent.getText().toString();

                if (mCerealPercent.equals("")){
                    showError(cerealPercent, "Morate uneti procenat", 1);
                }else{
                    float mCerealPercentAsFloat = Float.parseFloat(mCerealPercent);
                    if (mCerealPercentAsFloat > 100.0 ){
                        showError(cerealPercent, "Procenat ne moze biti veci od 100!", 1);
                    }else{
                        disableError(cerealPercent, 1);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return builder.create();
    }

    private void add_new_cereal(){
        if (!errorName && !errorPercent){
            listener.addCereal(mCerealName, mCerealPercent);
        }
    }
    private void disableError(EditText editText, int which){
        if (which == 0){
            errorName = false;
        }else if (which == 1){
            errorPercent = false;
        }
        editText.setError(null);
    }

    private void showError(EditText editText, String errorMessage, int which){
        if (which == 0){
            errorName = true;
        }else if (which == 1){
            errorPercent = true;
        }
        editText.setError(errorMessage);
    }

    public interface AddNewCerealListener{
        void addCereal(String name, String percent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddNewCerealListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement add new cereal listener");
        }
    }
}
