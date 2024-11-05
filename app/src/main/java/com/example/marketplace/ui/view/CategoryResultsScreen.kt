package com.example.marketplace.ui.view
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@Composable
fun CategoryResultsScreen(categoryName: String, products: List<Product>, onProductClick: (Product) -> Unit, goBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(title = categoryName, goBack
            )
        Spacer(modifier = Modifier.height(8.dp))
        FilterSection()
        Spacer(modifier = Modifier.height(8.dp))
        ProductGrid(products, onProductClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, goBack: () -> Unit) {
    TopAppBar(
    title = {Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )},
    navigationIcon = {
        IconButton(onClick = goBack ) {
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = null)
        }
})
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
fun ProductGrid(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Cambia según cuántos items quieras por fila
        modifier = Modifier.padding(8.dp)
    ) {
        items(products.size) { index ->
            ProductItem(product = products[index], onProductClick)
        }
    }
}

@Composable
fun ProductItem(product: Product, onProductClick: (Product) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable {onProductClick(product)  },
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



// Preview para ver cómo se ve la pantalla
@Preview(showBackground = true)
@Composable
fun PreviewCategoryResultsScreen() {
    val sampleProducts = listOf(
        Product("Producto 1", "Descripción del producto", 25.99, "", R.drawable.sample_image1),
        Product("Producto 2", "Descripción del producto", 30.50, "", R.drawable.sample_image2),
        Product("Producto 3", "Descripción del producto", 15.00, "", R.drawable.sample_image3),
    )
    CategoryResultsScreen(categoryName = "Ropa", products = sampleProducts, {}, {})
}
