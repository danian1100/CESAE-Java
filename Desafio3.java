import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Desafio3 {
    public static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> food = new ArrayList<>(10);
    static ArrayList<Double> price = new ArrayList<>(10);

    public static void main(String[] args) {
        getItems();
        scanner.close();
    }

    public static void getItems() {
        System.out.println("\n === Gestor de Lista de Compras === ");

        while (true) {
            System.out.print("Quantos itens deseja adicionar (1-10): \n");
            try {
                int countItems = scanner.nextInt();
                scanner.nextLine();
                if (countItems < 1 || countItems > 10) {
                    System.out.println("Número inválido. Deve estar entre 1 e 10.\n");
                    continue;
                }

                addItems(countItems);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Use apenas números.\n");
                scanner.nextLine();
            }
        }
    }

    public static void addItems(int countItems) {
        for (int i = 1; i <= countItems; i++) {
            while (true) {
                System.out.printf("\n---- Item %d ----\n", i);

                System.out.print("Nome do Item: ");
                String product = scanner.nextLine().trim();
                if (product.isEmpty()) {
                    System.out.println("Nome inválido. Tente de novo.");
                    continue;
                }

                System.out.print("Preço do Item: ");
                double cost;
                try {
                    cost = scanner.nextDouble();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Tente novamente.");
                    scanner.nextLine();
                    continue;
                }

                food.add(product);
                price.add(cost);
                break;
            }
        }
        accounting();
    }

    public static void accounting(){
        double subtotal = 0;
        double iva = 0.23;

        for (Double cost: price ) {
            subtotal += cost;
        }

        double priceOfIVA = subtotal * iva;
        double totalPrice = subtotal + priceOfIVA;

        showList(subtotal, priceOfIVA, totalPrice);
    }

    public static void showList(double subtotal, double priceOfIVA, double totalPrice){
        System.out.println("\n==== Lista de Compras ====");

        for (int i = 0; i < food.size(); i++) {
            System.out.printf("%d. %s ....... %.2f€\n", i + 1, food.get(i), price.get(i));
        }

        System.out.println("\n---- Total ----");
        System.out.printf("Subtotal: %.2f€\n", subtotal);
        System.out.printf("IVA (23%%): %.2f€\n", priceOfIVA);
        System.out.printf("Total Final: %.2f€\n", totalPrice);

        moreItems();
    }

   public static void moreItems() {
       if (food.size() == 10) {
           System.out.println("\nAlcançou o limite de 10 itens. Obrigado por usar o gestor de compras.");
           return;
       }

       while (food.size() < 10) {
           System.out.print("\nDeseja adicionar mais itens? (S/N): ");
           char answer = scanner.nextLine().trim().toUpperCase().charAt(0);

           if (answer != 'S' && answer != 'N') {
               System.out.println("Opção inválida. Responda S ou N.");
               continue;
           }

           if (answer == 'N') {
               break;
           }

           int remaining = 10 - food.size();
           System.out.printf("\nPode adicionar mais %d item(s).\n", remaining);
           System.out.println("Quantos itens deseja adicionar agora? ");
           int countItems;
           try {
               countItems = scanner.nextInt();
               scanner.nextLine();

               if (countItems < 1 ||countItems > remaining) {
                   System.out.println("Número inválido. Tente novamente.\n");
                   continue;
               }

               addItems(countItems);
           } catch (InputMismatchException e) {
               System.out.println("Entrada inválida. Use apenas números.\n");
               scanner.nextLine();
           }
       }
   }
}
