package com.example.chatsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsapp.databinding.ItemRecieveBinding;
import com.example.chatsapp.databinding.ItemSendBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int ITEM_SENT = 1;
    final int ITEM_RECIEVE = 2;

    public MessagesAdapter(Context context, ArrayList<Message> messages  ){
    this.context = context;
    this.messages = messages;
    }

    //Since we have two sample layouts in this case we can not directly inflate both in the onCreate
    //ViewHolder function

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT){
             View view = LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
             return new SentViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_recieve,parent,false);
            return new SentViewHolder(view);

        }

    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderID())){
            return ITEM_SENT;
        }
        else{
            return ITEM_RECIEVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if(holder.getClass() == SentViewHolder.class){
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.binding.message.setText(message.getMessage());
        } else{
            RecieverViewHolder viewHolder = (RecieverViewHolder) holder;
            viewHolder.binding.message.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Make two view holders for the two layouts item_send & item_recieve

    public class SentViewHolder extends RecyclerView.ViewHolder{
        //Using data binding as implemented in the whole project
        //Data type is a precreated Binding type

        ItemSendBinding binding;


        public SentViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemSendBinding.bind(itemView);
        }
    }
    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        ItemRecieveBinding binding ;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemRecieveBinding.bind(itemView);
        }
    }
}
