package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.CartAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.CartItemModel;
import kaitka.vishal.meeta.purple_ecommerce.R;
import kaitka.vishal.meeta.purple_ecommerce.databinding.ActivityDeliveryBinding;

public class DeliveryActivity extends AppCompatActivity {

    ActivityDeliveryBinding binding;
    private Button changeOrAddNewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_delivery);
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        changeOrAddNewAddress = findViewById(R.id.change_or_add_address_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.deliveryRecylerview.setLayoutManager(layoutManager);
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.samsung,"Samsung Galaxy s20",2,"₹60,000/-","₹70,000/-",2,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.samsung,"Samsung Galaxy s20",0,"₹60,000/-","₹70,000/-",1,0,1));
        cartItemModelList.add(new CartItemModel(0,R.drawable.samsung,"Samsung Galaxy s20",2,"₹60,000/-","₹70,000/-",2,1,0));
        cartItemModelList.add(new CartItemModel(1,"Price(3 items)", "₹32,000", "free", "₹96,000","₹10,000"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        binding.deliveryRecylerview.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddress.setVisibility(View.VISIBLE);





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}