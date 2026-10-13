package Atm.task;

import Atm.ui.CatalogoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.Keys;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class BuscarProducto implements Task {

    private final String terminoBusqueda;

    public BuscarProducto(String terminoBusqueda) {
        this.terminoBusqueda = terminoBusqueda;
    }

    public static BuscarProducto conElNombre(String terminoBusqueda) {
        return instrumented(BuscarProducto.class, terminoBusqueda);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CatalogoUI.CAMPO_BUSCAR, isVisible()).forNoMoreThan(15).seconds(),
                Clear.field(CatalogoUI.CAMPO_BUSCAR),
                Enter.theValue(terminoBusqueda).into(CatalogoUI.CAMPO_BUSCAR).thenHit(Keys.ENTER)
        );
    }
}
