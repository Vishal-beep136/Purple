package kaitka.vishal.meeta.purple_ecommerce;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.CategoryAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.HomePageAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.CategoryModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HomePageModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HorizontalProductScrollModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.SliderModel;

public class DBqueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();



    public static void loadCategories(CategoryAdapter categoryAdapter, Context context){
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon")
                                        .toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    public static void loadFragmentData(HomePageAdapter adapter, Context context){
        firebaseFirestore
                .collection("CATEGORIES")
                .document("HOME")
                .collection("TOP_DEALS")
                .orderBy("index")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
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

                                    homePageModelList.add(new HomePageModel(0, sliderModelList));

                                } else if ((long) documentSnapshot.get("view_type") == 1) {

                                    String stripAdBanner = documentSnapshot.get("strip_ad_banner").toString();
                                    String stripAdBackground = documentSnapshot.get("background").toString();
                                    homePageModelList.add(new HomePageModel(1, stripAdBanner, stripAdBackground));


                                } else if ((long) documentSnapshot.get("view_type") == 2) {
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
                                    }
                                    homePageModelList.add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList));


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
                                    homePageModelList.add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), gridLayoutModelList));


                                }

                            }
                            adapter.notifyDataSetChanged();


                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context,error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

}
