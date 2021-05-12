package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.MyRewardsAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.ProductDetailsAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.ProductImagesAdapter;
import kaitka.vishal.meeta.purple_ecommerce.DBqueries;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.ProductDescriptionFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.ProductSpecificationFragment;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.ProductSpecificationModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.RewardModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

import static kaitka.vishal.meeta.purple_ecommerce.Activites.MainActivity.showCart;
import static kaitka.vishal.meeta.purple_ecommerce.Fragments.SignInFragment.disableCloseBtn;
import static kaitka.vishal.meeta.purple_ecommerce.Fragments.SignUpFragment.disableCloseBtnSignUp;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private ImageView codIndicator;
    private TextView tvCodIndicator;
    private ProgressDialog dialog;

    private TabLayout viewpagerIndicator;
    private LinearLayout couponRedemptionLayout;
    private Button coupenRedeemBtn;

    private TextView rewardTitle;
    private TextView rewardBody;

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private String productDescription;
    private String productOthersDetails;

    /////rating layout starts here
    private LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressbarContainer;
    private TextView averageRating;
    /////rating layout ends here

    private Button buyNowBtn;
    private LinearLayout addToCartBtn;

    private static Boolean ALREADY_ADDED_TO_WISHLIST = false;
    private FloatingActionButton addToWishlistBtn;

    //product description
    private ConstraintLayout productsDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private TextView productOnlyDescriptionBody;
