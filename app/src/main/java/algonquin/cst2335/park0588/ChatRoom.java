package algonquin.cst2335.park0588;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.park0588.data.ChatRoomViewModel;
import algonquin.cst2335.park0588.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.park0588.databinding.ReceMessageBinding;
import algonquin.cst2335.park0588.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
private ActivityChatRoomBinding binding;
private RecyclerView.Adapter myAdapter;
private ChatRoomViewModel chatModel;
private ArrayList<ChatMessage> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null){
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }
        binding.sendButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            messages.add(new ChatMessage(
                    binding.editTextSent.getText().toString(),
                    currentDateAndTime,
                    true));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editTextSent.setText("");
        });
        binding.receButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            messages.add(new ChatMessage(
                    binding.editTextSent.getText().toString(),
                    currentDateAndTime,
                    false));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editTextSent.setText("");
        });
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                if(viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(),
                            parent, false);
                    view = binding.getRoot();
                } else{
                    ReceMessageBinding binding = ReceMessageBinding.inflate(getLayoutInflater(),
                            parent, false);
                    view = binding.getRoot();
                }
                return new MyRowHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage msgPosition = messages.get(position);
                if(msgPosition.getIsSentButton()){
                    return 0;
                } else{
                    return 1;
                }
            }
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);


        }
    }

}