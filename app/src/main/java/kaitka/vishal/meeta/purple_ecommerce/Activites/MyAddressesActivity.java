package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.AddressesAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.AddressesModel;
import kaitka.vishal.meeta.purple_ecommerce.R;
import kaitka.vishal.meeta.purple_ecommerce.databinding.ActivityMyAddressesBinding;

public class MyAddressesActivity extends AppCompatActivity {

    ActivityMyAddressesBinding binding;

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
        addressesModelList.add(new AddressesModel("Jhon smith", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));
        addressesModelList.add(new AddressesModel("Jhon smith ke papa", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));
        addressesModelList.add(new AddressesModel("Jhon smith ke dada", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));
        addressesModelList.add(new AddressesModel("Jhon smith ke chaca", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));
        addressesModelList.add(new AddressesModel("Jhon smith ke bade dada", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));
        addressesModelList.add(new AddressesModel("Jhon smith ke padosi", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));
        addressesModelList.add(new AddressesModel("Jhon smith ke dosht", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));
        addressesModelList.add(new AddressesModel("Steve Smith", "new york, street 12 b in the right floor of the empire state phone no.232342342", "324234"));


        AddressesAdapter addressesAdapter = new AddressesAdapter(addressesModelList);
        binding.addressRecyclerview.setAdapter(addressesAdapter);
        addressesAdapter.notifyDataSetChanged();

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