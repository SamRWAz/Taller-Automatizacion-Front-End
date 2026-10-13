package Atm.task;

import Atm.ui.CatalogoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class FiltrarCatalogo implements Task {

    private final String categoria;

    public FiltrarCatalogo(String categoria) {
        this.categoria = categoria;
    }

    public static FiltrarCatalogo porCategoria(String categoria) {
        return instrumented(FiltrarCatalogo.class, categoria);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CatalogoUI.SELECT_CATEGORIA, isVisible()).forNoMoreThan(10).seconds(),
                SelectFromOptions.byVisibleText(categoria).from(CatalogoUI.SELECT_CATEGORIA)
        );
    }
}
