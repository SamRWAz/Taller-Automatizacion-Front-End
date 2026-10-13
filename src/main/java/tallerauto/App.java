package tallerauto;

public class App {
    public String getGreeting() {
        return "¡Proyecto TallerAuto configurado correctamente con Gradle y Groovy!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
