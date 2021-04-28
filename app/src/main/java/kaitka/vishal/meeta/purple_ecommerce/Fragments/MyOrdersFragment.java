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

import kaitka.vishal.meeta.purple_ecommerce.Adapters.MyOrderAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.MyOrderItemModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }

    private RecyclerView myOrdersRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.samsung, 2, "Samsung galaxy s20 (modern)", "Deleiverd on Mon, 18 August 2032"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.iphone_12, 2, "Iphone 12 pro max", "Deleiverd on Mon, 07 August 2032"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.samsung, 4, "Samsung galaxy s20 (modern)", "Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.iphone_12, 1, "Iphone 12 pro ", "Deleiverd on Mon, 27 August 2032"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
        return view;
    }
}