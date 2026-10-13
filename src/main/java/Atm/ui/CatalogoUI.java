package Atm.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CatalogoUI {
    public static final Target TITULO_CATALOGO = Target.the("título de catálogo")
            .located(By.xpath("//h1[contains(text(),'Catálogo')]"));

    public static final Target SALUDO_USUARIO = Target.the("saludo de usuario en la cabecera")
            .located(By.cssSelector(".barra .saludo"));

    public static final Target CAMPO_BUSCAR = Target.the("campo de búsqueda de productos")
            .located(By.id("buscar"));

    public static final Target SELECT_CATEGORIA = Target.the("filtro desplegable de categoría")
            .located(By.name("categoria"));

    public static final Target TARJETAS_PRODUCTOS = Target.the("tarjetas de productos visibles")
            .located(By.cssSelector(".tarjeta-producto"));

    public static final Target NOMBRES_PRODUCTOS = Target.the("nombres de los productos listados")
            .located(By.cssSelector(".nombre-producto"));

    public static final Target PRODUCTO_POR_NOMBRE = Target.the("producto con nombre {0}")
            .locatedBy("//article[contains(@class,'tarjeta-producto')]//h3[contains(text(),'{0}')]");

    public static final Target MENSAJE_SIN_RESULTADOS = Target.the("mensaje de productos no encontrados")
            .located(By.cssSelector(".sin-resultados"));

    public static final Target MENSAJE_NOTIFICACION = Target.the("mensaje flotante de notificación del catálogo")
            .located(By.cssSelector("[data-testid='mensaje-catalogo']"));

    public static final Target CONTADOR_CARRITO = Target.the("contador de unidades en el ícono del carrito")
            .located(By.cssSelector("[data-testid='contador-carrito']"));

    public static final Target INPUT_CANTIDAD_PRODUCTO = Target.the("campo de cantidad para el producto {0}")
            .locatedBy("//article[contains(.,'{0}')]//input[@class='cantidad']");

    public static final Target BOTON_AGREGAR_PRODUCTO = Target.the("botón agregar al carrito del producto {0}")
            .locatedBy("//article[contains(.,'{0}')]//button[contains(@class,'btn-agregar')]");

    public static final Target ENLACE_CARRITO = Target.the("enlace hacia la página del carrito")
            .located(By.id("enlace-carrito"));
}
