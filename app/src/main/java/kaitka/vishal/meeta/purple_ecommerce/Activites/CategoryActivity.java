package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.CategoryAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.HomePageAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.CategoryModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HomePageModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.HorizontalProductScrollModel;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.SliderModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);

        //  ///////////////Banner Slider starts here///////////////////

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

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
        //  ///////////////Banner Slider ends here///////////////////



        /////HORIZONTAL PRODUCT LAYOUT STARTS HERE

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
        /////HORIZONTAL PRODUCT LAYOUT ENDS HERE

        /////////////////////////////// Main Recycler View


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner2, "#fbe7cb"));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"best of the day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner1, "#cf3c3b"));
        homePageModelList.add(new HomePageModel(3,"#Trending",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2,"Smartphone",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner2, "#ffffff"));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_search_icon){
            //todo search
            return true;

        }
        else if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}