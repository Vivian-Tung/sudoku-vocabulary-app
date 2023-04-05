package com.example.sudokuvocabulary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExitGameDialogFragment extends DialogFragment {

    static ExitGameDialogFragment newInstance(int num) {
        ExitGameDialogFragment f = new ExitGameDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_exit_game)
                .setPositiveButton(R.string.exit, (dialog, id) -> {
                    Intent intent = new Intent(getContext(), MainMenuActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}