package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.MyRewardsAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.ProductDetailsAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.ProductImagesAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.RewardModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

import static kaitka.vishal.meeta.purple_ecommerce.Activites.MainActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;
    private Button coupenRedeemBtn;

    /////coupendialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView couponsRecyclerViews;
    private static LinearLayout selectedCoupon ;
    /////coupendialog

    /////rating layout starts here
    private LinearLayout rateNowContainer;

    /////rating layout ends here

    private Button buyNowBtn;

    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;

    private static Boolean ALREADY_ADDED_TO_WISHLIST = false;
    private FloatingActionButton addToWishlistBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        coupenRedeemBtn = findViewById(R.id.coupen_redemption_btn);

        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.iphone_12);
        productImages.add(R.drawable.ic_smartphone);
        productImages.add(R.drawable.forgot_password_vector);

        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);


        viewpagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ALREADY_ADDED_TO_WISHLIST){
                    ALREADY_ADDED_TO_WISHLIST = false;
                    addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                }
                else {
                    ALREADY_ADDED_TO_WISHLIST = true;
                    addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }

            }
        });

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /////rating layout starts here
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x = 0; x<rateNowContainer.getChildCount(); x++){
            final int startPostion = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(startPostion);
                }
            });

        }
        /////rating layout ends here

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });

        ////coupon dialog
        Dialog checkCoupenPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDialog.setCancelable(true);
        checkCoupenPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCoupenPriceDialog.findViewById(R.id.toggle_recyclerview);
        couponsRecyclerViews = checkCoupenPriceDialog.findViewById(R.id.coupens_recyclerview);
        selectedCoupon = checkCoupenPriceDialog.findViewById(R.id.selected_coupen);

        coupenTitle = checkCoupenPriceDialog.findViewById(R.id.coupen_title);
        coupenExpiryDate = checkCoupenPriceDialog.findViewById(R.id.coupen_validity);
        coupenBody = checkCoupenPriceDialog.findViewById(R.id.coupen_body);

        TextView originalPrice = checkCoupenPriceDialog.findViewById(R.id.original_price_on_redeem_dialog);
        TextView discountedPrice = checkCoupenPriceDialog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsRecyclerViews.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();

        rewardModelList.add(new RewardModel("Nana Muna", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Rahi hoon", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("desh ka", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("sipahi hoon", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("mere sath bolo", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("ho", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021","free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("ho", "12.12.2021","free coupens in 2000 shoping"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList, true);
        couponsRecyclerViews.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });

        ////coupon dialog

        coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCoupenPriceDialog.show();
            }
        });


    }

    public static void showDialogRecyclerView()
    {
        if (couponsRecyclerViews.getVisibility() == View.GONE){
            couponsRecyclerViews.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);

        }
        else {
            couponsRecyclerViews.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);

        }
    }

    private void setRating(int startPostion) {
        for (int x = 0; x<rateNowContainer.getChildCount(); x++){
            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= startPostion){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
            return true;

        }
        else if (id == R.id.main_search_icon){
            //todo search
            return true;
        }
        else if (id == R.id.main_cart_icon_product_image){
            Intent cartIntent = new Intent(ProductDetailsActivity.this,  MainActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}