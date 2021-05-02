package kaitka.vishal.meeta.purple_ecommerce.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Modellls.AddressesModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder> {

    List<AddressesModel> addressesModelList;

    public AddressesAdapter(List<AddressesModel> addressesModelList) {
        this.addressesModelList = addressesModelList;
    }

    @NonNull
    @Override
    public AddressesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout, parent, false);
        return new Viewholder(view);
   }


    @Override
   public void onBindViewHolder(@NonNull AddressesAdapter.Viewholder holder, int position) {
        String name = addressesModelList.get(position).getFullname();
        String address = addressesModelList.get(position).getAddress();
        String pincode = addressesModelList.get(position).getPincode();

        holder.setData(name, address, pincode);

    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView pincode;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address_of_addresses_item);
            pincode = itemView.findViewById(R.id.pincode_of_address_item);
        }
        private void setData(String username, String userAddress, String userPincode){
            fullname.setText(username);
            address.setText(userAddress);
            pincode.setText(userPincode);

        }
    }
}
