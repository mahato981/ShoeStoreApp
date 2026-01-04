package com.shoesstore.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shoesstore.app.R;
import com.shoesstore.app.models.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    
    private Context context;
    private List<Product> products;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> products, OnProductClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        
        // Set product emoji
        holder.productEmoji.setText(product.getImage());
        
        // Set product name (first part before |)
        String fullName = product.getName();
        String displayName = fullName.contains("|") ? fullName.split("\\|")[0].trim() : fullName;
        holder.productName.setText(displayName);
        
        // Set sub category
        holder.productSubCategory.setText(product.getSubCategory());
        
        // Set price
        holder.productPrice.setText(product.getPrice());
        
        // Set category icon and text
        holder.categoryIcon.setText(getCategoryIcon(product.getCategory()));
        holder.categoryText.setText(product.getCategory());
        
        // Set gradient background
        setGradientBackground(holder.gradientBackground, product.getColor());
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }

    private String getCategoryIcon(String category) {
        switch (category) {
            case "Male": return "👨";
            case "Female": return "👩";
            case "Kids": return "👶";
            default: return "👤";
        }
    }

    private void setGradientBackground(View view, String colorName) {
        GradientDrawable gradient = new GradientDrawable();
        gradient.setShape(GradientDrawable.RECTANGLE);
        
        int[] colors = getGradientColors(colorName);
        gradient.setColors(colors);
        gradient.setOrientation(GradientDrawable.Orientation.BR_TL);
        
        view.setBackground(gradient);
    }

    private int[] getGradientColors(String colorName) {
        // Define gradient colors based on color name
        switch (colorName) {
            case "blue_gradient":
                return new int[]{Color.parseColor("#BFDBFE"), Color.parseColor("#93C5FD"), Color.parseColor("#A5F3FC")};
            case "pink_gradient":
                return new int[]{Color.parseColor("#FBCFE8"), Color.parseColor("#F9A8D4"), Color.parseColor("#FECDD3")};
            case "yellow_gradient":
                return new int[]{Color.parseColor("#FEF08A"), Color.parseColor("#FDBA74"), Color.parseColor("#FEF08A")};
            case "green_gradient":
                return new int[]{Color.parseColor("#BBF7D0"), Color.parseColor("#86EFAC"), Color.parseColor("#A7F3D0")};
            case "purple_gradient":
                return new int[]{Color.parseColor("#E9D5FF"), Color.parseColor("#D8B4FE"), Color.parseColor("#FBCFE8")};
            case "red_gradient":
                return new int[]{Color.parseColor("#FECACA"), Color.parseColor("#FDBA74"), Color.parseColor("#FEF08A")};
            case "gray_gradient":
                return new int[]{Color.parseColor("#E5E7EB"), Color.parseColor("#D1D5DB"), Color.parseColor("#F3F4F6")};
            case "indigo_gradient":
                return new int[]{Color.parseColor("#C7D2FE"), Color.parseColor("#A5B4FC"), Color.parseColor("#93C5FD")};
            case "dark_gradient":
                return new int[]{Color.parseColor("#4B5563"), Color.parseColor("#374151"), Color.parseColor("#1F2937")};
            case "red_pink_gradient":
                return new int[]{Color.parseColor("#FCA5A5"), Color.parseColor("#F9A8D4"), Color.parseColor("#FBBF24")};
            case "blue_cyan_gradient":
                return new int[]{Color.parseColor("#93C5FD"), Color.parseColor("#67E8F9"), Color.parseColor("#A5F3FC")};
            case "brown_gradient":
                return new int[]{Color.parseColor("#D97706"), Color.parseColor("#B45309"), Color.parseColor("#F59E0B")};
            case "orange_gradient":
                return new int[]{Color.parseColor("#FCA5A5"), Color.parseColor("#FB923C"), Color.parseColor("#FBBF24")};
            case "cyan_gradient":
                return new int[]{Color.parseColor("#A5F3FC"), Color.parseColor("#93C5FD"), Color.parseColor("#C7D2FE")};
            case "stone_gradient":
                return new int[]{Color.parseColor("#78716C"), Color.parseColor("#57534E"), Color.parseColor("#3F3F46")};
            case "purple_pink_gradient":
                return new int[]{Color.parseColor("#D8B4FE"), Color.parseColor("#F0ABFC"), Color.parseColor("#F9A8D4")};
            case "slate_gradient":
                return new int[]{Color.parseColor("#94A3B8"), Color.parseColor("#64748B"), Color.parseColor("#475569")};
            case "amber_gradient":
                return new int[]{Color.parseColor("#FCD34D"), Color.parseColor("#F59E0B"), Color.parseColor("#D97706")};
            case "rose_gradient":
                return new int[]{Color.parseColor("#FDA4AF"), Color.parseColor("#FB7185"), Color.parseColor("#F43F5E")};
            case "violet_gradient":
                return new int[]{Color.parseColor("#DDD6FE"), Color.parseColor("#C4B5FD"), Color.parseColor("#A78BFA")};
            case "dark_brown_gradient":
                return new int[]{Color.parseColor("#F59E0B"), Color.parseColor("#B45309"), Color.parseColor("#D97706")};
            case "teal_gradient":
                return new int[]{Color.parseColor("#5EEAD4"), Color.parseColor("#2DD4BF"), Color.parseColor("#14B8A6")};
            case "lime_gradient":
                return new int[]{Color.parseColor("#D9F99D"), Color.parseColor("#BEF264"), Color.parseColor("#A3E635")};
            default:
                return new int[]{Color.parseColor("#BFDBFE"), Color.parseColor("#93C5FD"), Color.parseColor("#A5F3FC")};
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productEmoji, productName, productSubCategory, productPrice;
        TextView categoryIcon, categoryText;
        View gradientBackground;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productEmoji = itemView.findViewById(R.id.productEmoji);
            productName = itemView.findViewById(R.id.productName);
            productSubCategory = itemView.findViewById(R.id.productSubCategory);
            productPrice = itemView.findViewById(R.id.productPrice);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categoryText = itemView.findViewById(R.id.categoryText);
            gradientBackground = itemView.findViewById(R.id.gradientBackground);
        }
    }
}
