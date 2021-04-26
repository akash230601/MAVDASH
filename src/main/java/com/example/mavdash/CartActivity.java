package com.example.mavdash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mavdash.Model.Cart;
import com.example.mavdash.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.example.mavdash.Prevalent.Prevalent;

public class CartActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextprocessbtn;
    private TextView txtTotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextprocessbtn = (Button) findViewById(R.id.NextProcessButton);
        txtTotalAmount = (TextView) findViewById(R.id.TotalPrice);

        protected void OnStart()
        {
            super.onStart();

            final  DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
            FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                    .setQuery(cartListRef.child("User View").child(Prevalent.currentOnlineUser
                    .getPhone()).child("Products"), Cart.class).build() ;

            FirebaseRecyclerAdapter<Cart,CartViewHolder> adapter
                    = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
            {
                @Override
                protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
                {
                    holder.txtProductQuantity.setText(model.getQuantity());
                    holder.txtProductPrice.setText(model.getPrice());
                    holder.txtProductName.setText(model.getPname());

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            CharSequence options[] = new CharSequence[]
                                    {
                                            "Edit","Delete"
                                    };
                            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("Cart Options:");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i)
                                {
                                    if(i==0)
                                    {
                                        Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    }

                                }
                            });

                        }
                    });


                }
                @NonNull
                @Override
                public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                {
                    View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                    CartViewHolder holder = new CartViewHolder(view);
                    return holder;

                }
            };

        }

    }
}