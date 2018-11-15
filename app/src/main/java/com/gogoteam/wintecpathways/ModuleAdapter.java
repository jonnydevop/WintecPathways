package com.gogoteam.wintecpathways;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<StudentProductsActivity> productList;

    public ModuleAdapter(Context mCtx, List<StudentProductsActivity> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        StudentProductsActivity product = productList.get(position);

        if(moduleName(product)) {
            holder.textViewId.setText(product.getId());
            holder.textViewTitle.setText(product.getTitle());
            holder.textViewShortDesc.setText(product.getShortdesc());
            holder.textViewButton.setText(String.valueOf(product.getButton()));
            //holder.imageView.setImageAlpha(product.getImage());
        }else
        {
            holder.textViewId.setText(product.getId());
            holder.textViewTitle.setText(product.getTitle());
            holder.textViewShortDesc.setText(product.getShortdesc());
            holder.textViewButton.setText(String.valueOf(product.getButton()));
            holder.stdPathwayModID.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewId, textViewTitle, textViewShortDesc, textViewButton;
        ImageView imageView;
        RelativeLayout stdPathwayModID;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewID);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewButton = itemView.findViewById(R.id.textViewButton);
            //imageView = itemView.findViewById(R.id.imageView);
            stdPathwayModID = itemView.findViewById(R.id.stdPathwayModID);
        }
    }

    //Check moduleName to set the background color
    private boolean moduleName(StudentProductsActivity productTest) {
        if (productTest.getTitle() != null &&
            (productTest.getTitle().equalsIgnoreCase("IT Operations")
            || productTest.getTitle().equalsIgnoreCase("Fundamentals of Programming and Problem Solving")
            || productTest.getTitle().equalsIgnoreCase("Professional Practice")
            || productTest.getTitle().equalsIgnoreCase("Business Systems Analysis & Design")
            || productTest.getTitle().equalsIgnoreCase("Introduction to Networks (Cisco 1)")
            || productTest.getTitle().equalsIgnoreCase("Operating Systems & Systems Support")
            || productTest.getTitle().equalsIgnoreCase("Database Principles")
            || productTest.getTitle().equalsIgnoreCase("Technical Support")
            || productTest.getTitle().equalsIgnoreCase("Object Oriented Programming")
            || productTest.getTitle().equalsIgnoreCase("Web Development")
            || productTest.getTitle().equalsIgnoreCase("Data-modelling and SQL")
            || productTest.getTitle().equalsIgnoreCase("Mathematics for IT")
            || productTest.getTitle().equalsIgnoreCase("Business, Interpersonal Communications & Technical Writing")
            || productTest.getTitle().equalsIgnoreCase("Business Essentials for IT Professionals")))
            return true;

        return false;

    }
}