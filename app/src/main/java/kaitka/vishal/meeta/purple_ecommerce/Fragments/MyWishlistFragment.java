package kaitka.vishal.meeta.purple_ecommerce.Fragments;

import android.app.ProgressDialog;
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
import kaitka.vishal.meeta.purple_ecommerce.DBqueries;
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
    private ProgressDialog dialog;
    public static WishlistAdapter wishlistAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        //defining the dialog and remember t code studio doesn't do like so this type of work you are done by you ok!
        dialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
        dialog.setIcon(R.drawable.ic_like_heart);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        if (DBqueries.wishlistModelList.size() == 0){
            DBqueries.wishlist.clear();
            DBqueries.loadWishlist(getContext(), dialog, true);
        }
        else {
            dialog.dismiss();
        }


        wishlistAdapter = new WishlistAdapter(DBqueries.wishlistModelList,true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}