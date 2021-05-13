package kaitka.vishal.meeta.purple_ecommerce.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Activites.ProductDetailsActivity;
import kaitka.vishal.meeta.purple_ecommerce.DBqueries;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.WishlistModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModelList;
    private Boolean wishlist;
    private int lastPosition = -1;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resourse = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        Long freeCoupens = wishlistModelList.get(position).getFreeCoupens();
        String rating = wishlistModelList.get(position).getRating();
        Long totalRatings = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        Boolean paymentMethod = wishlistModelList.get(position).isCOD();

        holder.setData(resourse,title,freeCoupens,rating,totalRatings,productPrice,cuttedPrice,paymentMethod,position);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }

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
            totalRatings = itemView.findViewById(R.id.total_ratings_on_wishlist);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price_on_wishlist);
            cuttedPrice = itemView.findViewById(R.id.cutted_price_on_wislist);
            paymentMethod = itemView.findViewById(R.id.payment_method_of_wislist_love);
            deleteBtn = itemView.findViewById(R.id.delete_btn_on_wishlist);


        }
        private void setData(String resourse, String title, Long freeCoupensNo, String avearageRate, Long totalRatingsNo, String price, String cuttedPriceValue, Boolean COD, int index){

            Glide.with(itemView.getContext())
                    .load(resourse)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_shopping_cart))
                    .into(productImage);

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
            totalRatings.setText("("+ totalRatingsNo +")");
            productPrice.setText("₹"+price+".00/-");
            cuttedPrice.setText("₹"+cuttedPriceValue+".00 /-");
            if (COD){
                paymentMethod.setVisibility(View.VISIBLE);
            }
            else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }
            if (wishlist){
                deleteBtn.setVisibility(View.VISIBLE);

            }
            else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(v -> {
                deleteBtn.setEnabled(false);
                DBqueries.removeFromWishlist(index,itemView.getContext());
            });

            itemView.setOnClickListener(v -> {
                Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                itemView.getContext().startActivity(productDetailsIntent);
            });
        }
    }
}
