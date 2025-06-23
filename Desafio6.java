import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Desafio6 {
    public static Scanner scanner = new Scanner(System.in);

    static ArrayList<String> titles = new ArrayList<>();
    static ArrayList<String> authors = new ArrayList<>();
    static ArrayList<String> isbns = new ArrayList<>();
    static ArrayList<Integer> totalStock = new ArrayList<>();
    static ArrayList<Integer> availableStock = new ArrayList<>();
    static ArrayList<Integer> activeLoans = new ArrayList<>();

    static HashMap<String, String> loanedReaders = new HashMap<>();
    static HashMap<String, LocalDate> loanDates = new HashMap<>();


    public static void main(String[] args) {
        menuLibrary();
        scanner.close();
    }

    public static void menuLibrary() {
        int option;

        do {
            System.out.println("""
                     \n=== Sistema da Biblioteca ===
                    1. Adicionar Livro
                    2. Procurar Livro
                    3. Mostrar Todos os Livros
                    4. Empréstimo de Livro
                    5. Devolução de Livro
                    6. Ver Estatísticas
                    7. Sair""");

            option=scanner.nextInt();
            System.out.println("Foi escolhida a tarefa: " + option);

            switch (option){
                case 1:
                    scanner.nextLine();
                    addBook();
                    break;
                case 2:
                    scanner.nextLine();
                    searchBook();
                    break;
                case 3:
                    scanner.nextLine();
                    showBooks();
                    break;
                case 4:
                    scanner.nextLine();
                    borrowBook();
                    break;
                case 5:
                    scanner.nextLine();
                    returnBook();
                    break;
                case 6:
                    scanner.nextLine();
                    libraryStatistics();
                    break;
                case 7:
                    System.out.println("Obrigado por usar o Sistema da Biblioteca.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, repita.");
                    break;
            }

        }while(option != 7);
    }

    //VALIDAÇÕES E OUTROS
    public static String validateText(String input) {
        while (input.trim().isEmpty()) {
            System.out.print("Entrada inválida. Digite novamente: ");
            input = scanner.nextLine();
        }
        return input.trim();
    }

    public static String validateUniqueISBN(String isbn) {
        while (true) {
            isbn = isbn.trim();
            if (isbn.length() != 10) {
                System.out.println("ISBN deve ter exatamente 10 caracteres.");
            } else if (isbns.contains(isbn)) {
                System.out.println("ISBN já existente. Introduza um ISBN único.");
            } else {
                break;
            }
            System.out.print("Digite o ISBN: ");
            isbn = scanner.nextLine();
        }
        return isbn;
    }

    public static String validateExistingISBN() {
        String isbn;
        int index = -1;
        do {
            System.out.print("Digite o ISBN do livro (10 dígitos): ");
            isbn = scanner.nextLine().trim();

            if (isbn.length() != 10) {
                System.out.println("O ISBN deve ter exatamente 10 dígitos.");
                continue;
            }

            index = isbns.indexOf(isbn);
            if (index == -1) {
                System.out.println("Livro não encontrado.");
            }
        } while (isbn.length() != 10 || index == -1);
        return isbn;
    }

    public static int validateInt(String input) {
        while (true) {
            input = input.trim();

            try {
                int number = Integer.parseInt(input);
                if (number < 0) {
                    System.out.println("Por favor, introduza um número inteiro positivo.");
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Introduza um número inteiro.");
            }
            System.out.print("Digite novamente: ");
            input = scanner.nextLine();
        }
    }

    public static String getBookState(int i) {
        if (activeLoans.get(i) == 0) return "Disponível";
        else if (availableStock.get(i) == 0) return "Totalmente Emprestado";
        else return "Parcialmente Emprestado";
    }

    // METODOS PRINCIPAIS
    public static void addBook() {
        if (titles.size() >= 100) {
            System.out.println("Limite máximo de 100 livros atingido.");
            return;
        }

        System.out.println("\n --- Adicionar Livro ---\n");

        System.out.print("Digite o título: ");
        String title = validateText(scanner.nextLine());

        System.out.print("Digite o autor: ");
        String author = validateText(scanner.nextLine());

        System.out.print("Digite o ISBN: ");
        String isbn = validateUniqueISBN(scanner.nextLine());

        System.out.print("Digite a quantidade: ");
        int stock = validateInt(scanner.nextLine());

        titles.add(title);
        authors.add(author);
        isbns.add(isbn);
        totalStock.add(stock);
        availableStock.add(stock);
        activeLoans.add(0);

        System.out.println("\n === Livro adicionado com sucesso! === \n");
        System.out.printf("Título: %s\nAutor: %s\nISBN:%-10s\nQuantidade disponível: %d\n",
                title, author, isbn, stock);
    }

    public static void searchBook() {
        if (titles.isEmpty()) {
            System.out.println("Nenhum livro registado.");
            return;
        }

        int option;

        do {
            System.out.println("""
                     \n--- Procurar Livros ---
                    1. Procurar por Título
                    2. Procurar por Autor
                    3. Procurar por ISBN
                    4. Voltar ao inicío""");

            option=scanner.nextInt();
            System.out.println("Tipo de pesquisa: " + option);

            switch (option) {
                case 1:
                case 2:
                case 3:
                    scanner.nextLine();
                    int index = seachBy(option);
                    System.out.println("\n === Resultados Encontrados === ");
                    System.out.printf("Título: %s\nAutor: %s\nISBN:%-10s\nQuantidade disponível: %d/%d cópias\nEstado: %s\n",
                            titles.get(index), authors.get(index), isbns.get(index), availableStock.get(index), totalStock.get(index), getBookState(index));
                    break;
                case 4:
                    System.out.println("A sair da \"Pesquisa de Livro\"");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, repita.");
                    break;
            }

        } while (option != 4);
    }

    public static int seachBy(int option) {
        System.out.print("Insira o termo a procurar: ");
        String targetword = scanner.nextLine().trim().toLowerCase();
        boolean wordFound = false;
        int index = 0;

        switch (option) {
            case 1:
                for (int i = 0; i < titles.size(); i++) {
                    if (titles.get(i).equalsIgnoreCase(targetword)) {
                        index = i;
                        wordFound = true;
                        break;
                    }
                }

                if (!wordFound) {
                    System.out.println("Título não encontrado. Tente novamente.");
                    searchBook();
                    break;
                }
                break;
            case 2:
                for (int i = 0; i < authors.size(); i++) {
                    if (authors.get(i).equalsIgnoreCase(targetword)) {
                        index = i;
                        wordFound = true;
                        break;
                    }
                }

                if (!wordFound) {
                    System.out.println("Autor não encontrado. Tente novamente.");
                    searchBook();
                    break;
                }
                break;
            case 3:
                for (int i = 0; i < authors.size(); i++) {
                    if (isbns.get(i).equalsIgnoreCase(targetword)) {
                        index = i;
                        wordFound = true;
                        break;
                    }
                }

                if (!wordFound) {
                    System.out.println("ISBN não encontrado. Tente novamente.");
                    searchBook();
                    break;
                }
                break;
            default:
                break; //este nunca irá acontecer
        }
        return index;
    }

    public static void showBooks() {
        if (titles.isEmpty()) {
            System.out.println("Nenhum livro registado.");
            return;
        }

        System.out.println(" === Lista Completa de Livros === \n");

        for (int i = 0; i < titles.size(); i++) {
            System.out.printf("Título: %s\nAutor: %s\nISBN:%-10s\nQuantidade Total: %d\n",
                    titles.get(i), authors.get(i), isbns.get(i), totalStock.get(i));
            System.out.printf("Quantidade disponível: %d\nEstado: %s\nEmpréstimos ativos: %d\n",
                    availableStock.get(i), getBookState(i), activeLoans.get(i));

            if (i != titles.size() - 1) System.out.println("-------------------------------");
        }
    }

    public static void borrowBook() {
        if (titles.isEmpty()) {
            System.out.println("Não é possível realizar empréstimos no momento.");
            return;
        }

        System.out.println("\n --- Empréstimo de Livro ---\n");

        String isbn = validateExistingISBN();
        int index = isbns.indexOf(isbn);

        if (availableStock.get(index) == 0) {
            System.out.println("Não há cópias disponíveis para empréstimo.");
            return;
        }

        System.out.print("Digite o nome do leitor: ");
        String readerName = validateText(scanner.nextLine());

        String readerID;
        while (true) {
            System.out.print("Digite o número de leitor (ex: L00001): ");
            readerID = scanner.nextLine().trim().toUpperCase();
            if (!readerID.matches("L\\d{5}")) {
                System.out.println("Formato inválido. Deve ser L seguido de 5 dígitos.");
            } else break;
        }

        String loanKey = isbn + "-" + readerID;
        loanedReaders.put(readerID, readerName);
        loanDates.put(loanKey, LocalDate.now());

        availableStock.set(index, availableStock.get(index) - 1);
        activeLoans.set(index, activeLoans.get(index) + 1);

        System.out.println("\n === Empréstimo realizado com sucesso! === \n");
        System.out.printf("Título: %s\nLeitor: %s (%s)\nData de empréstimo: %s\nData de devolução: %s\nCópias restantes: %d/%d\n",
                titles.get(index), readerName, readerID, LocalDate.now(), LocalDate.now().plusDays(7)
        , availableStock.get(index), totalStock.get(index));
    }

    public static void returnBook() {
        if (activeLoans.isEmpty()) {
            System.out.println("Não existem empréstimos realizados.");
            return;
        }

        System.out.println("\n --- Devolução de Livro ---\n");

        String isbn = validateExistingISBN();
        int index = isbns.indexOf(isbn);

        String readerID;
        while (true) {
            System.out.print("Digite o número de leitor (ex: L00001): ");
            readerID = scanner.nextLine().trim().toUpperCase();
            if (!readerID.matches("L\\d{5}")) {
                System.out.println("Formato inválido. Deve ser L seguido de 5 dígitos.");
            } else if (!loanedReaders.containsKey(readerID)) {
                System.out.println("Não existe nenhum leitor com esse número.");
            } else break;
        }

        String loanKey = isbn + "-" + readerID;
        if (!loanDates.containsKey(loanKey)) {
            System.out.println("Nenhum empréstimo ativo encontrado para esse leitor.");
            return;
        }

        LocalDate loanDate = loanDates.remove(loanKey);
        String readerName = loanedReaders.get(readerID);

        availableStock.set(index, availableStock.get(index) + 1);
        activeLoans.set(index, activeLoans.get(index) - 1);

        LocalDate today = LocalDate.now();
        boolean late = today.isAfter(loanDate.plusDays(7));

        System.out.println("\n === Devolução realizada com sucesso! === \n");
        System.out.printf("Título: %s\nLeitor: %s \nData de devolução: %s\n", titles.get(index), readerName, today);
        System.out.println("Estado: " + (late ? "Fora do prazo" : "Dentro do prazo"));
        System.out.printf("Cópias disponíveis: %d/%d\n", availableStock.get(index), totalStock.get(index));
    }

    public static void libraryStatistics() {
        if (titles.isEmpty()) {
            System.out.println("Não existem estatísticas para apresentar.");
            return;
        }

        int totalCopies = 0, totalLoans = 0;
        for (int i = 0; i < totalStock.size(); i++) {
            totalCopies += totalStock.get(i);
            totalLoans += activeLoans.get(i);
        }

        System.out.println("\n=== Estatísticas da Biblioteca ===");

        System.out.println("Total de livros cadastrados: " + titles.size());
        System.out.println("Total de cópias: " + totalCopies);
        System.out.println("Livros emprestados: " + totalLoans);
        System.out.println("Livros disponíveis: " + (totalCopies - totalLoans));
    }
}
