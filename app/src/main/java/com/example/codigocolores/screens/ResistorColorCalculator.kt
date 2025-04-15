package com.example.codigocolores

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource


@Composable
fun Color() {
    val coloresDigitos = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val valores = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    val coloresMultiplicador = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo")
    val multiplicadores = listOf(1.0, 10.0, 100.0, 1000.0, 10000.0)

    val coloresTolerancia = listOf("Dorado", "Plateado", "Ninguno")
    val tolerancias = listOf("±5%", "±10%", "±20%")

    var banda1 by remember { mutableStateOf(0) }
    var banda2 by remember { mutableStateOf(0) }
    var banda3 by remember { mutableStateOf(0) }
    var toleranciaIndex by remember { mutableStateOf(0) }
    var resultado by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxWidth() // Solo ocupa todo el ancho
            .wrapContentHeight(), // Permite que el alto crezca más allá del tamaño de la pantalla
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp), // Márgenes internos
                verticalArrangement = Arrangement.Top, // Para alinear desde arriba
                horizontalAlignment = Alignment.CenterHorizontally // Mantener el contenido centrado horizontalmente
        ) {
            Image(
                painter = painterResource(id = R.drawable.colores),
                contentDescription = "Resistencia",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 24.dp)
            )
            Text(
                text = "Código de Colores",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Esto lo centra
            )
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DropdownSelector("Banda 1 (Primer dígito)", coloresDigitos, banda1) { banda1 = it }
                    DropdownSelector("Banda 2 (Segundo dígito)", coloresDigitos, banda2) { banda2 = it }
                    DropdownSelector("Banda 3 (Multiplicador)", coloresMultiplicador, banda3) { banda3 = it }
                    DropdownSelector("Banda 4 (Tolerancia)", coloresTolerancia, toleranciaIndex) { toleranciaIndex = it }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    val valor = ((valores[banda1] * 10) + valores[banda2]) * multiplicadores[banda3]
                    resultado = "${valor.toInt()} Ω ${tolerancias[toleranciaIndex]}"
                },

                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Calcular", color = Color.White, fontSize = 18.sp)
                Icon(
                    imageVector = Icons.Rounded.Calculate,
                    contentDescription = "Calcular",
                    tint = Color.White

                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            if (resultado.isNotEmpty()) {
                Text(
                    "Resultado: $resultado",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@Composable
fun DropdownSelector(
    label: String,
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    buttonWidth = coordinates.size.width
                }
        ) {
            OutlinedButton(
                onClick = { expanded = true },
                shape = RoundedCornerShape(
                    bottomStart = if (expanded) 0.dp else 12.dp,
                    bottomEnd = if (expanded) 0.dp else 12.dp,
                    topStart = 12.dp,
                    topEnd = 12.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Text(
                    text = items[selectedIndex],
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Desplegar menú"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { buttonWidth.toDp() })
                    .heightIn(max = 60.dp) // Limita la altura del menú a 60dp
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
            ) {
                // Añadir un mensaje de texto para indicar que debe deslizarse
                if (items.size > 2) {
                    Text(
                        text = "Desliza hacia arriba para ver las opciones",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                // Este forEach es para ajustar la altura limitada
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onItemSelected(index)
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

