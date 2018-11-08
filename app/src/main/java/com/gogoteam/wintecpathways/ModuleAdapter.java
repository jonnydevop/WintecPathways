package com.gogoteam.wintecpathways;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        holder.textViewId.setText(product.getId());
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewShortDesc.setText(product.getShortdesc());
        holder.textViewButton.setText(String.valueOf(product.getButton()));
        holder.imageView.setImageAlpha(product.getImage());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewId, textViewTitle, textViewShortDesc, textViewButton;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewID);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewButton = itemView.findViewById(R.id.textViewButton);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}