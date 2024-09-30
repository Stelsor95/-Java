package Task01;
//Продвинутая работа с исключениями в Java

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class UserInputApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в произвольном порядке, разделенные пробелом: ");
        System.out.println("Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();

        String[] data = input.split(" ");

        if (data.length != 6) {
            System.err.println("Ошибка: количество данных не совпадает с требуемым");
            return;
        }

        String surname, name, patronymic, birthDate, phoneNumber, gender;

        try {
            surname = data[0];
            name = data[1];
            patronymic = data[2];
            birthDate = data[3];
            phoneNumber = data[4];
            gender = data[5];

            if (!isValidBirthDate(birthDate)) {
                throw new InvalidBirthDateException("Неверный формат даты рождения");
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                throw new InvalidPhoneNumberException("Неверный формат номера телефона");
            }

            if (!isValidGender(gender)) {
                throw new InvalidGenderException("Неверный пол");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Ошибка: не хватает данных");
            return;
        } catch (InvalidBirthDateException | InvalidPhoneNumberException | InvalidGenderException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(surname + ".txt", true))) {
            writer.println(
                    surname + " " + name + " " + patronymic + " " + birthDate + " " + phoneNumber + " " + gender);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean isValidBirthDate(String birthDate) {
        String[] parts = birthDate.split("\\.");
        if (parts.length != 3) {
            return false;
        }
        int day, month, year;
        try {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1900 && year <= 2100;
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        try {
            int number = Integer.parseInt(phoneNumber);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidGender(String gender) {
        return gender.equals("f") || gender.equals("m");
    }
}

class InvalidBirthDateException extends Exception {
    public InvalidBirthDateException(String message) {
        super(message);
    }
}

class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}

class InvalidGenderException extends Exception {
    public InvalidGenderException(String message) {
        super(message);
    }
}
