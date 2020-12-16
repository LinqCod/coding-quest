package com.linqcod.codingquest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private static final int MSG_TYPE_FRIEND = 0;
    private static final int MSG_TYPE_PLAYER = 1;

    private ArrayList<Message> messages;
    Context context;

    public ChatAdapter(Context ct, ArrayList<Message> messages) {
        context = ct;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        if(viewType == MSG_TYPE_FRIEND) {
            view = inflater.inflate(R.layout.friend_msg_box, parent, false);
        }
        else if(viewType == MSG_TYPE_PLAYER) {
            view = inflater.inflate(R.layout.player_msg_box, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.msgTV.setText(messages.get(position).getContent());
        if(messages.get(holder.getAdapterPosition()).getUserId() == 0 && messages.get(holder.getAdapterPosition()).getImageUri() != null) holder.imageIV.setImageResource(messages.get(position).getImageUri());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getUserId() == 0) return MSG_TYPE_FRIEND;
        else if(messages.get(position).getUserId() == 1) return MSG_TYPE_PLAYER;
        return super.getItemViewType(position);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView msgTV;
        ImageView imageIV;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            msgTV = itemView.findViewById(R.id.textMsg);
            imageIV = itemView.findViewById(R.id.imageMsg);
        }
    }
}
