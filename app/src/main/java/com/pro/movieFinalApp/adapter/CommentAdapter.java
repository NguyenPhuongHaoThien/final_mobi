package com.pro.movieFinalApp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.movieFinalApp.model.Comment;
import com.pro.movieFinalApp.R;
import java.util.List;



public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> mCommentList;

    public CommentAdapter(List<Comment> commentList) {
        mCommentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        holder.textViewUsername.setText(comment.getUserId());
        holder.textViewContent.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername;
        TextView textViewContent;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.text_view_username);
            textViewContent = itemView.findViewById(R.id.text_view_comment);
        }
    }
}
