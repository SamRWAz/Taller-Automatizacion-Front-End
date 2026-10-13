package Atm.task;

import Atm.ui.CarritoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AplicarCupon implements Task {

    private final String codigo;

    public AplicarCupon(String codigo) {
        this.codigo = codigo;
    }

    public static AplicarCupon conCodigo(String codigo) {
        return instrumented(AplicarCupon.class, codigo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CarritoUI.CAMPO_CUPON, isVisible()).forNoMoreThan(10).seconds(),
                Clear.field(CarritoUI.CAMPO_CUPON),
                Enter.theValue(codigo).into(CarritoUI.CAMPO_CUPON),
                Click.on(CarritoUI.BOTON_APLICAR_CUPON)
        );
    }
}
