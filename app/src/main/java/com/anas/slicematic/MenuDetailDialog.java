package com.anas.slicematic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class MenuDetailDialog extends DialogFragment {

    private static final String ARG_PIZZA = "argPizza";

    public static MenuDetailDialog newInstance(Pizza pizza) {
        MenuDetailDialog dialog = new MenuDetailDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PIZZA, pizza);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_menu_detail, container, false);

        ImageView pizzaImageView = view.findViewById(R.id.dialogPizzaImageView);
        TextView nameTextView = view.findViewById(R.id.dialogNameTextView);
        TextView priceTextView = view.findViewById(R.id.dialogPriceTextView);
        TextView descriptionTextView = view.findViewById(R.id.dialogDescriptionTextView);

        Pizza pizza = getArguments().getParcelable(ARG_PIZZA);
        if (pizza != null) {
            pizzaImageView.setImageResource(pizza.getImage());
            nameTextView.setText(pizza.getName());
            priceTextView.setText(pizza.getPrice());
            descriptionTextView.setText(getDescriptionForPizza(pizza.getName()));
        }

        return view;
    }

    private String getDescriptionForPizza(String pizzaName) {
        switch (pizzaName) {
            case "Margherita":
                return "Classic Italian pizza with tomato sauce, mozzarella cheese, and fresh basil leaves.";
            case "Pepperoni":
                return "Spicy pepperoni slices with tomato sauce and mozzarella cheese.";
            case "Hawaiian":
                return "Tropical pizza with ham, pineapple, and mozzarella cheese.";
            case "BBQ Chicken":
                return "Savory pizza topped with BBQ sauce, chicken, onions, and mozzarella cheese.";
            case "Supreme":
                return "Loaded pizza with pepperoni, sausage, bell peppers, onions, and olives.";
            case "Veggie Delight":
                return "Vegetarian pizza with mushrooms, bell peppers, onions, black olives, and tomatoes.";
            case "Meat Lovers":
                return "Hearty pizza with pepperoni, sausage, ham, bacon, and mozzarella cheese.";
            case "Four Cheese":
                return "Decadent pizza with mozzarella, cheddar, parmesan, and gorgonzola cheeses.";
            case "Buffalo Chicken":
                return "Spicy buffalo chicken pizza with ranch dressing and mozzarella cheese.";
            case "Mushroom":
                return "Savory pizza topped with mushrooms, garlic, mozzarella cheese, and herbs.";
            case "Mediterranean":
                return "Inspired by Mediterranean flavors, with olives, tomatoes, feta cheese, and herbs.";
            case "Chicken Fajita":
                return "Tex-Mex pizza with grilled chicken, bell peppers, onions, and cheddar cheese.";
            case "Olive & Tomato":
                return "Light and flavorful pizza with black olives, cherry tomatoes, and mozzarella cheese.";
            case "Tandoori":
                return "Indian-inspired pizza with tandoori chicken, onions, bell peppers, and cilantro.";
            case "Extravaganza":
                return "Ultimate pizza loaded with pepperoni, sausage, mushrooms, onions, bell peppers, olives, and extra cheese.";
            case "Hot & Spicy":
                return "Fiery pizza with spicy sausage, jalapenos, onions, and mozzarella cheese.";
            default:
                return "Description not available";
        }
    }
}
