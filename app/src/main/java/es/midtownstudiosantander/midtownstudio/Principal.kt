package es.midtownstudiosantander.midtownstudio

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.midtownstudiosantander.midtownstudio.aplication.MetodosJavascript
import es.midtownstudiosantander.midtownstudio.componentes.BottomBar
import es.midtownstudiosantander.midtownstudio.componentes.MyMenuDrawer
import es.midtownstudiosantander.midtownstudio.componentes.SplashCarga
import es.midtownstudiosantander.midtownstudio.componentes.TopBarApp
import es.midtownstudiosantander.midtownstudio.model.MenuModel
import es.midtownstudiosantander.midtownstudio.ui.theme.Green40
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Principal() {
    val url = "https://midtownstudiosantander.es/"
    val isLoaded = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    val contadorCarrito = remember { mutableStateOf("0") }
    val textoNoWhatsapp = stringResource(R.string.whatsapp_no)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val selectBottomBar = remember { mutableStateOf(0) }
    var menuWeb = remember { mutableStateOf(listOf<MenuModel>()) }

    val flow: Flow<String> = flow {
        while (true) {
            // Create a CompletableDeferred to wait for the callback.
            val result = CompletableDeferred<String>()

            webView.evaluateJavascript("""
                (function() {
                    var element = document.querySelector('.astra-icon.ast-icon-shopping-bag');
                    if (element !== null) {
                        return element.getAttribute('data-cart-total');
                    } else {
                        return 'X';
                    }
                })();
                """,
                ValueCallback<String> { msg ->
                    // Complete the CompletableDeferred when the callback is called.
                    result.complete(msg ?: "Null result")
                })
            // Wait for the callback to complete and then emit the result.
            emit(result.await())
            delay(1000)  // Wait for one second.
        }
    }

    LaunchedEffect(key1 = Unit) {
        flow.collect { msg ->
            contadorCarrito.value = msg.replace("\"", "")
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            MyMenuDrawer(menuWeb.value, modifier = Modifier) { url,posBottomBar ->
                selectBottomBar.value = posBottomBar
                webView.loadUrl(url)
                coroutineScope.launch { drawerState.close() }
            }
        })
    {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                if (isLoaded.value) {
                    TopBarApp(
                        contador = contadorCarrito.value,
                        onCarrito = {
                            MetodosJavascript.verCarrito(webView)
                        },
                        onMenu = {
                            coroutineScope.launch { drawerState.open() }
                        }
                    )
                }
            },
            bottomBar = {
                if (isLoaded.value) {
                    BottomBar(selectBottomBar) {
                        selectBottomBar.value = it
                        when (it) {
                            0 -> { webView.loadUrl("https://midtownstudiosantander.es/#") }
                            1 -> { webView.loadUrl("https://midtownstudiosantander.es/shop/") }
                            2 -> { webView.loadUrl("https://midtownstudiosantander.es/estilos/") }
                            3 -> { webView.loadUrl("https://midtownstudiosantander.es/#nosotros") }
                            4 -> { webView.loadUrl("https://midtownstudiosantander.es/contact-us/") }
                        }
                    }
                }
            },

            floatingActionButton = {
                if (isLoaded.value) {
                    FloatingActionButton(
                        onClick = {
                            val phoneNumber =
                                "34681661095" // Cambia esto a tu número completo, incluyendo el código de país
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse("whatsapp://send?phone=$phoneNumber")
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                // Mostrando un mensaje si WhatsApp no está instalado
                                Toast.makeText(
                                    context,
                                    textoNoWhatsapp,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        containerColor = Green40
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_whatsapp),
                            contentDescription = "Whatsapp",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { paddingValue ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                MyWebView(
                    url = url,
                    webView = webView,
                    listaMenu = {
                        if (menuWeb.value.isEmpty()) {    //Solo la primera vez
                            menuWeb.value = it    //Asigno los valores del menú a su variables
                        }
                    },
                    onLoad = {
                        isLoaded.value = it
                    }
                )

                if (!isLoaded.value) SplashCarga()
            }
        }
    }


}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MyWebView(url: String, webView: WebView, listaMenu: (ArrayList<MenuModel>) -> Unit, onLoad: (Boolean) -> Unit) {
    val activity = LocalContext.current as Activity

    BackHandler {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            activity.finish()
        }
    }

    AndroidView(
        factory = {
            webView.apply {
                var loadCounter = 0
                webViewClient = object : WebViewClient() {

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        request?.let {
                            val intentUrl = it.url.toString()
                            Log.d("WEBJR", intentUrl)
                            // Manejo de enlaces especiales como mailto, Facebook, Instagram, etc.
                            if (intentUrl.startsWith("mailto:") ||
                                intentUrl.contains("facebook.com") ||
                                intentUrl.contains("youtube.com") ||
                                intentUrl.contains("instagram.com")
                            ) {
                                Log.d("WEBJR", intentUrl)
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentUrl))
                                context.startActivity(intent)
                                return true // Indica que el enlace ha sido manejado
                            } else {
                                Log.d("WEBJR", intentUrl)
                            }
                        }
                        return false // Deja que el WebView maneje el enlace
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        if (view != null) {
                            MetodosJavascript.quitarHeader(view)                            // Quita a cabecera
                            MetodosJavascript.quitarFooter(view)                            // Quita el footer
                            MetodosJavascript.quitarBtnWhatsapp(view)                       // Quita el botón de whatsapp
                            MetodosJavascript.obtenerMenuWeb(view) {    // Lee el menú de la WEB
                                listaMenu(it)
                            }
                        }
                        loadCounter++
                        if (loadCounter == 2) {
                            onLoad(true)
                        }
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        update = { it.loadUrl(url) }
    )
}


