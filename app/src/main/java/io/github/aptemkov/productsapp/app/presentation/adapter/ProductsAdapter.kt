package io.github.aptemkov.productsapp.app.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.aptemkov.productsapp.databinding.ProductItemHorizontalBinding
import io.github.aptemkov.productsapp.domain.models.ProductDomain

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    var data: List<ProductDomain> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    class ProductViewHolder(val binding: ProductItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemHorizontalBinding.inflate(inflater, parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = data[position]
        val context = holder.itemView.context

        with(holder.binding) {
            productTitle.text = product.title
            productDescription.text = product.description
            productPrice.text = "$${product.price}"
            productDiscount.text = "-${product.discountPercentage}%"

            Glide.with(context).load(product.thumbnail)
                .error(ColorDrawable(Color.GRAY))
                .placeholder(ColorDrawable(Color.GRAY))
                .into(productImage)
        }
    }
}