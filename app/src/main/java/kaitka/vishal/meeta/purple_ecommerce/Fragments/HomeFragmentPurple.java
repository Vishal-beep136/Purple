package kaitka.vishal.meeta.purple_ecommerce.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


import kaitka.vishal.meeta.purple_ecommerce.Adapters.CategoryAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.GridProductLayoutAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.HomePageAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.HorizontalProductScrollAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.SliderAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.CategoryModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HomePageModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HorizontalProductScrollModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.SliderModel;
import kaitka.vishal.meeta.purple_ecommerce.R;
import kaitka.vishal.meeta.purple_ecommerce.ui.slideshow.SlideshowViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentPurple extends Fragment {


    public HomeFragmentPurple() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private HomePageAdapter adapter;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_purple, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryModelList);

        categoryRecyclerView.setAdapter(categoryAdapter);


        firebaseFirestore = FirebaseFirestore.getInstance();
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
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        /////HORIZONTAL PRODUCT LAYOUT STARTS HERE
/*
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.samsung, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.samsung, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.samsung, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.samsung, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.samsung, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));

 */
        /////HORIZONTAL PRODUCT LAYOUT ENDS HERE

        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList = new ArrayList<>();
        adapter = new HomePageAdapter(homePageModelList);
        homePageRecyclerView.setAdapter(adapter);

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
                                    homePageModelList.add(new HomePageModel(2,documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList));


                                } else if ((long) documentSnapshot.get("view_type") == 3) {

                                }

                            }
                            adapter.notifyDataSetChanged();


                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });


        ///////////////////////////// Main Recycler view ends here

        return view;

    }

}