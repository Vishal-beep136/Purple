package kaitka.vishal.meeta.purple_ecommerce.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.MyRewardsAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.RewardModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
        RecyclerView rewardsRecyclerView = view.findViewById(R.id.discount_rewards_recyclerview);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();

        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList, false);
        rewardsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        return view;
    }
}