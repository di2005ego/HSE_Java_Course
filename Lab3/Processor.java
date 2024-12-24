package Lab3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Processor {
    public boolean checkCorrect(int day, int month) {
        if (day == 31 & (month == 4 | month == 6 | month == 9 | month == 11)) {
            return false;
        }
        else if (day > 29 & month == 2) {
            return false;
        }
        return true;
    }

    public boolean checkLeap(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        return false;
    }

    public void processing(String name, String birthDate) throws Exception {
        String[] nameParts = name.split(" ");
        if (nameParts.length != 3) {
            throw new Exception("Некорректный ввод ФИО.");
        }
        if (!(birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}"))) {
            throw new Exception("Некорректный ввод даты рождения.");
        }
        String[] dateParts = birthDate.split("\\.");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        LocalDate currDate = LocalDate.now();
        if ( (day < 1 | day > 31) |
            (month < 1 | month > 12) | 
            (year < 1000 | year > currDate.getYear()) |
            (year == currDate.getYear() & month > currDate.getMonthValue()) |
            (year == currDate.getYear() & month == currDate.getMonthValue() & day > currDate.getDayOfMonth()) |
            !checkCorrect(day, month) |
            (day == 29 & month == 2 & !checkLeap(year)) ) {
            throw new Exception("Некорректный ввод даты рождения.");
        }
        String nameInits = nameParts[0] + " " + nameParts[1].charAt(0) + "." + nameParts[2].charAt(0) + ".";
        String gender = "";
        if (nameParts[2].charAt(nameParts[2].length() - 1) == 'ч') {
            gender = "Мужской";
        }
        else {
            gender = "Женский";
        }
        LocalDate birthDateLD = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int age = currDate.getYear() - birthDateLD.getYear();
        if ( birthDateLD.getMonthValue() > currDate.getMonthValue() |
             (birthDateLD.getMonthValue() == currDate.getMonthValue() &
              birthDateLD.getDayOfMonth() > currDate.getDayOfMonth()) ) {
            age--;
        }
        String strAge = Integer.toString(age);
        String years = " лет";
        Character lastNum = strAge.charAt(strAge.length() - 1);
        Character prevNum = '0';
        if (strAge.length() > 1) {
            prevNum = strAge.charAt(strAge.length() - 2);
        }
        if (lastNum == '1' & prevNum != '1') {
            years = " год";
        }
        else if ((lastNum == '2' | lastNum == '3' | lastNum == '4') & prevNum != '1') {
            years = " года";
        }
        System.out.println("ФИО: " + nameInits);
        System.out.println("Пол: " + gender);
        System.out.println("Возраст: " + age + years);
    }
}
