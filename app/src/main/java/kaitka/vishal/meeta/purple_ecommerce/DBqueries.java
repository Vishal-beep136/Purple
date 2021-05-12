package kaitka.vishal.meeta.purple_ecommerce;

import android.app.ProgressDialog;
import android.content.Context;
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
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.CategoryAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.HomePageAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.HomeFragmentPurple;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.CategoryModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HomePageModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HorizontalProductScrollModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.SliderModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.WishlistModel;

public class DBqueries {


    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
//    public static List<HomePageModel> homePageModelList = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesName = new ArrayList<>();
    public static List<String> wishlist = new ArrayList<>();


    public static void loadCategories(RecyclerView categoryRecyclerView, final Context context) {
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

    public static void loadWishlist(Context context, ProgressDialog dialog) {

        firebaseFirestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA")
                .document("MY_WISHLIST")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (long)task.getResult().get("list_size"); x++){
                            //here you must check the todo: product ID is like this or in Capital letters
                            wishlist.add(task.getResult().get("product_ID_"+x).toString());


                        }

                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
    }

}
