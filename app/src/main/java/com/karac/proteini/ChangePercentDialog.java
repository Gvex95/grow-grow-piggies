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
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ChangePercentDialog extends DialogFragment {
    private TextView textView;
    private EditText editText;
    private boolean percentError = false;
    private ChangePercentDialogListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_percente, null);
        String cerealName = getArguments().getString("cerealName");

        builder.setView(view)
                .setTitle("Promenite procenat proteina")
                .setNegativeButton("otkazi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("sacuvaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPercent = editText.getText().toString();
                        if (newPercent.equals("")){
                            newPercent = "0";
                        }
                        float p = Float.parseFloat(newPercent);
                        if (!percentError){
                            listener.changePercent(p);
                        }
                    }
                });

        textView = (TextView) view.findViewById(R.id.changePercentCerealName);
        textView.setText(cerealName);
        editText = (EditText) view.findViewById(R.id.changePercentNewValue);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editText.getText().toString().equals("")){
                    float p = Float.parseFloat(editText.getText().toString());
                    if (p > 100.0){
                        showError(editText, "Novi procenat ne sme biti veci od 100");
                    }else{
                        disableError(editText);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return builder.create();
    }

    public interface ChangePercentDialogListener{
        void changePercent(float newPercent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ChangePercentDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement change percent dialog");
        }
    }

    private void disableError(EditText editText){
        percentError = false;
        editText.setError(null);
    }

    private void showError(EditText editText, String errorMessage){
        percentError = true;
        editText.setError(errorMessage);
    }
}
