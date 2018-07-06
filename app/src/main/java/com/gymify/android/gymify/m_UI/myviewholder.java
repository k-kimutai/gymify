package com.gymify.android.gymify.m_UI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gymify.android.gymify.R;

public class myviewholder extends RecyclerView.ViewHolder {

    TextView namesets,namereps,namedate,nameworkout;
    public myviewholder(View itemView) {
        super(itemView);

        namesets= itemView.findViewById(R.id.namesets);
        namereps= itemView.findViewById(R.id.namereps);
        namedate= itemView.findViewById(R.id.namedate);
        nameworkout= itemView.findViewById(R.id.nameworkout);
    }
}
