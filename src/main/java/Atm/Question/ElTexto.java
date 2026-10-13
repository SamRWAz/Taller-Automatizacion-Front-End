package Atm.Question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class ElTexto implements Question<String> {

    private final Target target;

    public ElTexto(Target target) {
        this.target = target;
    }

    public static ElTexto de(Target target) {
        return new ElTexto(target);
    }

    @Override
    public String answeredBy(Actor actor) {
        target.resolveFor(actor).waitUntilVisible();
        return Text.of(target).answeredBy(actor).trim();
    }
}
