import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] jugadores = {"Jugador 1", "Jugador 2", "Jugador 3", "Jugador 4",
                "Jugador 5", "Jugador 6", "Jugador 7", "Jugador 8"};
        int[] ids = {1, 2, 3, 4, 5, 6, 7, 8};

        String[] roles = {"Lobo", "Lobo", "Aldeano", "Vidente", "Protector", "Abuelo", "Perro-lobo", "Cazador"};

        asignarRoles(jugadores, roles);

        System.out.println("Roles asignados:");
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println(jugadores[i] + " - ID: " + ids[i] + ": " + roles[i]);
        }

        boolean lobosVivos = true;
        boolean aldeanosVivos = true;
        boolean perroLoboSeleccionado = false;

        Scanner scanner = new Scanner(System.in);

        // Seleccionar el rol del Perro-lobo solo en la primera noche
        System.out.println("Perro-lobo, ¿deseas ser loo o aldeano? (lobo/aldeano)");
        String decision = scanner.next();
        if (decision.equalsIgnoreCase("lobo")) {
            roles[6] = "Lobo";
        } else {
            roles[6] = "Aldeano";
        }

        boolean primeraRonda = true;
        boolean protegido = false;

        while (lobosVivos && aldeanosVivos) {
            System.out.println("\n--- Ronda de noche ---");
            // Lógica para la noche
            if (primeraRonda) {
                // Aquí va la lógica para los otros roles en la primera ronda
                // Protector, Vidente y Lobos
                System.out.println("Protector, selecciona a qué jugador deseas proteger (ingresa el ID):");
                int idJugadorProtegido = obtenerJugadorValido(scanner, jugadores, ids);
                int indiceProtegido = obtenerIndicePorId(ids, idJugadorProtegido);
                System.out.println("El protector ha protegido a " + jugadores[indiceProtegido]);
                protegido = true;

                System.out.println("Lobos, discutan a qué jugador desean eliminar (ingresa el ID):");
                int idJugadorEliminado;
                boolean loboSeleccionadoCorrectamente = false;
                do {
                    idJugadorEliminado = obtenerJugadorValido(scanner, jugadores, ids);
                    int indiceEliminado = obtenerIndicePorId(ids, idJugadorEliminado);
                    String rolEliminado = roles[indiceEliminado];
                    if (rolEliminado.equals("Lobo")) {
                        System.out.println("¡No puedes eliminar a un compañero lobo!");
                    } else {
                        loboSeleccionadoCorrectamente = true;
                    }
                } while (!loboSeleccionadoCorrectamente);

                int indiceEliminado = obtenerIndicePorId(ids, idJugadorEliminado);
                if (idJugadorEliminado == idJugadorProtegido) {
                    System.out.println("¡El jugador protegido ha sido atacado por los lobos pero ha sido protegido por el Protector!");
                } else {
                    System.out.println("Los lobos han decidido eliminar a " + jugadores[indiceEliminado]);
                    eliminarJugador(jugadores, roles, ids, indiceEliminado);
                }

                System.out.println("Vidente, ¿a qué jugador deseas investigar su rol? (ingresa el ID)");
                int idJugadorInvestigado = obtenerJugadorValido(scanner, jugadores, ids);
                int indiceInvestigado = obtenerIndicePorId(ids, idJugadorInvestigado);
                String rolInvestigado = roles[indiceInvestigado];
                System.out.println("El rol de " + jugadores[indiceInvestigado] + " es: " + rolInvestigado);

                primeraRonda = false; // Cambiar el estado de la primera ronda después de que se hayan preguntado todos los roles
            } else {
                // Aquí va la lógica para los roles en las siguientes rondas

                // Protector, Vidente y Lobos
                System.out.println("Protector, selecciona a qué jugador deseas proteger (ingresa el ID):");
                int idJugadorProtegido = obtenerJugadorValido(scanner, jugadores, ids);
                int indiceProtegido = obtenerIndicePorId(ids, idJugadorProtegido);
                if (protegido) {
                    System.out.println("El protector ya ha protegido a un jugador esta noche.");
                } else {
                    System.out.println("El protector ha protegido a " + jugadores[indiceProtegido]);
                }

                System.out.println("Lobos, discutan a qué jugador desean eliminar (ingresa el ID):");
                boolean loboSeleccionadoCorrectamente = false;
                int idJugadorEliminado;
                do {
                    idJugadorEliminado = obtenerJugadorValido(scanner, jugadores, ids);
                    int indiceEliminado = obtenerIndicePorId(ids, idJugadorEliminado);
                    String rolEliminado = roles[indiceEliminado];
                    if (rolEliminado.equals("Lobo")) {
                        System.out.println("¡No puedes eliminar a un compañero lobo!");
                    } else {
                        loboSeleccionadoCorrectamente = true;
                    }
                } while (!loboSeleccionadoCorrectamente);

                int indiceEliminado = obtenerIndicePorId(ids, idJugadorEliminado);
                if (idJugadorEliminado == idJugadorProtegido) {
                    System.out.println("¡El jugador protegido ha sido atacado por los lobos pero ha sido protegido por el Protector!");
                } else {
                    System.out.println("Los lobos han decidido eliminar a " + jugadores[indiceEliminado]);
                    eliminarJugador(jugadores, roles, ids, indiceEliminado);
                }

                System.out.println("Vidente, ¿a qué jugador deseas investigar su rol? (ingresa el ID)");
                int idJugadorInvestigado = obtenerJugadorValido(scanner, jugadores, ids);
                int indiceInvestigado = obtenerIndicePorId(ids, idJugadorInvestigado);
                String rolInvestigado = roles[indiceInvestigado];
                System.out.println("El rol de " + jugadores[indiceInvestigado] + " es: " + rolInvestigado);
            }

            protegido = false; // Reiniciar estado de protegido para la siguiente ronda de noche

            // Simulando el fin de la ronda
            System.out.println("\n--- Ronda de día ---");
            // Lógica para el día
            // ...

            System.out.println("¿Continuar? (s/n)");
            String continuar = scanner.next();
            if (continuar.equalsIgnoreCase("n")) {
                break;
            }
        }

        // Determinar el ganador
        if (lobosVivos) {
            System.out.println("¡Los lobos ganaron!");
        } else if (aldeanosVivos) {
            System.out.println("¡Los aldeanos ganaron!");
        } else {
            System.out.println("¡Es un empate!");
        }
    }

    public static void asignarRoles(String[] jugadores, String[] roles) {
        Random random = new Random();
        for (int i = 0; i < jugadores.length; i++) {
            int indice = random.nextInt(jugadores.length);
            // Intercambiamos los roles en los índices i e indice
            String temp = roles[i];
            roles[i] = roles[indice];
            roles[indice] = temp;
        }
    }

    public static int obtenerIndicePorId(int[] ids, int id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id) {
                return i;
            }
        }
        return -1;
    }

    public static int obtenerJugadorValido(Scanner scanner, String[] jugadores, int[] ids) {
        int idJugador;
        do {
            System.out.println("Ingresa el ID del jugador:");
            idJugador = scanner.nextInt();
            if (!jugadorValido(idJugador, ids)) {
                System.out.println("El jugador ingresado no es válido. Por favor, ingresa el ID de otro jugador.");
            }
        } while (!jugadorValido(idJugador, ids));
        return idJugador;
    }

    public static boolean jugadorValido(int idJugador, int[] ids) {
        for (int id : ids) {
            if (id == idJugador) {
                return true;
            }
        }
        return false;
    }

    public static void eliminarJugador(String[] jugadores, String[] roles, int[] ids, int indice) {
        System.out.println(jugadores[indice] + " ha sido eliminado.");
        roles[indice] = "Eliminado";
        ids[indice] = -1;
    }
}
