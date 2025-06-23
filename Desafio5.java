import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Desafio5 {
    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();
    static String player = "";

    public static void main(String[] args) {
        createPlayer();
        scanner.close();
    }

    public static void createPlayer() {
        while(true) {
            System.out.println("\n === Adivinhe o Número!! === \n" +
                    "Digite o seu nome: ");
            player = scanner.nextLine();

            if (player.trim().isEmpty()) {
                System.out.println("Nome inválido. Por favor, tente de novo.");
            } else {
                break;
            }
        }
        chooseDifficulty();
    }

    public static void chooseDifficulty() {
        System.out.println("\nNível de dificuldade?\n" +
                "1 - Fácil (1 - 50)\n" +
                "2 - Médio (1 - 100)\n" +
                "3 - Difícil (1 - 200)");

        int level=scanner.nextInt();

        gameSummary(level);
    }

    public static void gameSummary(int option) {
        String level;
        int tries;
        int highestNumber;

        switch (option) {
            case 1:
                level = "Fácil (1 - 50)";
                tries = 5;
                highestNumber = 50;
                break;
            case 2:
                level = "Médio (1 - 100)";
                tries = 10;
                highestNumber = 100;
                break;
            case 3:
                level = "Difícil (1 - 200)";
                tries = 15;
                highestNumber = 200;
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                chooseDifficulty();
                return;
        }

        System.out.printf("\nBem vindo %s! Vamos começar!!\n", player);
        System.out.println("Dificuldade: " + level);
        System.out.println("Tentativas máximas: " + tries);

        playGame(highestNumber, tries);
    }

    public static void playGame(int highNumber, int tries) {
        int rightNumber = random.nextInt(highNumber) + 1;
        ArrayList<Integer> attempts = new ArrayList<>();

        for (int i = 1; i <= tries; i++) {
            System.out.printf("\nTentativa #%d: ", i);
            int guessNumber = scanner.nextInt();

            attempts.add(guessNumber);

            if (guessNumber == rightNumber) {
                System.out.printf("Parabéns! Acertaste em %d tentativas!\n", i);
                gameStatistics(rightNumber, attempts, i, tries);
                return;
            } else if (guessNumber > rightNumber) {
                System.out.println("Alto demais!");
            } else {
                System.out.println("Baixo demais!");
            }
            System.out.println("Tentativas anteriores: " + attempts);
        }
        System.out.println("\nFim do Jogo. O número certo era: " + rightNumber);
        gameStatistics(rightNumber, attempts, tries, tries);
    }

    public static void gameStatistics(int rightNumber, ArrayList<Integer> attempts,int usedTries, int maxTries) {
        System.out.println("\n=== Estatísticas do Jogo ===");
        System.out.println("Jogador: " + player);
        System.out.println("Número sorteado: " + rightNumber);
        System.out.printf("Tentativas usadas: %d/%d\n", usedTries, maxTries);
        System.out.println("Histórico: " + attempts);
        System.out.println("Resultado: " + (usedTries == maxTries ? "Perdeste" : "Vitória"));

        tryAgain();
    }

    public static void tryAgain() {
        scanner.nextLine();
        while (true) {
            System.out.println("\nQuer jogar novamente? (S/N)");
            String answer = scanner.nextLine().trim().toUpperCase();

            if (answer.equals("S")) {
                chooseDifficulty();
                break;
            } else if (answer.equals("N")) {
                System.out.println("\nObrigado por jogar. Até à próxima!");
                break;
            } else {
                System.out.println("\nResposta inválida. Por favor, digite S ou N.");
            }
        }
    }
}
