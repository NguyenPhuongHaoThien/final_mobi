package com.pro.movieFinalApp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.pro.movieFinalApp.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pro.movieFinalApp.model.Comment;
import com.pro.movieFinalApp.adapter.CommentAdapter;
import java.util.ArrayList;
import java.util.List;
public class CommentFragment extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    private RecyclerView mRecyclerView;
    private EditText mEditTextComment;

    private List<Comment> mCommentList = new ArrayList<>();
    private CommentAdapter mCommentAdapter;

    public CommentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mEditTextComment = view.findViewById(R.id.edit_text_comment);

        mCommentAdapter = new CommentAdapter(mCommentList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommentAdapter);

        mEditTextComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String comment = mEditTextComment.getText().toString().trim();

                    if (!TextUtils.isEmpty(comment)) {
                        String userId = mCurrentUser.getUid();
                        String movieId = "6"; // thay đổi movieId bằng movie được chọn

                        Comment newComment = new Comment(userId, movieId, comment);
                        mDatabase.child("comment").push().setValue(newComment);

                        mEditTextComment.setText("");
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mEditTextComment.getWindowToken(), 0);
                    }

                    return true;
                }

                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabase.child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCommentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    mCommentList.add(comment);
                }

                mCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}

