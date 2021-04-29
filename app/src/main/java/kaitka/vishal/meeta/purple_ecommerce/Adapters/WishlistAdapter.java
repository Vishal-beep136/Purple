package kaitka.vishal.meeta.purple_ecommerce.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Modellls.WishlistModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModelList;

    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resourse = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        int freeCoupens = wishlistModelList.get(position).getFreeCoupens();
        String rating = wishlistModelList.get(position).getRating();
        int totalRatings = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        String paymentMethod = wishlistModelList.get(position).getPaymentMethod();

        holder.setData(resourse,title,freeCoupens,rating,totalRatings,productPrice,cuttedPrice,paymentMethod);


    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.product_image_of_wislist);
            productTitle = itemView.findViewById(R.id.product_title_of_wishlist);
            freeCoupens = itemView.findViewById(R.id.free_coupens_of_wishlist);
            coupenIcon = itemView.findViewById(R.id.coupen_icon_label);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings_on_wishlist_item_layout);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price_on_wishlist);
            cuttedPrice = itemView.findViewById(R.id.cutted_price_on_wislist);
            paymentMethod = itemView.findViewById(R.id.payment_method_of_wislist_love);
            deleteBtn = itemView.findViewById(R.id.delete_btn_on_wishlist);


        }
        private void setData(int resourse, String title, int freeCoupensNo, String avearageRate, int totalRatingsNo, String price, String cuttedPriceValue, String payMethod){
            productImage.setImageResource(resourse);
            productTitle.setText(title);
            if (freeCoupensNo != 0){
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {
                    freeCoupens.setText("free " + freeCoupensNo + " coupen");
                }
                else {
                    freeCoupens.setText("free " + freeCoupensNo + " coupens");
                }

            }
            else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);

            }
            rating.setText(avearageRate);
            totalRatings.setText(totalRatingsNo + "(ratings)");
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPriceValue);
            paymentMethod.setText(payMethod);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "removed from wishlist", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
