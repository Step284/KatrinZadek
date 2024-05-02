package org.example;

import org.example.Classes.*;
import java.io.*;
import java.util.*;

public class CQuiz {
    private List<Otazka> otazka; // List otázek
    private Scanner scanner; // Scanner instance pro čtení vstupu od uživatele

    public CQuiz() {
        this.otazka = new ArrayList<>(); // Inicializace instance listu otázek
        this.scanner = new Scanner(System.in); // Inicializace instance Scanneru pro čtení vstupu od uživatele
    }

    // Metoda pro načtení otázek ze souboru
    public void nacistSoubor(String nazevSouboru) throws IOException {
        List<String> lines = new ArrayList<>(); // List pro uložení řádek ze souboru
        try (BufferedReader reader = new BufferedReader(new FileReader(nazevSouboru))) { // Načtení souboru
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line); // Přidání načtené řádky do listu
            }
        }

        List<String> aktualniOtazka = new ArrayList<>(); // List pro aktuální řádky otázky
        for (String line : lines) {
            if (line.equals("/")) { // Pokud narazíme na oddělovač otázek
                zpracovatAktualniOtazku(aktualniOtazka); // Zpracování aktuálních řádků otázky
                aktualniOtazka.clear(); // Vyčištění listu pro další otázku
            } else {
                aktualniOtazka.add(line); // Přidání aktuálního řádku do listu otázky
            }
        }

        // Zpracování poslední otázky, pokud nějaká zůstala
        if (!aktualniOtazka.isEmpty()) {
            zpracovatAktualniOtazku(aktualniOtazka);
        }
    }

    // Metoda pro zpracování řádků otázky a vytvoření odpovídajícího objektu Otazka
    private void zpracovatAktualniOtazku(List<String> radkyOdpovedi) {
        String text = ""; // Text otázky
        List<String> moznosti = new ArrayList<>(); // Možnosti odpovědí
        String spravnaodpoved = ""; // Správná odpověď
        int body = 0; // Počet bodů za otázku

        for (String line : radkyOdpovedi) { // Procházení řádků otázky
            if (line.startsWith("Otázka")) {
                text = line.split(":")[1].trim(); // Načtení textu otázky
            } else if (line.startsWith("Správně:")) {
                spravnaodpoved = line.split(":")[1].trim(); // Načtení správné odpovědi
                moznosti.add(line.split(":")[1].trim()); // Přidání správné odpovědi mezi možnosti odpovědí
            } else if (line.startsWith("Špatně:")) {
                moznosti.add(line.split(":")[1].trim()); // Přidání špatné odpovědi mezi možnosti odpovědí
            } else if (line.startsWith("Hodnota:")) {
                body = Integer.parseInt(line.split(":")[1].trim()); // Načtení počtu bodů za otázku
            }
        }

        // Vytvoření objektu Otazka a přidání do seznamu otázek
        Otazka otazka = new Otazka(text, moznosti, spravnaodpoved, body);
        this.otazka.add(otazka);
    }

    // Metoda pro zobrazení otázky uživateli a získání odpovědi
    private String aktualniOdpovidac(Otazka otazka) {
        System.out.println(otazka.getText()); // Výpis textu otázky
        List<String> options = otazka.getMoznosti(); // Seznam možností odpovědí
        Collections.shuffle(options); // Zamíchání možností odpovědí
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i)); // Výpis možností odpovědí
        }
        System.out.print("Zadej číslo odpovědi: "); // Výzva k zadání odpovědi

        while (true) { // Smyčka pro opakované čtení vstupu od uživatele
            try {
                int moznost = scanner.nextInt(); // Čtení vstupu od uživatele
                if (moznost < 1 || moznost > options.size()) {
                    System.out.println("Zadej prosím číslo odpovídající možnosti ze seznamu");
                } else {
                    String zvolenaOtazka = options.get(moznost - 1); // Získání vybrané možnosti odpovědi
                    if (otazka.jeSpravne(zvolenaOtazka)) {
                        System.out.println("Správně!");
                    } else {
                        System.out.println("Špatně. Správná odpověď byla: " + otazka.getSpravnaOdpoved());
                    }
                    return zvolenaOtazka; // Návrat vybrané možnosti odpovědi
                }
            } catch (InputMismatchException e) {
                System.out.println("Zadej prosím platné číslo ze seznamu");
                scanner.next(); // Zpracování neplatného vstupu
            }
        }
    }

    // Metoda pro spuštění kvízu
    public void startQuiz() {
        int soucetBodu = 0; // Celkový počet bodů
        for (Otazka otazka : otazka) {
            String odpovedUzivatele = aktualniOdpovidac(otazka); // Zobrazení otázky a získání odpovědi od uživatele
            if (otazka.jeSpravne(odpovedUzivatele)) {
                soucetBodu += otazka.getBody(); // Zvýšení celkového počtu bodů o body za správně zodpovězenou otázku
            } else {
                System.out.println("Špatně."); // Výpis zprávy o nesprávné odpovědi
            }
        }
        System.out.println("Kvíz skončil. Celkový počet bodů: " + soucetBodu); // Výpis celkového počtu bodů
    }
}