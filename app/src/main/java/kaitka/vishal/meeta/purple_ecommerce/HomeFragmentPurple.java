package kaitka.vishal.meeta.purple_ecommerce;

import android.graphics.Color;
import android.os.Bundle;

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
import android.widget.LinearLayout;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentPurple extends Fragment {



    public HomeFragmentPurple() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    //  Banner Slider ///////////////////////
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;

    //Banner Slider ////////////////////////////////

    //////////Strip Ad Layout start here
    private ImageView stripAdImage;
    private ConstraintLayout stripAdContainer;
    //////////Strip Ad Layout end here

    /////HORIZONTAL PRODUCT LAYOUT STARS HERE
    private TextView horizontalLayoutTitle;
    private Button horizontalLayoutViewAllBtn;
    private RecyclerView horizontalRecyclerView;
    /////HORIZONTAL PRODUCT LAYOUT ENDS HERE

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

        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel("line", "Home"));
        categoryModelList.add(new CategoryModel("line", "Electronics"));
        categoryModelList.add(new CategoryModel("line", "Furniture"));
        categoryModelList.add(new CategoryModel("line", "Tech"));
        categoryModelList.add(new CategoryModel("line", "Fashion"));
        categoryModelList.add(new CategoryModel("line", "Kids"));
        categoryModelList.add(new CategoryModel("line", "Men"));
        categoryModelList.add(new CategoryModel("line", "Woman"));
        categoryModelList.add(new CategoryModel("line", "Toys"));
        categoryModelList.add(new CategoryModel("line", "sports"));
        categoryModelList.add(new CategoryModel("line", "kitchen"));
        categoryModelList.add(new CategoryModel("line", "shoes"));
        categoryModelList.add(new CategoryModel("line", "books"));

        categoryAdapter = new CategoryAdapter(categoryModelList);

        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        //  ///////////////Banner Slider starts here///////////////////
        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);
        sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.forget_pass,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.purple_logo,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.banner1,"#FF8DAB"));

        sliderModelList.add(new SliderModel(R.drawable.banner2,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.ic_green_email,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.ic_baseline_home_24,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.ic_man,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.banner1,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.forget_pass,"#FF8DAB"));

        sliderModelList.add(new SliderModel(R.drawable.purple_logo,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.banner1,"#FF8DAB"));
        sliderModelList.add(new SliderModel(R.drawable.banner2,"#FF8DAB"));



        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };

        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSlideShow();
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });

        //  ///////////////Banner Slider ends here///////////////////

        //////////Strip Ad Layout START here
        stripAdImage = view.findViewById(R.id.strip_ad_image);
        stripAdContainer = view.findViewById(R.id.strip_ad_container);

        stripAdImage.setImageResource(R.drawable.banner2);
        stripAdContainer.setBackgroundColor(Color.parseColor("#fbe7cb"));
        //////////Strip Ad Layout END here

        /////HORIZONTAL PRODUCT LAYOUT STARTS HERE
        horizontalLayoutTitle = view.findViewById(R.id.horizontal__scroll_layout_title);
        horizontalLayoutViewAllBtn = view.findViewById(R.id.horizontal_scroll_view_all_btn);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerview);

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.iphone_12, "Iphone 12 pro max", "512 GB Storage With 64 MP camera", "₹1,000,00"));


        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        /////HORIZONTAL PRODUCT LAYOUT ENDS HERE

        /////GRID PRODUCT LAYOUT START HERE
        TextView gridLayoutTitle = view.findViewById(R.id.grid_layout_product_title);
        Button gridLayoutViewAllBtn = view.findViewById(R.id.grid_product_layout_viewall_btn);
        GridView gridView = view.findViewById(R.id.grid_product_layout_gridview);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));

        /////GRID PRODUCT LAYOUT ENDS HERE

        ///////////////////////////////

        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner2, "#fbe7cb"));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"best of the day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner1, "#cf3c3b"));
        homePageModelList.add(new HomePageModel(3,"#Trending",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2,"Smartphone",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner2, "#ffffff"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        /////////////////////////////

        return view;

    }

    //  Banner Slider for testing start here////////////////////////////

    private void pageLooper(){
        if (currentPage == sliderModelList.size() -2 ){
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
        if (currentPage == 1){
            currentPage = sliderModelList.size() -3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
    }
    private void startBannerSlideShow(){
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()){
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++, true);

            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }
    private void stopBannerSlideShow(){
        timer.cancel();
    }

    // ////////////banner slider ends here ////////////////

}