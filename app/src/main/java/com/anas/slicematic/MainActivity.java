package com.anas.slicematic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements PizzaAdapter.PizzaClickListener {

    private static final int REQUEST_CART_ACTIVITY = 1;
    private List<Pizza> pizzaList;
    private PizzaAdapter pizzaAdapter;

    private int pizzaCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView pizzaRecyclerView = findViewById(R.id.pizzaRecyclerView);
        pizzaList = createPizzaList();
        pizzaAdapter = new PizzaAdapter(pizzaList, this);
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaRecyclerView.setAdapter(pizzaAdapter);

        Button cartButton = findViewById(R.id.cartButton);
        Button historyButton = findViewById(R.id.historyButton);

        cartButton.setOnClickListener(v -> {
            if (pizzaCount > 0) {
                ArrayList<Pizza> selectedPizzas = new ArrayList<>();
                for (Pizza pizza : pizzaList) {
                    if (pizza.getQuantity() > 0) {
                        selectedPizzas.add(pizza);
                    }
                }
                if (selectedPizzas.size() > 0) {
                    double total = calculateTotal(selectedPizzas);
                    openCartActivity(selectedPizzas, total);
                } else {
                    Toast.makeText(MainActivity.this, "No pizzas added to the cart", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "No pizzas added to the cart", Toast.LENGTH_SHORT).show();
            }
        });

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private List<Pizza> createPizzaList() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza("Margherita", "Rp 75000", R.drawable.margherita));
        pizzas.add(new Pizza("Pepperoni", "Rp 120000", R.drawable.pepperoni));
        pizzas.add(new Pizza("Hawaiian", "Rp 130000", R.drawable.hawaiian));
        pizzas.add(new Pizza("BBQ Chicken", "Rp 140000", R.drawable.bbq_chicken));
        pizzas.add(new Pizza("Supreme", "Rp 150000", R.drawable.supreme));
        pizzas.add(new Pizza("Veggie Delight", "Rp 130000", R.drawable.veggie_delight));
        pizzas.add(new Pizza("Meat Lovers", "Rp 120000", R.drawable.meat_lovers));
        pizzas.add(new Pizza("Four Cheese", "Rp 110000", R.drawable.four_cheese));
        pizzas.add(new Pizza("Buffalo Chicken", "Rp 140000", R.drawable.buffalo_chicken));
        pizzas.add(new Pizza("Mushroom", "Rp 130000", R.drawable.mushroom));
        pizzas.add(new Pizza("Mediterranean", "Rp 120000", R.drawable.mediterranean));
        pizzas.add(new Pizza("Chicken Fajita", "Rp 100000", R.drawable.chicken_fajita));
        pizzas.add(new Pizza("Olive & Tomato", "Rp 75000", R.drawable.olive_tomato));
        pizzas.add(new Pizza("Tandoori", "Rp 140000", R.drawable.tandoori));
        pizzas.add(new Pizza("Extravaganza", "Rp 190000", R.drawable.extravaganza));
        pizzas.add(new Pizza("Hot & Spicy", "Rp 85000", R.drawable.hot_spicy));
        return pizzas;
    }


    private void openCartActivity(List<Pizza> selectedPizzas, double total) {
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        intent.putExtra("selectedPizzas", new ArrayList<>(selectedPizzas));
        intent.putExtra("total", total);
        startActivityForResult(intent, REQUEST_CART_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CART_ACTIVITY && resultCode == RESULT_OK) {
            ArrayList<Pizza> updatedPizzas = data.getParcelableArrayListExtra("selectedPizzas");
            if (updatedPizzas != null) {
                for (Pizza pizza : pizzaList) {
                    for (Pizza updatedPizza : updatedPizzas) {
                        if (Objects.equals(pizza.getName(), updatedPizza.getName())) {
                            pizza.setQuantity(updatedPizza.getQuantity());
                        }
                    }
                }
                pizzaAdapter.notifyDataSetChanged();
            }
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
    }

    private double calculateTotal(List<Pizza> pizzas) {
        double total = 0;
        for (Pizza pizza : pizzas) {
            int quantity = pizza.getQuantity();
            double price = Double.parseDouble(pizza.getPrice().replaceAll("[^\\d.]+", ""));
            total += (quantity * price);
        }
        return total;
    }

    @Override
    public void onPlusClick(int position) {
        addPizzaToCart(position);
    }

    @Override
    public void onMinusClick(int position) {
        removePizzaFromCart(position);
    }

    @Override
    public void onItemClick(int position) {
        Pizza pizza = pizzaList.get(position);
        MenuDetailDialog dialog = MenuDetailDialog.newInstance(pizza);
        dialog.show(getSupportFragmentManager(), "menu_detail_dialog");
    }

    private void addPizzaToCart(int position) {
        Pizza pizza = pizzaList.get(position);
        int quantity = pizza.getQuantity();
        if (quantity < 10) {
            pizzaCount++;
            quantity++;
            pizza.setQuantity(quantity);
            pizzaAdapter.notifyItemChanged(position);
        } else {
            Toast.makeText(this, "Maximum pizza limit reached", Toast.LENGTH_SHORT).show();
        }
    }

    private void removePizzaFromCart(int position) {
        Pizza pizza = pizzaList.get(position);
        int quantity = pizza.getQuantity();
        if (quantity > 0) {
            pizzaCount--;
            quantity--;
            pizza.setQuantity(quantity);
            pizzaAdapter.notifyItemChanged(position);
        } else {
            Toast.makeText(this, "Cannot remove more pizzas", Toast.LENGTH_SHORT).show();
        }
    }
}
