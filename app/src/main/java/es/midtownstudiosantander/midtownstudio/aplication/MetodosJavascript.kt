package es.midtownstudiosantander.midtownstudio.aplication

import android.webkit.ValueCallback
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.midtownstudiosantander.midtownstudio.model.MenuModel

class MetodosJavascript {
    companion object {

        fun quitarHeader(view:WebView) {
            view.evaluateJavascript("document.querySelector('header').style.display='none';",null )
        }

        fun quitarFooter(view: WebView) {
            view.evaluateJavascript("document.querySelector('footer').style.display='none';",null )
        }

        fun quitarBtnWhatsapp(view: WebView) {
            view.evaluateJavascript("document.querySelector('.ht-ctc-chat').remove();",null )
        }

        fun obtenerMenuWeb(webView: WebView, listaMenu: (ArrayList<MenuModel>) -> Unit) {
            webView.evaluateJavascript("""
                                (function() {
                                    var menuItems = document.querySelectorAll('.main-navigation .menu-link');
                                    var results = [];
                                    
                                    for (var i = 0; i < menuItems.length; i++) {
                                        var name = menuItems[i].textContent.trim();
                                        var url = menuItems[i].getAttribute('href');
                                        var parentLi = menuItems[i].closest('li');
                                        var hasSubmenuParent = parentLi.closest('ul').closest('li.menu-item-has-children');
                                        var parentName = hasSubmenuParent ? hasSubmenuParent.querySelector('.menu-link').textContent.trim() : null;
                            
                                        // Evita agregar elementos duplicados
                                        var isDuplicate = results.some(item => item.name === name && item.url === url);
                                        if (!isDuplicate) {
                                            var menuItem = {
                                                name: name,
                                                url: url
                                            };
                            
                                            if (parentName) {
                                                menuItem.submenu = parentName;
                                            }
                            
                                            results.push(menuItem);
                                        }
                                    }
                                    return JSON.stringify(results);
                                })(); """,
                ValueCallback<String> { jsonResult ->
                    // Limpia el resultado quitando caracteres no validos
                    val cleanedJson = jsonResult.replace("\\","").trim('"')

                    // Convertir a lista
                    val gson = Gson()
                    val type = object : TypeToken<List<MenuModel>>() {}.type
                    val menus: ArrayList<MenuModel> = gson.fromJson(cleanedJson, type)
                    listaMenu(menus)
                }
            )
        }

        fun verCarrito(view: WebView) {
            view.evaluateJavascript("""
                (function() {
                    var element = document.querySelector('#astra-mobile-cart-drawer');
                    if (element !== null) {
                        element.classList.add('active');
                    }
                })();""", null)
        }
    }
}