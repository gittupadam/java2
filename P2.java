public class P2{
    public static void main(String args[]) throws Exception{
        Shared s = new Shared();

        Test t1 = new Test(s);
        Test t2 = new Test(s);

        t1.start();
        t2.start();

    }

}

class Test extends Thread{
    Shared PD;

    Test(Shared s){
        PD = s;
    }

    public void run(){
        synchronized(PD){
            System.out.println(this.getName() + " RUNNING");
            for(int i=0; i<10; i++){
                PD.increment();
                System.out.println("FROM " + this.getName() + " C = " + PD.c);
            }
        }
    }
} 
class Shared{
    int c;

    Shared(){
        c = 0;
    }

    public void increment(){
       c++;
    }
}