package kaitka.vishal.meeta.purple_ecommerce.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private RecyclerView testing;
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

        //  ///////////////Banner Slider starts here///////////////////

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.banner1, "#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.banner2, "#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.ic_green_email, "#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.ic_baseline_home_24, "#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.ic_man, "#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.banner1, "#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.forget_pass, "#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.purple_logo, "#FF8DAB"));

        //  ///////////////Banner Slider ends here///////////////////


        /////HORIZONTAL PRODUCT LAYOUT STARTS HERE

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
        /////HORIZONTAL PRODUCT LAYOUT ENDS HERE

        /////////////////////////////// Main Recycler View

        testing = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner2, "#fbe7cb"));
        homePageModelList.add(new HomePageModel(2, "Deals of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3, "best of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner1, "#cf3c3b"));
        homePageModelList.add(new HomePageModel(3, "#Trending", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2, "Smartphone", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner2, "#ffffff"));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner2, "#fbe7cb"));
        homePageModelList.add(new HomePageModel(2, "Deals of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3, "best of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner1, "#cf3c3b"));
        homePageModelList.add(new HomePageModel(3, "#Trending", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2, "Smartphone", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner2, "#ffffff"));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        ///////////////////////////// Main Recycler view ends here

        return view;

    }

}