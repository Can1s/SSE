package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        boolean foundOrNot = false;
        boolean exit = true;
        List<String> people = new ArrayList<>();
        String dopPeople = "";
        int coincidences;


        for(int i = 0; i < args.length; i++) {
            if(args[i].equals("--data")) {
                String fileName = args[i+1];
                try {
                    people = Files.readAllLines(Paths.get(fileName));
                    dopPeople = String.valueOf(Files.readAllLines(Paths.get(fileName)));
                } catch (IOException e) {
                    System.out.println("Error");
                }
            }
        }
        String[] peopleStr = dopPeople.replaceAll(", ", " ").split(" ");

        while (exit) {
            printMenu();
            int action = scanner.nextInt();
            System.out.println();
            switch (action) {
                case 1:
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String strategy = scanner2.nextLine();
                    System.out.println("Enter a name or email to search all suitable people.");
                    String[] dataForSearch = scanner2.nextLine().split(" ");
                    switch (strategy) {
                        case "ANY":
                            for(String x : peopleStr) {
                                for (String forSearch : dataForSearch) {
                                    if (x.toLowerCase().matches("\\b" + forSearch.toLowerCase() + "\\b")) {
                                        foundOrNot = true;
                                        break;
                                    }
                                }
                            }
                            if(foundOrNot) {
                                coincidences = 0;
                                for (String person : peopleStr) {
                                    for (String forSearch : dataForSearch) {
                                        if (person.toLowerCase().matches("\\b" +
                                                forSearch.toLowerCase() + "\\b")) {
                                            coincidences++;
                                            break;
                                        }
                                    }
                                }
                                System.out.println(coincidences + " persons found:");
                                for (String person : people) {
                                    for (String forSearch : dataForSearch) {
                                        if (person.toLowerCase().contains(forSearch.toLowerCase())) {
                                            System.out.println(person);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                System.out.println("No matching people found.");
                            }
                            break;
                        case "NONE":
                            boolean bool = false;
                            int cnt;
                            for(String x : people) {
                                cnt = 0;
                                String[] temp = x.split(" ");
                                for(String tmp : temp) {
                                    for(String search : dataForSearch) {
                                        if(!(tmp.toLowerCase().contains(search.toLowerCase()))) {
                                            cnt++;
                                        }
                                    }
                                }
                                if(cnt == temp.length * dataForSearch.length) {
                                    bool = true;
                                    break;
                                }
                            }
                            if(bool) {
                                coincidences = 0;
                                for (String person : people) {
                                    cnt = 0;
                                    String[] temp = person.split(" ");
                                    for(String tmp : temp) {
                                        for(String search : dataForSearch) {
                                            if(!(tmp.toLowerCase().matches("\\b" + search.toLowerCase() + "\\b"))) {
                                                cnt++;
                                            }
                                        }
                                    }
                                    if(cnt == temp.length * dataForSearch.length) {
                                        coincidences++;
                                    }
                                }
                                System.out.println(coincidences + " persons found:");
                                for (String person : people) {
                                    cnt = 0;
                                    String[] temp = person.split(" ");
                                    for(String tmp : temp) {
                                        for(String search : dataForSearch) {
                                            if(!(tmp.toLowerCase().matches("\\b" + search.toLowerCase() + "\\b"))) {
                                                cnt++;
                                            }
                                        }
                                    }
                                    if(cnt == temp.length * dataForSearch.length) {
                                        System.out.println(person);
                                    }
                                }
                            } else {
                                System.out.println("No matching people found.");
                            }
                            break;
                        case "ALL":
                            int ct = 0;
                            boolean booL = false;
                            for(String x : peopleStr) {
                                if(ct < 0) break;
                                else ct = 0;
                                for (String forSearch : dataForSearch) {
                                    if (x.toLowerCase().matches("\\b" +
                                            forSearch.toLowerCase() + "\\b")) {
                                        ct++;
                                    }
                                    if(ct == dataForSearch.length) {
                                        ct = -1;
                                        booL = true;
                                        break;
                                    }
                                }
                            }
                            if(booL) {
                                coincidences = 0;
                                int pok;
                                for (String person : people) {
                                    pok = 0;
                                    for (String forSearch : dataForSearch) {
                                        if (person.toLowerCase().matches("\\b" +
                                                forSearch.toLowerCase() + "\\b")) {
                                            pok++;
                                        }
                                        if(pok == dataForSearch.length) {
                                            coincidences++;
                                        }
                                    }
                                }
                                System.out.println(coincidences + " persons found:");
                                int jen;
                                for (String person : people) {
                                    jen = 0;
                                    for (String forSearch : dataForSearch) {
                                        if (person.toLowerCase().matches("\\b" +
                                                forSearch.toLowerCase() + "\\b")) {
                                            jen++;
                                        }
                                        if(jen == dataForSearch.length) {
                                            System.out.println(person);
                                        }
                                    }
                                }
                            } else {
                                System.out.println("No matching people found.");
                            }
                            break;
                        default:
                            System.out.println("Incorrect strategy! Try again.");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("=== List of people ===");
                    for (String person : people) {
                        System.out.println(person);
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    exit = false;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }
        }
    }
}
