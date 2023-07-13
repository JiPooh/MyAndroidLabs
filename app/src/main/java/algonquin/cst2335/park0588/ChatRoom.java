package algonquin.cst2335.park0588;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        MessageDatabase db = Room.databaseBuilder(
                getApplicationContext(), MessageDatabase.class, "database-name").build();
        ChatMessageDAO mDAO = db.cmDAO();
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null){
            chatModel.messages.postValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                messages.addAll(mDAO.getAllMessages());
                runOnUiThread(()->{
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

                });
            });
        }
        binding.sendButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage sendMsg = new ChatMessage(
                    binding.editTextSent.getText().toString(),
                    currentDateAndTime,
                    true);
            messages.add(sendMsg);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                    mDAO.insertMessage(sendMsg);
                });
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editTextSent.setText("");
        });
        binding.receButton.setOnClickListener(clk -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage recMsg = new ChatMessage(
                    binding.editTextSent.getText().toString(),
                    currentDateAndTime,
                    false);
            messages.add(recMsg);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()-> {
                        mDAO.insertMessage(recMsg);
                    });
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editTextSent.setText("");
        });
        binding.rv.setLayoutManager(new LinearLayoutManager(this));

    }

    class MyRowHolder extends RecyclerView.ViewHolder{
        MessageDatabase db;
        ChatMessageDAO mDAO;
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            db = Room.databaseBuilder(
                    getApplicationContext(), MessageDatabase.class, "database-name").build();
            mDAO = db.cmDAO();
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(clk ->{
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you wish to delete this message: '"
                        + messageText.getText() + "' ?")
                        .setTitle("Question")
                        .setPositiveButton("Yes",(dialog, cl) -> {
                    ChatMessage m = messages.get(position);
                    ChatMessage removedText = messages.get(position);
                    Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(()-> {
                    mDAO.deleteMessage(m);});
                    messages.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                            .setAction("UNDO", clck ->{
                                messages.add(position, removedText);
                                myAdapter.notifyItemInserted(position);
                            })
                            .show();
                })
                        .setNegativeButton("No", (dialog, cl) -> {})
                        .create().show();
            });

        }
    }

}