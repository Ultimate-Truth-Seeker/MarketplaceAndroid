package com.example.marketplace.ui.view
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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

@Composable
fun CategoryResultsScreen(categoryName: String, products: List<Product>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(title = categoryName)
        Spacer(modifier = Modifier.height(8.dp))
        FilterSection()
        Spacer(modifier = Modifier.height(8.dp))
        ProductGrid(products)
    }
}

@Composable
fun TopBar(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun FilterSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(onClick = { /* Acción para ordenar por popularidad */ }) {
            Text("Popularidad")
        }
        OutlinedButton(onClick = { /* Acción para ordenar por precio */ }) {
            Text("Precio")
        }
        OutlinedButton(onClick = { /* Acción para ordenar por fecha de lanzamiento */ }) {
            Text("Fecha")
        }
    }
}

@Composable
fun ProductGrid(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Cambia según cuántos items quieras por fila
        modifier = Modifier.padding(8.dp)
    ) {
        items(products.size) { index ->
            ProductItem(product = products[index])
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = product.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "$${product.price}",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

// Modelo de datos para productos
data class Product(
    val name: String,
    val price: Double,
    val imageRes: Int // Recurso de imagen
)

// Preview para ver cómo se ve la pantalla
@Preview(showBackground = true)
@Composable
fun PreviewCategoryResultsScreen() {
    val sampleProducts = listOf(
        Product("Producto 1", 25.99, R.drawable.sample_image1),
        Product("Producto 2", 30.50, R.drawable.sample_image2),
        Product("Producto 3", 15.00, R.drawable.sample_image3),
        // Agrega más productos de ejemplo
    )
    CategoryResultsScreen(categoryName = "Ropa", products = sampleProducts)
}
