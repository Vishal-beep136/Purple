package kaitka.vishal.meeta.purple_ecommerce.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kaitka.vishal.meeta.purple_ecommerce.Adapters.ProductSpecificationAdapter;
import kaitka.vishal.meeta.purple_ecommerce.Modellls.ProductSpecificationModel;
import kaitka.vishal.meeta.purple_ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {


    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private RecyclerView productSpecificationRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);

        productSpecificationRecyclerView = view.findViewById(R.id.product_specification_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);

        List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
        productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM" , "16GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Storage" , "256GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Brand" , "Apple"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Made" , "California"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Design by" , "Loki Fergutation"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Used by" , "Tony Stark"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"blue rays" , "190"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"heart by" , "crish hemswarth"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));

        productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM" , "16GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Storage" , "256GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Brand" , "Apple"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Made" , "California"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Design by" , "Loki Fergutation"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Used by" , "Tony Stark"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"blue rays" , "190"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"heart by" , "crish hemswarth"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"almond screen" , "1920 X 1820"));







        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }
}