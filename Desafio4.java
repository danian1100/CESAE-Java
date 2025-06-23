import java.util.Scanner;

public class Desafio4 {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        addText();
        scanner.close();
    }

    public static void addText() {
        String text;

        while (true) {
            System.out.println("\n **** Analisador de Texto **** \n" +
                    "Digite uma frase para análise: ");
            text = scanner.nextLine();

            if (text.trim().isEmpty()) {
                System.out.println("Texto inválido. Por favor, digite algo.");
            } else {
                break;
            }
        }
        textAnalysis(text);
    }

    public static void textAnalysis(String text) {
        System.out.println("\n **** Resultados da Análise **** \n");
        System.out.printf("Texto original: \"%s\"\n", text);

        String formatText = text.trim().replaceAll("\\s+", " ");
        System.out.printf("Texto formatado: \"%s\"\n", formatText);

        basicAnalysis(formatText);
        detailAnalysis(formatText);
        searchAnalysis(formatText);
    }

    public static void basicAnalysis(String text) {
        String[] words = text.split(" ");

        int countWords = words.length;
        String longestWord = "";
        String shortestWord = words[0];
        int totalCharacters = 0;

        for (String word : words) {
            int len = word.length();
            totalCharacters += len;

            if (len > longestWord.length()) longestWord = word;
            if (len < shortestWord.length()) shortestWord = word;
        }

        double median = (double) totalCharacters / countWords;

        System.out.println("\n--- Análise Básica ---");
        System.out.printf("Quantidade de palavras: %d\n", countWords);
        System.out.println("Palavra mais longa: " + longestWord);
        System.out.println("Palavra mais curta: " + shortestWord);
        System.out.printf("Média de comprimento: %.2f letras.\n", median);
    }

    public static void detailAnalysis(String text) {
        int totalChars = text.length();
        int letters = 0;
        int spaces = 0;
        int capsLetters = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) letters++;
            if (Character.isWhitespace(c)) spaces++;
            if (Character.isUpperCase(c)) capsLetters++;
        }

        System.out.println("\n--- Análise Detalhada ---");
        System.out.println("Caracteres totais: " + totalChars);
        System.out.println("Total de letras: " + letters);
        System.out.println("Total de espaços: " + spaces);
        System.out.println("Letras maiúsculas: " + capsLetters);
    }

    public static void searchAnalysis(String text) {
        System.out.println("\n--- Pesquisa de Palavras --");
        System.out.print("Insira a palavra a procurar: ");
        String targetword = scanner.nextLine().toLowerCase();

        String[] words = text.split(" ");

        boolean wordFound = false;
        System.out.println("\n--- Pesquisa... ---");

        for (int i = 0; i < words.length; i++) {
            if (words[i].equalsIgnoreCase(targetword)) {
                System.out.printf("Encontrada \"%s\" na posição %d.\n", words[i], i + 1);
                wordFound = true;
                break;
            }
        }

        if (!wordFound) {
            System.out.println("Palavra não encontrada.");
        }
    }
}
