import java.util.InputMismatchException;
import java.util.Scanner;

public class Desafio2 {
    public static Scanner scanner = new Scanner(System.in);
    public static String name = "";
    public static double grade = 0;

    public static void main(String[] args) {
        getStudent();
        scanner.close();
    }

    public static void getStudent() {
        System.out.println("\nDigite o nome do aluno/a: ");
        name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Nome inválido. Tente de novo.\n");
            getStudent();
            return;
        }

        schoolEvaluation();
    }

    public static void validateGrade() {
        while (true) {
            try {
                System.out.println("Digite a nota (0-100) do aluno/a: ");
                grade = scanner.nextDouble();
                scanner.nextLine();

                if (grade < 0 || grade > 100) {
                    System.out.println("Quantia inválida. Tente de novo.\n");
                } else break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Use números.\n");
                scanner.nextLine();
            }
        }
    }

    public static void schoolEvaluation() {
        validateGrade();

        char letterGrade;
        String situation;
        String feedback;

        if (grade <= 59) {
            letterGrade = 'F';
            situation = "Reprovado";
            feedback = "F - Reprovado. É essencial rever toda a matéria com dedicação.\n" +
                    "Horas recomendadas de estudo: 5 horas/dia + plano de recuperação.";
        } else if (grade <= 69) {
            letterGrade = 'D';
            situation = "Suficiente";
            feedback = "D - Conseguiste passar, mas com dificuldades. Reforça os teus estudos.\n" +
                    "Horas recomendadas de estudo: 4 horas/dia com apoio extra";
        } else if (grade <= 79) {
            letterGrade = 'C';
            situation = "Bom";
            feedback = "C - Bom trabalho, mas há espaço para melhorias. Revê os conteúdos menos dominados.\n" +
                    "Horas recomendadas de estudo: 3 horas/dia.";
        } else if (grade <= 89) {
            letterGrade = 'B';
            situation = "Muito bom";
            feedback = "B - Muito bom desempenho! Continua a esforçar-te.\n" +
                    "Horas recomendadas de estudo: 2 horas/dia.";
        } else {
            letterGrade = 'A';
            situation = "Excelente";
            feedback = "A - Excelente desempenho! Estás no caminho certo. Continua com o bom trabalho.\n" +
                    "Horas recomendadas de estudo: 1 hora/dia";
        }

        System.out.println("\n ## Resultado ## ");
        System.out.printf("Aluno: %s\nNota numérica: %.2f\nConceito: %c\nSituação: %s\nFeedback: %s\n",
                name, grade, letterGrade, situation, feedback);

        assessMoreStudents();
    }

    public static void assessMoreStudents() {
        System.out.println("\nDeseja calcular outra nota? (S/N)");
        char answer = scanner.nextLine().toUpperCase().charAt(0);

        if (answer == 'S') getStudent();
        else if (answer == 'N') return; //desnecessário, mas irei deixar estar por clareza
        else {
            System.out.println("Opção inválida. Responda S ou N.");
            assessMoreStudents();
        }
    }
}
