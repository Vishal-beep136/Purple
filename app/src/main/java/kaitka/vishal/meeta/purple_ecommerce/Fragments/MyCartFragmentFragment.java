package kaitka.vishal.meeta.purple_ecommerce.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.CartAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.CartItemModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MyCartFragmentFragment extends Fragment {


    public MyCartFragmentFragment() {
        // Required empty public constructor
    }


    private RecyclerView cartItemsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart_fragment, container, false);
        cartItemsRecyclerView = view.findViewById(R.id.cart_item_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.samsung,"Samsung Galaxy s20",2,"₹60,000/-","₹70,000/-",2,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.samsung,"Samsung Galaxy s20",0,"₹60,000/-","₹70,000/-",1,0,1));
        cartItemModelList.add(new CartItemModel(0,R.drawable.samsung,"Samsung Galaxy s20",2,"₹60,000/-","₹70,000/-",2,1,0));
        cartItemModelList.add(new CartItemModel(1,"Price(3 items)", "₹32,000", "free", "₹96,000","₹10,000"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        return view;
    }
}