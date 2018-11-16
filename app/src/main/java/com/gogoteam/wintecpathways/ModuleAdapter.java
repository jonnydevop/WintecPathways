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
        //Log.i("ModuleAdapter", "Pathway_2()  " + product.getPathway_2().length() + "   " + "Pathway_3()  " + product.getPathway_3().length());

        if(product != null && ( product.getPathway_1() !=null  && product.getPathway_1().length() ==0)) {
            holder.moduleName.setText(product.getMName());
            holder.moduleID.setText(product.getMID());
            holder.moduleButton.setText(String.valueOf(product.getButton()));
            //holder.imageView.setImageAlpha(product.getImage());
        }else
        {
            holder.moduleName.setText(product.getMName());
            holder.moduleID.setText(product.getMID());
            holder.moduleButton.setText(String.valueOf(product.getButton()));
            holder.stdPathwayModID.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView moduleName, moduleID, moduleButton;
        //ImageView imageView;
        RelativeLayout stdPathwayModID;

        public ProductViewHolder(View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(R.id.moduleName);
            moduleID = itemView.findViewById(R.id.moduleID);
            moduleButton = itemView.findViewById(R.id.moduleButton);
            //imageView = itemView.findViewById(R.id.imageView);
            stdPathwayModID = itemView.findViewById(R.id.stdPathwayModID);
        }
    }
}