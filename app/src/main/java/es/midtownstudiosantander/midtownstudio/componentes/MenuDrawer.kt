package es.midtownstudiosantander.midtownstudio.componentes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.midtownstudiosantander.midtownstudio.R
import es.midtownstudiosantander.midtownstudio.model.MenuModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMenuDrawer(
    menuWeb: List<MenuModel>,
    modifier: Modifier = Modifier,
    onClick: (String,Int) -> Unit
){

    LazyColumn (modifier = Modifier){
        item {
            ModalDrawerSheet(
                modifier = Modifier,
            ) {
                CabeceraDrawer(modifier)
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(2.dp))

                //Log.e("JR DRAWER", menuWeb.size.toString())
                MenuSeccion(texto = "Inicio", url = "https://midtownstudiosantander.es/#", onClick = { onClick(it,0) })
                for (menu in menuWeb) {
                    if (menu.submenu == null) {
                        val posBottonBar = when (menu.name) {
                            "Tienda" -> 1
                            "Estilos de baile" -> 2
                            "Contacto" -> 4
                            else -> -1
                        }
                        MenuSeccion(texto = menu.name, url = menu.url, onClick = { onClick(it,posBottonBar) })
                        for (subMenu in menuWeb) {
                            if (subMenu.submenu.equals(menu.name)) {
                                SubMenuSeccion(texto = subMenu.name, url = subMenu.url, onClick = { onClick(it,-1) })
                            }
                        }
                    }
                }
                PoliticasUso{ onClick(it,-1)}
                PieDrawer{ onClick(it,-1)}
            }
        }
    }
}

@Composable
private fun SubMenuSeccion(texto: String, url: String, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(url) }
    ) {
        Row(
            modifier = Modifier.padding(start = 58.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
            Text(
                text = texto,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun MenuSeccion(texto: String, url: String, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(url) }
    ) {
        Row(
            modifier = Modifier.padding(start = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.ArrowForwardIos, contentDescription = null)
            Text(
                text = texto,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun CabeceraDrawer(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 70.dp, vertical = 5.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo_midtown),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoliticasUso( onClick: (String) -> Unit) {
    Spacer(modifier = Modifier.size(5.dp))
    Divider()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Políticas de uso", modifier = Modifier.padding(horizontal = 5.dp), fontSize = 20.sp)
        NavigationDrawerItem(
            label = {
                Text(text = "Política de privacidad")
            },
            selected = false,
            onClick = { onClick("https://midtownstudiosantander.es/politica-privacidad/") },
            icon = { Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null) },
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(text = "Aviso Legal")
            },
            selected = false,
            onClick = { onClick("https://midtownstudiosantander.es/aviso-legal/") },
            icon = { Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null) },
            shape = MaterialTheme.shapes.small
        )
    }
}

@Composable
fun PieDrawer(onClick: (String) -> Unit) {
    Spacer(modifier = Modifier.size(5.dp))
    Divider()
    Spacer(modifier = Modifier.size(10.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.facebook),
            contentDescription = stringResource(R.string.logo_midtown),
            modifier = Modifier
                .size(60.dp)
                .clickable { onClick("https://www.facebook.com/Midtown-Studio-Santander-1496724113777290/") }
        )
        Spacer(modifier = Modifier.size(5.dp))
        Image(
            painter = painterResource(id = R.drawable.instagram),
            contentDescription = stringResource(R.string.logo_midtown),
            modifier = Modifier
                .size(60.dp)
                .clickable { onClick("https://m.instagram.com/midtownstudiosantander/") }
        )
        Spacer(modifier = Modifier.size(5.dp))
        Image(
            painter = painterResource(id = R.drawable.youtube),
            contentDescription = stringResource(R.string.logo_midtown),
            modifier = Modifier
                .size(60.dp)
                .clickable { onClick("https://www.youtube.com/channel/UCVaUgTsatcfFYA6jq7XXOHg") }
        )
    }
}
