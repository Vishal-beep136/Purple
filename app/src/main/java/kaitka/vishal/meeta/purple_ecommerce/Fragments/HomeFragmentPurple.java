package kaitka.vishal.meeta.purple_ecommerce.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.CategoryAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Adapters.HomePageAdapter;
import kaitka.vishal.meeta.purple_ecommerce.R;

import static kaitka.vishal.meeta.purple_ecommerce.DBqueries.categoryModelList;
import static kaitka.vishal.meeta.purple_ecommerce.DBqueries.homePageModelList;
import static kaitka.vishal.meeta.purple_ecommerce.DBqueries.loadCategories;
import static kaitka.vishal.meeta.purple_ecommerce.DBqueries.loadFragmentData;

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


        categoryAdapter = new CategoryAdapter(categoryModelList);

        categoryRecyclerView.setAdapter(categoryAdapter);

        if (categoryModelList.size() == 0) {
            loadCategories(categoryAdapter, getContext());
        } else {
            categoryAdapter.notifyDataSetChanged();
        }

        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());

        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        adapter = new HomePageAdapter(homePageModelList);

        homePageRecyclerView.setAdapter(adapter);

        if (homePageModelList.size() == 0) {
            loadFragmentData(adapter, getContext());
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
        return view;

    }
}