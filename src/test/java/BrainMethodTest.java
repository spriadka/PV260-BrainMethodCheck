/**
 * Created by spriadka on 5/30/17.
 */
public class BrainMethodTest {

    public void testLinesOfCodeExceeding(){
        int a = 10;
        a += 20;
        a += 10;
        a += 20;
        a += 20;
        a += 20;
        a += 20;
        a += 20;
        System.out.println(a);
        System.out.println(a);
        System.out.println(a);












        a += 20;
    }

    public void testCycloExceeding(){
        if (true){
            if (true){
                if (true){
                    System.out.println("I am exceeding");
                    if (true){
                        System.out.println("This also exceeding");
                    }
                }
            }
        }
    }
    public void testControlDepthExceeding() {
        for (int i = 0; i < 4; ++i) {
            while (true) {
                while (true) {
                    while (true) {
                        System.out.println("Hello");
                    }
                }
            }
        }
        while (true){

        }
    }

    public void testMaxVariables(){
        int a = 0;
        int b = 0;
        int c = 0;
    }

    public void brainMethod(){
        int a = 0;
        System.out.println(a);
    }

}
