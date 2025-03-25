package ap.exercises.ex1.Chapter5;
/*Question 5.18 :
 Write a program that reads in three strings and sorts them lexicographically.
Enter three strings:Charlie Able Baker
Able
Baker
Charlie
*/
import java.util.Scanner;

public class Main_EX1_E5_18 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter three strings : ");

        String str1 = scanner.next();
        String str2 = scanner.next();
        String str3 = scanner.next();

        String temp;
        if (str1.compareTo(str2) > 0 ) {
            temp = str1;
            str1 = str2;
            str2 = temp;
        }

        if (str2.compareTo(str3) > 0 ) {
            temp = str2;
            str2 = str3;
            str3 = temp;
        }
        if (str1.compareTo(str3) > 0 ) {
            temp = str1;
            str1 = str3;
            str3 = temp;
        }

        System.out.println("After sorting the strings : ");
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
    }
}
