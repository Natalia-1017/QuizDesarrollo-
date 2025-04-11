package com.example.codigocolores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Color() {

    val colorNames = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val multiplicadores = listOf(1, 10, 100, 1000, 10000) // Para banda 3: Negro a Amarillo
    val toleranciaColors = listOf("Ninguno", "Dorado", "Plateado")
    val tolerancias = listOf("", "±5%", "±10%")

    var banda1 by remember { mutableStateOf(0) }
    var banda2 by remember { mutableStateOf(0) }
    var banda3 by remember { mutableStateOf(0) }
    var toleranciaIndex by remember { mutableStateOf(0) }
    var resultado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Código de Colores de Resistencias", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        DropdownSelector("Banda 1", colorNames, banda1) { banda1 = it }
        DropdownSelector("Banda 2", colorNames, banda2) { banda2 = it }
        DropdownSelector("Banda 3 (Multiplicador)", colorNames.subList(0, 5), banda3) { banda3 = it }
        DropdownSelector("Tolerancia", toleranciaColors, toleranciaIndex) { toleranciaIndex = it }

        Button(onClick = {
            val valor = ((banda1 * 10) + banda2) * multiplicadores[banda3]
            resultado = "$valor Ω ${tolerancias[toleranciaIndex]}"
        }) {
            Text("Calcular")
        }

        if (resultado.isNotEmpty()) {
            Text(
                "Resultado: $resultado",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DropdownSelector(label: String, items: List<String>, selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.Start) {
        Text(label, fontWeight = FontWeight.SemiBold)
        Box {
            Button(
                onClick = { expanded = true },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(items[selectedIndex])
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onItemSelected(index)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
