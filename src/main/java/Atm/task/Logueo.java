package Atm.task;

import Atm.PO.PaginaLogin;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Logueo implements Task {

    private final String usu;
    private final String contras;

    public Logueo(String usu, String contras) {
        this.usu = usu;
        this.contras = contras;
    }

    public static Logueo go(String usu, String contras) {
        return instrumented(Logueo.class, usu, contras);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(PaginaLogin.usuario, isVisible()).forNoMoreThan(15).seconds(),
            Enter.theValue(usu).into(PaginaLogin.usuario),
            WaitUntil.the(PaginaLogin.password, isVisible()).forNoMoreThan(15).seconds(),
            Enter.theValue(contras).into(PaginaLogin.password)
        );

        System.out.println("Se ingresaron las credenciales para el usuario: " + usu);
    }
}