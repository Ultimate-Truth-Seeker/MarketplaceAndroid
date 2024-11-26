package com.example.marketplace.ui.view

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marketplace.navigation.BottomNavigationMenu
import com.example.marketplace.ui.model.CartItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketplace.ui.model.CartItemWithProduct
import com.example.marketplace.ui.viewmodel.ShoppingCartViewModel


@Composable
fun ShoppingCartScreen(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCheckout: () -> Unit,
    shoppingCartViewModel: ShoppingCartViewModel = viewModel()
) {
    val cartItems by shoppingCartViewModel.cartItems.collectAsState()
    val totalPrice by remember { derivedStateOf { shoppingCartViewModel.calculateTotal() } }

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
            items(cartItems) { cartItem ->
                CartItemView(
                    item = cartItem,
                    onRemove = { shoppingCartViewModel.removeProductFromCart(it) },
                    onUpdateQuantity = { quantity ->
                        shoppingCartViewModel.updateProductQuantity(cartItem, quantity)
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
    item: CartItemWithProduct,
    onRemove: (CartItemWithProduct) -> Unit,
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
            // Aquí puedes agregar la imagen y detalles del producto
            // ...

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



