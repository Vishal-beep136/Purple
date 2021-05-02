package kaitka.vishal.meeta.purple_ecommerce.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Modellls.AddressesModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

import static kaitka.vishal.meeta.purple_ecommerce.Activites.DeliveryActivity.SELECT_ADDRESS;
import static kaitka.vishal.meeta.purple_ecommerce.Activites.MyAddressesActivity.refreshItem;
import static kaitka.vishal.meeta.purple_ecommerce.Fragments.MyAccountFragment.MANAGE_ADDRESS;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder> {

    List<AddressesModel> addressesModelList;
    private int MODE;
    private int preSelectedPostion;

    public AddressesAdapter(List<AddressesModel> addressesModelList, int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE = MODE;
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
        Boolean selected = addressesModelList.get(position).getSelected();

        holder.setData(name, address, pincode,selected,position);

    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView checkIcon;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address_of_addresses_item);
            pincode = itemView.findViewById(R.id.pincode_of_address_item);
            checkIcon = itemView.findViewById(R.id.check_icon);
        }
        private void setData(String username, String userAddress, String userPincode, Boolean selected, int postion){

            fullname.setText(username);
            address.setText(userAddress);
            pincode.setText(userPincode);

            if (MODE == SELECT_ADDRESS){
                checkIcon.setImageResource(R.drawable.ic_tick);
                if (selected){
                    checkIcon.setVisibility(View.VISIBLE);
                    preSelectedPostion = postion;
                }
                else {
                    checkIcon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelectedPostion != postion) {
                            addressesModelList.get(postion).setSelected(true);
                            addressesModelList.get(preSelectedPostion).setSelected(false);
                            refreshItem(preSelectedPostion, postion);
                            preSelectedPostion = postion;
                        }


                    }
                });



            }
            else if (MODE == MANAGE_ADDRESS){


            }

        }
    }
}
