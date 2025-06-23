import java.util.InputMismatchException;
import java.util.Scanner;

public class Desafio1 {
    public static Scanner scanner = new Scanner(System.in);
    public static String name = "";
    public static int transactions = 0;
    public static double balance = 0;

    public static void main(String[] args) {
        accountCreation();
        scanner.close();
    }

    public static double validationMoney(String question){
        while (true) {
            System.out.println(question);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value < 0) {
                    System.out.println("Quantia inválida. Tente de novo.\n");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Use números.\n");
                scanner.nextLine();
            }
        }
    }

    public static void accountCreation() {
        System.out.println(" -- Criação de Conta -- \n" +
                "Digite o nome do titular: ");
        //VALIDAÇÃO DO NOME
        name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Nome inválido. Tente de novo.\n");
            accountCreation();
            return;
        }
        //VALIDAÇÃO DO DEPOSITO
        double initialDeposit = validationMoney("Digite o depósito inicial: ");

        String accCreationConfirmation =
                "\n -- Conta criada com sucesso!! -- \n" +
                "Titular: %s\n" +
                "Saldo inicial: %.2f\n" +
                "Estado: Ativo\n" +
                "Transações: %d";

        System.out.printf((accCreationConfirmation) + "%n", name, initialDeposit, ++transactions);
        balance = initialDeposit;
        actions();
    }

    public static void actions() {
        while(true) {
            System.out.println("\n -- Bem vindo ao Banco Java -- \n" +
                    "O que pretende realizar?\n" +
                    " 1 - Depositar\n" +
                    " 2 - Levantar\n" +
                    " 3 - Verificar Saldo\n" +
                    " 4 - Sair" );

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    depositMoney();
                    break;
                case "2":
                    withdrawMoney();
                    break;
                case "3":
                    balanceVerification();
                    break;
                case "4":
                    System.out.println("Obrigado pela sua visita.");
                    return;
                default:
                    System.out.println("Opção inválida. Tente de novo.");
                    break;
            }
        }
    }

    public static void depositMoney() {
        System.out.println("\n -- Depósito -- ");

        double deposit = validationMoney("Indique o valor a depositar: ");

        double newBalance = balance + deposit;
        transactions++;
        String accDepositConfirmation =
                "\n -- Depósito efetuado com sucesso!! -- \n" +
                        "Titular: %s\n" +
                        "Saldo inicial: %.2f\n" +
                        "Saldo atual: %.2f\n" +
                        "Estado: Ativo\n" +
                        "Transações: %d";

        System.out.printf((accDepositConfirmation) + "%n", name, balance, newBalance, transactions);
        balance = newBalance;
    }

    public static void withdrawMoney() {
        System.out.println("\n -- Levantamento -- ");

        double withdraw = validationMoney("Indique o valor a levantar: ");

        if(balance < withdraw) {
            System.out.println("Fundos insuficientes na conta. Tente de novo.\n");
            withdrawMoney();
            return;
        }

        balance -= withdraw;
        transactions++;
        String accWithdrawConfirmation =
                "\n -- Levantamento efetuado com sucesso!! -- \n" +
                        "Titular: %s\n" +
                        "Saldo retirado: %.2f\n" +
                        "Saldo atual: %.2f\n" +
                        "Estado: Ativo\n" +
                        "Transações: %d";

        System.out.printf((accWithdrawConfirmation) + "%n", name, withdraw, balance, transactions);
    }

    public static void balanceVerification() {
        String accBalanceConfirmation =
                "\n -- Verificação de Saldo -- \n" +
                        "Titular: %s\n" +
                        "Saldo atual: %.2f\n" +
                        "Estado: Ativo\n" +
                        "Transações: %d";

        System.out.printf((accBalanceConfirmation) + "%n", name, balance, transactions);
    }
}
