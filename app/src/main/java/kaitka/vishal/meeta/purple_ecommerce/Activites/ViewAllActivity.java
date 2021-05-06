package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.GridProductLayoutAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.WishlistAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HorizontalProductScrollModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.WishlistModel;
import kaitka.vishal.meeta.purple_ecommerce.R;
import kaitka.vishal.meeta.purple_ecommerce.databinding.ActivityViewAllBinding;

public class ViewAllActivity extends AppCompatActivity {

    ActivityViewAllBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Deals of the day");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        if (layout_code == 0) {

            binding.recyclerview.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.recyclerview.setLayoutManager(layoutManager);

            List<WishlistModel> wishlistModelList = new ArrayList<>();

            //added some dummy list or you can say dumy wislist;
            wishlistModelList.add(new WishlistModel(R.drawable.iphone_12, "Iphone 12 pro max", 1, "4.5", 1345, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.samsung, "Samsung 12 pro max", 0, "4.5", 1845, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.iphone_12, "Iphone 12 pro max", 2, "4.5", 145, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.samsung, "Samsung 12 pro max", 2, "4.5", 15, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.iphone_12, "Iphone 12 pro max", 3, "4.5", 1145, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.samsung, "Samsung pro max", 0, "4.5", 2145, "Rs.1,00,000", "1,50,000", "Cash On Delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.iphone_12, "Iphone 12 pro max", 1, "4.5", 1345, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.samsung, "Samsung 12 pro max", 0, "4.5", 1845, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.iphone_12, "Iphone 12 pro max", 2, "4.5", 145, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.samsung, "Samsung 12 pro max", 2, "4.5", 15, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.iphone_12, "Iphone 12 pro max", 3, "4.5", 1145, "Rs.1,00,000", "1,50,000", "Cash On Delivery Available"));

            wishlistModelList.add(new WishlistModel(R.drawable.samsung, "Samsung pro max", 0, "4.5", 2145, "Rs.1,00,000", "1,50,000", "Cash On Delivery"));

            WishlistAdapter adapter = new WishlistAdapter(wishlistModelList, false);
            binding.recyclerview.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else if (layout_code == 1) {
            //second condition starts here
            binding.gridViewAllActivity.setVisibility(View.VISIBLE);
            List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            binding.gridViewAllActivity.setAdapter(gridProductLayoutAdapter);

        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}