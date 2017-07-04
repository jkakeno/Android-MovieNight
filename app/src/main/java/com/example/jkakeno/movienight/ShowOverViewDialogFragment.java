package com.example.jkakeno.movienight;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class ShowOverViewDialogFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
//Get the overview from the clicked recycler view holder
        Bundle bundle = getArguments();
        String overview = bundle.getString("overview");
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.show_overview))
                .setMessage(overview)
                .setPositiveButton(context.getString(R.string.ok_button_text),null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
