package algonquin.cst2335.park0588;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.park0588.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage m){
        selected = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);
        binding.msgDet.setText(selected.message);
        binding.timeDet.setText(selected.timeSent);
        binding.idDet.setText("ID = " + selected.id);
        if(selected.getIsSentButton()){
            binding.senderDet.setText("Sent");
        }else{
            binding.senderDet.setText("Received");
        }

        return binding.getRoot();
    }
}
