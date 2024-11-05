package com.example.marketplace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.marketplace.R
import com.example.marketplace.ui.model.CartItem
import com.example.marketplace.ui.model.Product


@Composable
fun CheckoutScreen(
    products: List<CartItem>,
    totalPrice: Double,
    onConfirmPurchase: () -> Unit,
) {
    var selectedAddress by remember { mutableStateOf("1234 Elm Street, City, Country") }
    var cardNumber by remember { mutableStateOf("") }
    var cardExpiration by remember { mutableStateOf("") }
    var cardCvv by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Confirm Purchase",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Resumen de productos y total
        ProductSummary(products, totalPrice)

        Spacer(modifier = Modifier.height(16.dp))

        // Detalles de pago
        PaymentDetails(
            cardNumber = cardNumber,
            onCardNumberChange = { cardNumber = it },
            cardExpiration = cardExpiration,
            onCardExpirationChange = { cardExpiration = it },
            cardCvv = cardCvv,
            onCardCvvChange = { cardCvv = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selección de dirección
        ShippingAddress(
            selectedAddress = selectedAddress,
            onAddressChange = { selectedAddress = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para confirmar la compra
        Button(
            onClick = onConfirmPurchase,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Confirm Purchase")
        }
    }
}

@Composable
fun ProductSummary(products: List<CartItem>, totalPrice: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            products.forEach { item ->
                Text(text = "${item.product.name} x${item.quantity} - $${item.product.price * item.quantity}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Total: $$totalPrice",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun PaymentDetails(
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    cardExpiration: String,
    onCardExpirationChange: (String) -> Unit,
    cardCvv: String,
    onCardCvvChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = cardNumber,
            onValueChange = onCardNumberChange,
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = cardExpiration,
                onValueChange = onCardExpirationChange,
                label = { Text("Expiration Date") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = cardCvv,
                onValueChange = onCardCvvChange,
                label = { Text("CVV") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ShippingAddress(
    selectedAddress: String,
    onAddressChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Shipping Address",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = selectedAddress,
            onValueChange = onAddressChange,
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewCheckoutScreen() {
    val sampleProducts = listOf(
        CartItem(Product("Producto 1", "Descripción del producto", 25.99, "", R.drawable.sample_image1), 1),
        CartItem(Product("Producto 2", "Descripción del producto", 30.50, "", R.drawable.sample_image2), 2),
        CartItem(Product("Producto 3", "Descripción del producto", 15.00, "", R.drawable.sample_image3),1),
    )
    CheckoutScreen(
        products = sampleProducts,
        totalPrice = 116.99,
        onConfirmPurchase = {}
    )
}
