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

import kaitka.vishal.meeta.purple_ecommerce.Adapters.WishlistAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.WishlistModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment {



    public MyWishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private RecyclerView wishlistRecyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();

        //added some dummy list or you can say dumy wislist;
        wishlistModelList.add(new WishlistModel(R.drawable.iphone_12,"Iphone 12 pro max",1,"4.5",1345,"Rs.1,00,000","1,50,000", "Cash On Delivery Available"));

        wishlistModelList.add(new WishlistModel(R.drawable.samsung,"Samsung 12 pro max",0,"4.5",1845,"Rs.1,00,000","1,50,000", "Cash On Delivery Available"));

        wishlistModelList.add(new WishlistModel(R.drawable.iphone_12,"Iphone 12 pro max",2,"4.5",145,"Rs.1,00,000","1,50,000", "Cash On Delivery Available"));

        wishlistModelList.add(new WishlistModel(R.drawable.samsung,"Samsung 12 pro max",2,"4.5",15,"Rs.1,00,000","1,50,000", "Cash On Delivery Available"));

        wishlistModelList.add(new WishlistModel(R.drawable.iphone_12,"Iphone 12 pro max",3,"4.5",1145,"Rs.1,00,000","1,50,000", "Cash On Delivery Available"));

        wishlistModelList.add(new WishlistModel(R.drawable.samsung,"Samsung pro max",0,"4.5",2145,"Rs.1,00,000","1,50,000", "Cash On Delivery"));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}