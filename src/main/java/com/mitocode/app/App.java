package com.mitocode.app;

import com.mitocode.model.Account;
import com.mitocode.model.Person;
import com.mitocode.model.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {

    private void m1(){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int sum = numbers
                .parallelStream()
                .peek(System.out::println)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("La suma es " + sum);
    }

    private void m2(){
        List<String> words = Arrays.asList("Java", "C#", "Kotlin", "Go", "Perl", "Cobol");
        List<String> result =  words.parallelStream()
                .peek(System.out::println)
                .filter(word -> word.length() > 3)
                .map(String::toUpperCase)
                .toList();

        System.out.println(result);
    }

    private void m3(){
        List<Integer> numbers = Arrays.asList(10,15,3,7,29,18);
        int max = numbers.parallelStream()
                .peek(System.out::println)
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
        System.out.println("El valor máximo es: " + max);
    }

    private void m4(){
        List<Person> people = Arrays.asList(
                new Person("Duke", 4),
                new Person("Mito", 33),
                new Person("Code", 25)
        );

        List<Person> sortedPeople = people.parallelStream()
                .sorted(Comparator.comparing(Person::getAge).reversed())
                .toList();

        System.out.println(sortedPeople);
    }

    public void m5(){
        List<Integer> numbers = new ArrayList<>();
        IntStream.range(0, 10_000_000).forEach(numbers::add);

        long startTimeStream = System.currentTimeMillis();
        double sumStream = numbers.stream()
                .mapToDouble(Math::sqrt)
                .sum();
        long endTimeStream = System.currentTimeMillis();

        System.out.println("Suma usando stream(): " + sumStream);
        System.out.println("Tiempo con stream(): " + (endTimeStream - startTimeStream) + " ms");

        System.out.println("=====================================================");

        long startTimeParallelStream = System.currentTimeMillis();
        double sumParallelStream = numbers.parallelStream()
                .mapToDouble(Math::sqrt)
                .sum();
        long endTimeParallelStream = System.currentTimeMillis();

        System.out.println("Suma usando parallelStream(): " + sumParallelStream);
        System.out.println("Tiempo con parallelStream(): " + (endTimeParallelStream - startTimeParallelStream) + " ms");
    }

    public void m6(){
        List<Transaction> transactions = Arrays.asList(
                new Transaction("A001", 500.0, true),
                new Transaction("A002", -150.0, false),
                new Transaction("A001", -100.0, true),
                new Transaction("A003", 300.0, true),
                new Transaction("A002", 200.0, true)
        );

        Map<String, Account> accounts = new HashMap<>();
        accounts.put("A001", new Account("A001", 1000.0));
        accounts.put("A002", new Account("A002", 1500.0));
        accounts.put("A003", new Account("A003", 2000.0));

        transactions.parallelStream()
                .peek(System.out::println)
                .filter(Transaction::isSuccessful)
                .collect(Collectors.groupingBy(
                        Transaction::getAccountId,
                        Collectors.summingDouble(Transaction::getAmount)
                ))
                .forEach((accountId, totalAmount) -> {
                    Account account = accounts.get(accountId);
                    if(account != null) {
                        account.updateBalance(totalAmount);
                    }
                });

        accounts.values().forEach(System.out::println);
    }

    public static void main(String[] args) {
        App app = new App();
        app.m6();
    }
}
