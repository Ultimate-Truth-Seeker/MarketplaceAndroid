package com.example.marketplace.ui.view
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.marketplace.R
import com.example.marketplace.ui.model.Product
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.marketplace.ui.viewmodel.ShoppingCartViewModel


@Composable
fun ProductDetailScreen(
    product: Product,
    onCartClick: () -> Unit,
    onBackClick: () -> Unit,
    shoppingCartViewModel: ShoppingCartViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        item { TopBar("Detalles del Producto", onBackClick) }
        item { ProductImageSection(product) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ProductInfoSection(product) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            AddToCartButton(onCartClick = {
                shoppingCartViewModel.addProductToCart(product)
                onCartClick()
            })
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { RatingsSection(product) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { SellerInfoSection() }
    }
}

@Composable
fun ProductImageSection(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = product.imageUrl),
            contentDescription = product.name,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductInfoSection(product: Product) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = product.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = product.description,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "$${product.price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E88E5)
        )
    }
}

@Composable
fun AddToCartButton(onCartClick: () -> Unit) {
    Button(
        onClick = onCartClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Añadir al Carrito", fontSize = 16.sp)
    }
}

@Composable
fun RatingsSection(product: Product) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Comentarios y Valoraciones",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Aquí se agregarían los comentarios de los usuarios
        // Por ejemplo, iterando sobre una lista de comentarios en el objeto "product"
        Text("Valoración: ★★★★☆", fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun SellerInfoSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Información del Vendedor",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Información ficticia para el vendedor
        Text("Nombre del Vendedor: Juan Pérez", fontSize = 16.sp)
        Text("Localización: Ciudad X", fontSize = 16.sp)
    }
}




