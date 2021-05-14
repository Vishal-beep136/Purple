package kaitka.vishal.meeta.purple_ecommerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaitka.vishal.meeta.purple_ecommerce.Activites.ProductDetailsActivity;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.CategoryAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.HomePageAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.HomeFragmentPurple;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyWishlistFragment;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.CategoryModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HomePageModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HorizontalProductScrollModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.SliderModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.WishlistModel;

import static kaitka.vishal.meeta.purple_ecommerce.Activites.ProductDetailsActivity.initialRating;
import static kaitka.vishal.meeta.purple_ecommerce.Activites.ProductDetailsActivity.productId;

public class DBqueries {


    //Defining Section START here

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
//    public static List<HomePageModel> homePageModelList = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesName = new ArrayList<>();
    public static List<String> wishlist = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();
    public static List<String> myRatedIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    //Defining Section END here


    //this load categories method.
    public static void loadCategories(RecyclerView categoryRecyclerView, final Context context) {
        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            categoryModelList.add(new CategoryModel(documentSnapshot.get("icon")
                                    .toString(), documentSnapshot.get("categoryName").toString()));
                        }
                        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                        categoryRecyclerView.setAdapter(categoryAdapter);
                        categoryAdapter.notifyDataSetChanged();

                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //this is load fragmentData method.
    public static void loadFragmentData(RecyclerView homePageRecyclerView, Context context, final int index, String categoryName) {
        firebaseFirestore
                .collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS")
                .orderBy("index")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            if ((long) documentSnapshot.get("view_type") == 0) {
                                List<SliderModel> sliderModelList = new ArrayList<>();
                                long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                for (long x = 1; x < no_of_banners + 1; x++) {
                                    String banners = documentSnapshot.get("banner_" + x).toString();
                                    String banner_background = documentSnapshot.get("banner_" + x + "_bg").toString();
                                    sliderModelList.add(new SliderModel(banners, banner_background));
                                }

                                lists.get(index).add(new HomePageModel(0, sliderModelList));

                            } else if ((long) documentSnapshot.get("view_type") == 1) {

                                String stripAdBanner = documentSnapshot.get("strip_ad_banner").toString();
                                String stripAdBackground = documentSnapshot.get("background").toString();
                                lists.get(index).add(new HomePageModel(1, stripAdBanner, stripAdBackground));


                            } else if ((long) documentSnapshot.get("view_type") == 2) {

                                List<WishlistModel> viewAllProductList = new ArrayList<>();

                                List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                long no_of_products = (long) documentSnapshot.get("no_of_products");

                                for (long x = 1; x < no_of_products + 1; x++) {
                                    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                                            documentSnapshot.get("product_ID_" + x).toString(),
                                            documentSnapshot.get("product_image_" + x).toString(),
                                            documentSnapshot.get("product_title_" + x).toString(),
                                            documentSnapshot.get("product_subtitle_" + x).toString(),
                                            documentSnapshot.get("product_price_" + x).toString()
                                    ));

                                    viewAllProductList.add(new WishlistModel(
                                            documentSnapshot.get("product_ID_" + x).toString(),
                                            documentSnapshot.get("product_image_" + x).toString(),
                                            documentSnapshot.get("product_full_title_" + x).toString(),
                                            (Long) documentSnapshot.get("free_coupens_" + x),
                                            documentSnapshot.get("average_rating_" + x).toString(),
                                            (Long) documentSnapshot.get("totalRate" + x),
                                            documentSnapshot.get("product_price_" + x).toString(),
                                            documentSnapshot.get("cutted_price_" + x).toString(),
                                            (boolean) documentSnapshot.get("COD_" + x)));
                                }
                                lists.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList, viewAllProductList));


                            } else if ((long) documentSnapshot.get("view_type") == 3) {
                                List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
                                long no_of_products = (long) documentSnapshot.get("no_of_products");

                                for (long x = 1; x < no_of_products + 1; x++) {
                                    gridLayoutModelList.add(new HorizontalProductScrollModel(
                                            documentSnapshot.get("product_ID_" + x).toString(),
                                            documentSnapshot.get("product_image_" + x).toString(),
                                            documentSnapshot.get("product_title_" + x).toString(),
                                            documentSnapshot.get("product_subtitle_" + x).toString(),
                                            documentSnapshot.get("product_price_" + x).toString()
                                    ));
                                }
                                lists.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), gridLayoutModelList));


                            }

                        }
                        HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                        homePageRecyclerView.setAdapter(homePageAdapter);
                        homePageAdapter.notifyDataSetChanged();
                        HomeFragmentPurple.swipeRefreshLayout.setRefreshing(false);


                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //this is load wishlist method.
    public static void loadWishlist(Context context, final ProgressDialog dialog, final boolean loadProductData) {

        wishlist.clear();
        firebaseFirestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA")
                .document("MY_WISHLIST")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (Long) task.getResult().get("list_size"); x++) {
                            wishlist.add(task.getResult().get("product_ID_" + x).toString());

                            if (DBqueries.wishlist.contains(productId)) {
                                ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                                if (ProductDetailsActivity.addToWishlistBtn != null) {
                                    ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                                }
                            } else {
                                if (ProductDetailsActivity.addToWishlistBtn != null) {
                                    ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                }
                                ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                            }

                            if (loadProductData) {
                                wishlistModelList.clear();
                                firebaseFirestore.collection("PRODUCTS")
                                        .document(task.getResult().get("product_ID_" + x).toString())
                                        .get().addOnCompleteListener((Task<DocumentSnapshot> task1) -> {
                                    if (task1.isSuccessful()) {
                                        wishlistModelList.add(new WishlistModel(
                                                productId,
                                                task1.getResult().get("product_image_1").toString(),
                                                task1.getResult().get("product_title_1").toString(),
                                                (Long) task1.getResult().get("free_coupens"),
                                                task1.getResult().get("average_rating").toString(),
                                                (Long) task1.getResult().get("totalRate"),
                                                task1.getResult().get("product_price").toString(),
                                                task1.getResult().get("cutted_price").toString(),
                                                (boolean) task1.getResult().get("COD")));

                                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();

                                    } else {
                                        String error = task1.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }

                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
    }

    //this is removeFromWishlist method.
    public static void removeFromWishlist(int index, final Context context) {
        wishlist.remove(index);
        Map<String, Object> updateWishlist = new HashMap<>();

        for (int x = 0; x < wishlist.size(); x++) {
            updateWishlist.put("product_ID_" + x, wishlist.get(x));
        }
        updateWishlist.put("list_size", (long) wishlist.size());

        firebaseFirestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA")
                .document("MY_WISHLIST")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModelList.size() != 0) {
                        wishlistModelList.remove(index);
                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                    Toast.makeText(context, "Removed Successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    if (ProductDetailsActivity.addToWishlistBtn != null) {
                        ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                    }
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                ProductDetailsActivity.running_wishlist_query = false;
            }
        });

    }

    public static void loadRatingList(Context context) {
        if (!ProductDetailsActivity.running_rating_query) {

            ProductDetailsActivity.running_rating_query = true;

            myRatedIds.clear();
            myRating.clear();

            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                    .document("MY_RATINGS").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        myRatedIds.add(task.getResult().get("product_ID_" + x).toString());
                        myRating.add((long) task.getResult().get("rating_" + x));

                        if (task.getResult().get("product_ID_" + x).toString().equals(productId)) {
                            ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                            if (ProductDetailsActivity.rateNowContainer != null) {
                                ProductDetailsActivity.setRating(initialRating);
                            }
                        }
                    }
                } else {
                    String error = task.getException().getMessage().toUpperCase();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.running_rating_query = false;
            });
        }
    }


    //this is clearData method.
    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesName.clear();
        wishlist.clear();
        wishlistModelList.clear();
    }


}
