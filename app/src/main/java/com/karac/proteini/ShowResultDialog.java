package com.karac.proteini;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ShowResultDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.result, null);

        float totalMass = getArguments().getFloat("totalMass");
        float totalProteinMass = getArguments().getFloat("totalProteinMass");
        float totalProteinPercent = getArguments().getFloat("totalProteinPercent");

        builder.setView(view)
                .setTitle("Rezultat")
                .setNegativeButton("potvrdi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        TextView textView = (TextView) view.findViewById(R.id.result);
        textView.setText(String.format("Ukupna masa smese je: %s grama \nUkupna masa proteina u smesi je: %s grama \nUkupan procenat proteina u smesi je: %s %%", totalMass, totalProteinMass, totalProteinPercent));

        return builder.create();
    }
}
