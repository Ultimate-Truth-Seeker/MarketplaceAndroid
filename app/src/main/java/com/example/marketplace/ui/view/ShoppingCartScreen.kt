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
import com.example.marketplace.R
import com.example.marketplace.navigation.BottomNavigationMenu
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.Product

@Composable
fun ShoppingCartScreen(
    products: List<CartItem>,
    onRemoveProduct: (CartItem) -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
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
        BottomNavigationMenu(
            onHomeClick = onHomeClick,
            onCartClick = {},
            onProfileClick = onProfileClick
        )
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
                        Text("-")  // Aquí podrías usar un ícono de decremento
                    }
                    Text(text = item.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = { onUpdateQuantity(item.quantity + 1) }) {
                        Text("+")  // Aquí podrías usar un ícono de incremento
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


fun calculateTotal(cartItems: List<CartItem>): Double {
    return cartItems.sumOf { it.product.price * it.quantity }
}

// Preview para ver cómo se ve la pantalla
@Preview(showBackground = true)
@Composable
fun PreviewShoppingCartScreen() {
    val sampleProducts = listOf(
        CartItem(Product("Producto 1", "Descripción del producto", 25.99, R.drawable.sample_image1), 1),
        CartItem(Product("Producto 2", "Descripción del producto", 30.50, R.drawable.sample_image2), 2),
        CartItem(Product("Producto 3", "Descripción del producto", 15.00, R.drawable.sample_image3), 1)
    )

    ShoppingCartScreen(
        products = sampleProducts,
        onRemoveProduct = {},
        onUpdateQuantity = { _, _ -> },
        onHomeClick = {},
        onProfileClick = {},
        onCheckout = {}
    )
}
