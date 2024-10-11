package com.example.marketplace.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShoppingCartScreen(
    products: List<CartItem>,
    onRemoveProduct: (CartItem) -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onCheckout: () -> Unit
) {
    var totalPrice by remember { mutableStateOf(calculateTotal(products)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        Text(
            text = "Shopping Cart",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Listado de productos
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(products) { product ->
                CartItemView(
                    item = product,
                    onRemove = { onRemoveProduct(it) },
                    onUpdateQuantity = { quantity ->
                        onUpdateQuantity(product, quantity)
                        totalPrice = calculateTotal(products)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Total y botón de pago
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Total: $$totalPrice",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 8.dp)
        )
        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Proceed to Checkout")
        }
    }
}

@Composable
fun CartItemView(
    item: CartItem,
    onRemove: (CartItem) -> Unit,
    onUpdateQuantity: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = item.product.imageRes),
                contentDescription = item.product.name,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${item.product.price}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onUpdateQuantity(item.quantity - 1) }) {
                        Text("-")
                    }
                    Text(text = item.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = { onUpdateQuantity(item.quantity + 1) }) {
                        Text("+")
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { onRemove(item) }) {
                Text("Remove")
            }
        }
    }
}

data class Product(
    val name: String,
    val price: Double,
    val imageRes: Int // Recurso de imagen
)

data class CartItem(
    val product: Product,
    var quantity: Int
)

fun calculateTotal(cartItems: List<CartItem>): Double {
    return cartItems.sumOf { it.product.price * it.quantity }
}

// Preview para ver cómo se ve la pantalla
@Preview(showBackground = true)
@Composable
fun PreviewShoppingCartScreen() {
    val sampleProducts = listOf(
        CartItem(Product("Producto 1", 25.99, R.drawable.sample_image1), 1),
        CartItem(Product("Producto 2", 30.50, R.drawable.sample_image2), 2),
        CartItem(Product("Producto 3", 15.00, R.drawable.sample_image3), 1),
    )
    ShoppingCartScreen(
        products = sampleProducts,
        onRemoveProduct = {},
        onUpdateQuantity = { _, _ -> },
        onCheckout = {}
    )
}
