package es.midtownstudiosantander.midtownstudio.componentes

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.midtownstudiosantander.midtownstudio.ui.theme.Purple40
import es.midtownstudiosantander.midtownstudio.ui.theme.PurpleGrey80


@Composable
fun BottomBar(select:MutableState<Int>, modifier: Modifier = Modifier, onSelect: (Int) -> Unit) {


    BottomAppBar(
        modifier = modifier
            .height(48.dp)
            .clipToBounds(),
        containerColor = Purple40,
        tonalElevation = 5.dp,
    ) {
        NavigationBarItem(
            selected = select.value == 0,
            onClick = {
                select.value = 0
                onSelect(0)
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Home",
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                indicatorColor = PurpleGrey80,
                selectedIconColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = select.value == 1,
            onClick = {
                select.value = 1
                onSelect(1)
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.ShoppingCart,
                    contentDescription = "Recetas",
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                indicatorColor = PurpleGrey80,
                selectedIconColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = select.value == 2,
            onClick = {
                select.value = 2
                onSelect(2)
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Favoritos",
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                indicatorColor = PurpleGrey80,
                selectedIconColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = select.value == 3,
            onClick = {
                select.value = 3
                onSelect(3)
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Groups,
                    contentDescription = "Favoritos",
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                indicatorColor = PurpleGrey80,
                selectedIconColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = select.value == 4,
            onClick = {
                select.value = 4
                onSelect(4)
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Mail,
                    contentDescription = "Comunidad",
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                indicatorColor = PurpleGrey80,
                selectedIconColor = Color.Black
            )
        )
    }
}