package kaitka.vishal.meeta.purple_ecommerce.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Modellls.RewardModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.Viewholder> {

    private List<RewardModel> rewardModelList;

    public MyRewardsAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String title = rewardModelList.get(position).getTitle();
        String validation = rewardModelList.get(position).getExpiryDate();
        String coupenBody = rewardModelList.get(position).getCoupenBody();
        holder.setData(title,validation,coupenBody);

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView coupenTitle, coupenExpiryDate, coupenBody;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenExpiryDate = itemView.findViewById(R.id.coupen_validity);
            coupenBody = itemView.findViewById(R.id.coupen_body);
        }
        private void setData(String title, String expDate, String coupenBodyText){
            coupenTitle.setText(title);
            coupenExpiryDate.setText(expDate);
            coupenBody.setText(coupenBodyText);

        }
    }
}
