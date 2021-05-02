package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.AddressesAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.AddressesModel;
import kaitka.vishal.meeta.purple_ecommerce.R;
import kaitka.vishal.meeta.purple_ecommerce.databinding.ActivityMyAddressesBinding;

import static kaitka.vishal.meeta.purple_ecommerce.Activites.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {

    ActivityMyAddressesBinding binding;
    public static AddressesAdapter addressesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.addressRecyclerview.setLayoutManager(layoutManager);

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Jhon smith", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", true));
        addressesModelList.add(new AddressesModel("Jhon smith ke papa", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", false));
        addressesModelList.add(new AddressesModel("Jhon smith ke chacha", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", false));
        addressesModelList.add(new AddressesModel("Jhon smith ke dada", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", false));
        addressesModelList.add(new AddressesModel("Jhon smith ke padosi", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", false));
        addressesModelList.add(new AddressesModel("Jhon smith ke dosth", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", false));
        addressesModelList.add(new AddressesModel("Jhon smith ka bhai", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", false));
        addressesModelList.add(new AddressesModel("Steve Smith", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234", false));



        int mode = getIntent().getIntExtra("MODE", -1);
        if (mode == SELECT_ADDRESS){
            binding.deliverHereBtn.setVisibility(View.VISIBLE);
        }
        else {
            binding.deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(addressesModelList, mode);
        binding.addressRecyclerview.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)binding.addressRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();

    }
    public static void refreshItem(int deselect, int select){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}