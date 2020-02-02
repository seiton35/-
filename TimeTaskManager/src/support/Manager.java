package support;

import java.util.Scanner;

public class Manager {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input a name of task: ");
        String name = in.nextLine();
        if(name.length()!= 0) {
            System.out.print("Input a steps: ");
            String steps = in.nextLine();
            if(steps.length()!= 0) {

            }else {
                System.out.println("must be at least one step!");
            }
        }else {
            System.out.println("name is too short!");
        }

        in.close();
    }
}
