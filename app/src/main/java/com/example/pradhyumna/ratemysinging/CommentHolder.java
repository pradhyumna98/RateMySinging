package com.example.pradhyumna.ratemysinging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


class CommentHolder extends RecyclerView.ViewHolder {

    public TextView tvname , tvdate , tvcomment;

    public CommentHolder(@NonNull View itemView) {
        super(itemView);

        tvcomment = itemView.findViewById(R.id.comment);
        tvdate = itemView.findViewById(R.id.date);
        tvname = itemView.findViewById(R.id.name);
    }
}
