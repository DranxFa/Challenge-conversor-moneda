package com.alura.conversor;

import com.alura.conversor.Model.Moneda;
import com.alura.conversor.Utils.CurrencyService;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CurrencyService currencyService = new CurrencyService();
        Scanner scanner = new Scanner(System.in);

        String mensaje = """
                ********************************************
                Sea Bienvenido/a al Conversor de Moneda =)
                
                1) Dólar ==>> Peso Argentino
                2) Peso Argentino ==>> Dólar
                3) Dólar ==>> Real Brasileño
                4) Real Brasileño ==>> Dólar
                5) Dólar ==>> Peso Colombiano
                6) Peso Colombiano ==>> Peso Argentino
                7) Dólar ==>> Sol Peruano
                8) Sol Peruano ==>> Dólar
                9) Salir
                Elija una ópcion válida:
                ********************************************
                """;

        int opcion;
        while (true) {
            System.out.println(mensaje);

            if (!scanner.hasNextInt()) {
                System.out.println("Ingrese un número válido");
                scanner.next();
                continue;
            }
            opcion = scanner.nextInt();

            if (opcion == 9) {
                System.out.println("Saliendo del programa");
                break;
            }

            Map<Integer, Moneda[]> opciones = Map.of(
                    1, new Moneda[]{Moneda.USD, Moneda.ARS},
                    2, new Moneda[]{Moneda.ARS, Moneda.USD},
                    3, new Moneda[]{Moneda.USD, Moneda.BRL},
                    4, new Moneda[]{Moneda.BRL, Moneda.USD},
                    5, new Moneda[]{Moneda.USD, Moneda.COP},
                    6, new Moneda[]{Moneda.COP, Moneda.USD},
                    7, new Moneda[]{Moneda.USD, Moneda.PEN},
                    8, new Moneda[]{Moneda.PEN, Moneda.USD}
            );

            if (!opciones.containsKey(opcion)){
                System.out.println("Opcion no válida");
                continue;
            }

            System.out.println("Ingresa el valor que deseas convertir");

            if (!scanner.hasNextDouble()) {
                System.out.println("Ingrese un monto válido");
                scanner.next();
                continue;
            }
            double valor = scanner.nextDouble();

            Moneda[] monedas = opciones.get(opcion);
            double resultado = currencyService.convert(monedas[0], monedas[1], valor);
            System.out.printf(
                    "El valor %.2f [%s] corresponde al valor final de ==>>> %.2f [%s]%n",
                    valor, monedas[0], resultado, monedas[1]);
        }
    }
}
