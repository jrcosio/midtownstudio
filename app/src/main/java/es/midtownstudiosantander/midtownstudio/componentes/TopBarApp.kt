package es.midtownstudiosantander.midtownstudio.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.midtownstudiosantander.midtownstudio.R
import es.midtownstudiosantander.midtownstudio.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(contador: String, onCarrito: () -> Unit, onMenu: () -> Unit) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    modifier = Modifier.height(60.dp),
                    contentDescription = stringResource(R.string.logotipo_de_midtownstudio)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenu) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menú",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )
            }

        },
        actions = {
            IconButton(onClick = onCarrito) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "Carrito",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column() {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = contador,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
//            val textoNoWhatsapp = stringResource(R.string.whatsapp_no)
//            IconButton(onClick = {
//                val phoneNumber =
//                    "34681661095" // Cambia esto a tu número completo, incluyendo el código de país
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("whatsapp://send?phone=$phoneNumber")
//                try {
//                    context.startActivity(intent)
//                } catch (e: Exception) {
//                    // Mostrando un mensaje si WhatsApp no está instalado
//                    Toast.makeText(
//                        context,
//                        textoNoWhatsapp,
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }) {
//                Card(
//                    modifier = Modifier.fillMaxSize(),
//                    colors = CardDefaults.cardColors(Green40)
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.icon_whatsapp),
//                        contentDescription = "Whatsapp",
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(2.dp)
//                    )
//                }
//            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Purple40)

    )
}