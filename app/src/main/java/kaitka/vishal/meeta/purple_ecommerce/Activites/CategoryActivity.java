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

import static kaitka.vishal.meeta.purple_ecommerce.DBqueries.lists;
import static kaitka.vishal.meeta.purple_ecommerce.DBqueries.loadFragmentData;
import static kaitka.vishal.meeta.purple_ecommerce.DBqueries.loadedCategoriesName;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private HomePageAdapter adapter;


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




        /////////////////////////////// Main Recycler View


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        int listPosition = 0;
        for (int x = 0; x<loadedCategoriesName.size(); x++){
            if (loadedCategoriesName.get(x).equals(title.toUpperCase())){
                listPosition  = x;
            }
        }
        if (listPosition == 0){
            loadedCategoriesName.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(loadedCategoriesName.size() -1));
            loadFragmentData(adapter, this, loadedCategoriesName.size() -1, title);
        }
        else {
            adapter = new HomePageAdapter(lists.get(listPosition));
        }
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