//product description


    private FirebaseFirestore firebaseFirestore;

    /////coupendialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView couponsRecyclerViews;
    private static LinearLayout selectedCoupon;
    /////coupendialog

    //defining sign in dialog
    private Dialog signInDialog;

    private FirebaseUser currentUser;
    private String productId;

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
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        coupenRedeemBtn = findViewById(R.id.coupen_redemption_btn);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_ratings_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        codIndicator = findViewById(R.id.cod_indicator_imageview);
        tvCodIndicator = findViewById(R.id.tv_cod_indicator);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDetailsTabsContainer = findViewById(R.id.product_details_tab_container);
        productsDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure = findViewById(R.id.total_rating_figures);
        ratingsProgressbarContainer = findViewById(R.id.rating_progressbar_container);
        averageRating = findViewById(R.id.avarage_rating);
        couponRedemptionLayout = findViewById(R.id.coupoun_redemption_layout);

        //defining the dialog and remember t code studio doesn't do like so this type of work you are done by you ok!
        dialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        dialog.setIcon(R.drawable.ic_like_heart);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();



        firebaseFirestore = FirebaseFirestore.getInstance();
        List<String> productImages = new ArrayList<>();

        productId = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS")
                .document(productId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long x = 1; x < (Long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title_1").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("( " + (Long) documentSnapshot.get("total_ratings") + " ) ratings");
                    productPrice.setText("₹" + documentSnapshot.get("product_price").toString() + ".00/-");
                    cuttedPrice.setText("₹" + documentSnapshot.get("cutted_price").toString() + ".00/-");

                    if ((boolean) documentSnapshot.get("COD")) {
                        codIndicator.setVisibility(View.VISIBLE);
                        tvCodIndicator.setVisibility(View.VISIBLE);
                    } else {
                        codIndicator.setVisibility(View.INVISIBLE);
                        tvCodIndicator.setVisibility(View.INVISIBLE);
                    }

                    rewardTitle.setText((long) documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupen_title").toString());
                    rewardBody.setText(documentSnapshot.get("free_coupen_body").toString());

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabsContainer.setVisibility(View.VISIBLE);
                        productsDetailsOnlyContainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();


                        productOthersDetails = documentSnapshot.get("product_other_details").toString();
                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {
                            String specTitle = documentSnapshot.get("spec_title_" + x).toString();

                            productSpecificationModelList.add(new ProductSpecificationModel(0, specTitle));

                            for (long j = 1; j < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; j++) {
                                String specName = documentSnapshot.get("spec_title_" + x + "_field_" + j + "_name").toString();
                                String specValue = documentSnapshot.get("spec_title_" + x + "_field_" + j + "_value").toString();

                                productSpecificationModelList.add(new ProductSpecificationModel(1, specName, specValue));
                            }

                        }
                    } else {
                        productDetailsTabsContainer.setVisibility(View.GONE);
                        productsDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                    }


                    totalRatings.setText((long) documentSnapshot.get("total_ratings") + "ratings");

                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long) documentSnapshot.get("star_" + (5 - x))));
                        ProgressBar progressBar = (ProgressBar) ratingsProgressbarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get("star_" + (5 - x)))));
                    }
                    totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));
                    averageRating.setText(documentSnapshot.get("average_rating").toString());
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(ProductDetailsActivity.this.getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOthersDetails, productSpecificationModelList));

                    if (DBqueries.wishlist.size() == 0){
                        DBqueries.loadWishlist(ProductDetailsActivity.this, dialog);
                    }
                    else {
                        dialog.dismiss();
                    }

                    if (DBqueries.wishlist.contains(productId)){
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                    }
                    else {
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }

                } else {
                    dialog.dismiss();
                    Log.d("TAG", "onComplete:" + task.getException().getLocalizedMessage());
                    Toast.makeText(ProductDetailsActivity.this, "Error: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewpagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishlistBtn.setOnClickListener(v -> {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                if (ALREADY_ADDED_TO_WISHLIST) {
                    ALREADY_ADDED_TO_WISHLIST = false;
                    addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                } else {


                    Map<String,Object> addProduct = new HashMap<>();
                    addProduct.put("product_ID_"+String.valueOf(DBqueries.wishlist.size()),productId);


                    firebaseFirestore.collection("USERS")
                            .document(currentUser.getUid())
                            .collection("USER_DATA")
                            .document("MY_WISHLIST")
                            .set(addProduct)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        Map<String ,Object> updateListSize = new HashMap<>();
                                        updateListSize.put("list_size", (long) (DBqueries.wishlist.size() + 1));

                                        firebaseFirestore.collection("USERS")
                                                .document(currentUser.getUid())
                                                .collection("USER_DATA")
                                                .document("MY_WISHLIST")
                                                .update(updateListSize)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            ALREADY_ADDED_TO_WISHLIST = true;
                                                            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                                            DBqueries.wishlist.add(productId);
                                                            Toast.makeText(ProductDetailsActivity.this, "Added to your wishlist!", Toast.LENGTH_SHORT).show();

                                                        }
                                                        else {
                                                            Log.d("TAG", "onComplete:" + task.getException().getLocalizedMessage());
                                                            Toast.makeText(ProductDetailsActivity.this, "Error: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });

                                    }
                                    else {
                                        Log.d("TAG", "onComplete:" + task.getException().getLocalizedMessage());
                                        Toast.makeText(ProductDetailsActivity.this, "Error: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
            }

        });

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
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int startPostion = x;
            rateNowContainer.getChildAt(x).setOnClickListener(v -> {
                //checking
                if (currentUser == null) {
                    signInDialog.show();
                    Log.d("Dialog", "onCreate: Dialog showed");
                } else {
                    setRating(startPostion);
                }
            });

        }
        /////rating layout ends here

        buyNowBtn.setOnClickListener(v -> {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }

        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    //todo: add to cart
                    Toast.makeText(ProductDetailsActivity.this, "sorry. this service is currently not available!", Toast.LENGTH_SHORT).show();

                }
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

        rewardModelList.add(new RewardModel("Nana Muna", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Rahi hoon", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("desh ka", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("sipahi hoon", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("mere sath bolo", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("ho", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("Jai", "12.12.2021", "free coupens in 2000 shoping"));
        rewardModelList.add(new RewardModel("ho", "12.12.2021", "free coupens in 2000 shoping"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList, true);
        couponsRecyclerViews.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(v -> showDialogRecyclerView());

        ////coupon dialog

        coupenRedeemBtn.setOnClickListener(v -> checkCoupenPriceDialog.show());

        //// Sign In Dialog
        //sign in dialog is start from here
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn);
        Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        //dialog sign In btn setOnClickListener
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCloseBtn = true;
                disableCloseBtnSignUp = true;
                signInDialog.dismiss();
                RegisterActivity.setSignUpFragment = false;
                startActivity(registerIntent);
                finish();
            }
        });

        //dialog sign UP btn setOnClickListener
        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCloseBtn = true;
                disableCloseBtnSignUp = true;
                signInDialog.dismiss();
                RegisterActivity.setSignUpFragment = true;
                startActivity(registerIntent);
                finish();
            }
        });
        //// Sign In Dialog

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            couponRedemptionLayout.setVisibility(View.GONE);
        } else {
            couponRedemptionLayout.setVisibility(View.VISIBLE);
        }
    }

    public static void showDialogRecyclerView() {
        if (couponsRecyclerViews.getVisibility() == View.GONE) {
            couponsRecyclerViews.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);

        } else {
            couponsRecyclerViews.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);

        }
    }

    private void setRating(int startPostion) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= startPostion) {
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

        if (id == android.R.id.home) {
            finish();
            return true;

        } else if (id == R.id.main_search_icon) {
            //todo search
            return true;
        } else if (id == R.id.main_cart_icon_product_image) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}