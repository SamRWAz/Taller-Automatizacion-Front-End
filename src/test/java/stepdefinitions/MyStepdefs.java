package stepdefinitions;

import Atm.Question.ElEstado;
import Atm.Question.ElTexto;
import Atm.Question.LaCantidadDeElementos;
import Atm.task.AgregarAlCarrito;
import Atm.task.AplicarCupon;
import Atm.task.BuscarProducto;
import Atm.task.ConfirmarPedido;
import Atm.task.FiltrarCatalogo;
import Atm.task.IniciarSesion;
import Atm.task.IrACheckout;
import Atm.task.IrAlCarrito;
import Atm.ui.CarritoUI;
import Atm.ui.CatalogoUI;
import Atm.ui.CheckoutUI;
import Atm.ui.LoginUI;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class MyStepdefs {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
        theActorCalled("Alquimista");
    }

    // ==========================================
    // Pasos de Autenticación
    // ==========================================

    @Dado("que el usuario abre la página de inicio de sesión {string}")
    public void queElUsuarioAbreLaPaginaDeInicioDeSesion(String url) {
        theActorInTheSpotlight().attemptsTo(Open.url(url));
    }

    @Cuando("ingresa el usuario {string} y la contraseña {string}")
    public void ingresaElUsuarioYLaContrasena(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.conCredenciales(usuario, clave));
    }

    @Entonces("debería ingresar al sistema y ver el saludo {string}")
    public void deberiaIngresarAlSistemaYVerElSaludo(String saludoEsperado) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CatalogoUI.SALUDO_USUARIO, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El saludo en la barra superior contiene el usuario esperado",
                        ElTexto.de(CatalogoUI.SALUDO_USUARIO), containsString(saludoEsperado))
        );
    }

    @Entonces("debería observar el mensaje de error {string}")
    public void deberiaObservarElMensajeDeError(String mensajeError) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(LoginUI.MENSAJE_ERROR, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El mensaje de error de credenciales coincide con el esperado",
                        ElTexto.de(LoginUI.MENSAJE_ERROR), containsString(mensajeError))
        );
    }

    // ==========================================
    // Pasos de Catálogo
    // ==========================================

    @Dado("que el usuario inicia sesión exitosamente en {string} con {string} y {string}")
    public void queElUsuarioIniciaSesionExitosamente(String url, String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(
                Open.url(url),
                IniciarSesion.conCredenciales(usuario, clave),
                WaitUntil.the(CatalogoUI.TITULO_CATALOGO, isVisible()).forNoMoreThan(10).seconds()
        );
    }

    @Cuando("filtra el catálogo por la categoría {string}")
    public void filtraElCatalogoPorLaCategoria(String categoria) {
        theActorInTheSpotlight().attemptsTo(FiltrarCatalogo.porCategoria(categoria));
    }

    @Entonces("debería visualizar productos pertenecientes a la categoría seleccionada")
    public void deberiaVisualizarProductosPertenecientesALaCategoriaSeleccionada() {
        theActorInTheSpotlight().should(
                seeThat("La cantidad de productos mostrados es mayor a 0",
                        LaCantidadDeElementos.visibles(CatalogoUI.TARJETAS_PRODUCTOS), greaterThan(0))
        );
    }

    @Cuando("busca en el catálogo el término {string}")
    public void buscaEnElCatalogoElTermino(String termino) {
        theActorInTheSpotlight().attemptsTo(BuscarProducto.conElNombre(termino));
    }

    @Entonces("debería visualizar el producto {string} en los resultados")
    public void deberiaVisualizarElProductoEnLosResultados(String nombreEsperado) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CatalogoUI.NOMBRES_PRODUCTOS, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El resultado de búsqueda contiene el producto esperado",
                        ElTexto.de(CatalogoUI.NOMBRES_PRODUCTOS), containsString(nombreEsperado))
        );
    }

    @Entonces("debería observar el aviso de búsqueda {string}")
    public void deberiaObservarElAvisoDeBusqueda(String avisoEsperado) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CatalogoUI.MENSAJE_SIN_RESULTADOS, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El aviso de catálogo sin resultados es visible",
                        ElTexto.de(CatalogoUI.MENSAJE_SIN_RESULTADOS), containsString(avisoEsperado))
        );
    }

    // ==========================================
    // Pasos de Carrito y Cupones
    // ==========================================

    @Cuando("agrega {string} unidades del producto {string} al carrito")
    public void agregaUnidadesDelProductoAlCarrito(String cantidad, String producto) {
        theActorInTheSpotlight().attemptsTo(AgregarAlCarrito.elProducto(producto, cantidad));
    }

    @Dado("que tiene agregado {string} unidad del producto {string} en el carrito")
    public void queTieneAgregadoUnidadDelProductoEnElCarrito(String cantidad, String producto) {
        theActorInTheSpotlight().attemptsTo(AgregarAlCarrito.elProducto(producto, cantidad));
    }

    @Y("tiene agregado {string} unidad del producto {string} en el carrito")
    public void yTieneAgregadoUnidadDelProductoEnElCarrito(String cantidad, String producto) {
        theActorInTheSpotlight().attemptsTo(AgregarAlCarrito.elProducto(producto, cantidad));
    }

    @Entonces("el contador del carrito debería mostrar al menos {string} unidades")
    public void elContadorDelCarritoDeberiaMostrarAlMenosUnidades(String unidadesEsperadas) {
        theActorInTheSpotlight().should(
                seeThat("El contador del carrito en la barra coincide",
                        ElTexto.de(CatalogoUI.CONTADOR_CARRITO), equalToIgnoringCase(unidadesEsperadas))
        );
    }

    @Y("debería ver la notificación {string}")
    public void deberiaVerLaNotificacion(String notificacionEsperada) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CatalogoUI.MENSAJE_NOTIFICACION, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El mensaje de notificación del catálogo coincide",
                        ElTexto.de(CatalogoUI.MENSAJE_NOTIFICACION), containsString(notificacionEsperada))
        );
    }

    @Entonces("debería observar la alerta {string}")
    public void deberiaObservarLaAlerta(String alertaEsperada) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CatalogoUI.MENSAJE_NOTIFICACION, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("La alerta de cantidad máxima del catálogo coincide",
                        ElTexto.de(CatalogoUI.MENSAJE_NOTIFICACION), containsString(alertaEsperada))
        );
    }

    @Y("se encuentra en la pantalla del carrito de compras")
    public void seEncuentraEnLaPantallaDelCarritoDeCompras() {
        theActorInTheSpotlight().attemptsTo(
                IrAlCarrito.ahora(),
                WaitUntil.the(CarritoUI.TITULO_CARRITO, isVisible()).forNoMoreThan(10).seconds()
        );
    }

    @Cuando("aplica el cupón de descuento {string}")
    public void aplicaElCuponDeDescuento(String cupon) {
        theActorInTheSpotlight().attemptsTo(AplicarCupon.conCodigo(cupon));
    }

    @Entonces("debería observar el mensaje de cupón {string}")
    public void deberiaObservarElMensajeDeCupon(String mensajeEsperado) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CarritoUI.MENSAJE_CUPON, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El mensaje de resultado del cupón coincide",
                        ElTexto.de(CarritoUI.MENSAJE_CUPON), containsString(mensajeEsperado))
        );
    }

    @Y("el resumen debería reflejar el descuento aplicado")
    public void elResumenDeberiaReflejarElDescuentoAplicado() {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CarritoUI.FILA_DESCUENTO, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("La fila de descuento se encuentra visible en el resumen",
                        ElEstado.esVisible(CarritoUI.FILA_DESCUENTO), is(true))
        );
    }

    // ==========================================
    // Pasos de Checkout
    // ==========================================

    @Dado("que avanza hacia la pantalla de pago y envío")
    public void queAvanzaHaciaLaPantallaDePagoYEnvio() {
        theActorInTheSpotlight().attemptsTo(
                IrACheckout.ahora(),
                WaitUntil.the(CheckoutUI.BOTON_CONFIRMAR, isVisible()).forNoMoreThan(10).seconds()
        );
    }

    @Cuando("intenta confirmar el pedido sin ingresar sus datos")
    public void intentaConfirmarElPedidoSinIngresarSusDatos() {
        theActorInTheSpotlight().attemptsTo(ConfirmarPedido.sinDatos());
    }

    @Entonces("debería observar la alerta de checkout {string}")
    public void deberiaObservarLaAlertaDeCheckout(String mensajeEsperado) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CheckoutUI.MENSAJE_CHECKOUT, isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El mensaje general de validación de checkout coincide",
                        ElTexto.de(CheckoutUI.MENSAJE_CHECKOUT), containsString(mensajeEsperado))
        );
    }

    @Y("el campo de nombre debería señalar el error {string}")
    public void elCampoDeNombreDeberiaSenalarElError(String errorEsperado) {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(CheckoutUI.ERROR_CAMPO.of("nombre"), isVisible()).forNoMoreThan(10).seconds()
        );
        theActorInTheSpotlight().should(
                seeThat("El error de validación bajo el campo de nombre coincide",
                        ElTexto.de(CheckoutUI.ERROR_CAMPO.of("nombre")), containsString(errorEsperado))
        );
    }
}