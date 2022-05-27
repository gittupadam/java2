public class P6 {
    public static void main(String args[]) throws Exception{
        SharedData1 s1 = new SharedData1();
        SharedData2 s2 = new SharedData2();

        First f = new First(s1, s2);
        Second s = new Second(s1, s2);

        Thread t1 = new Thread(f);
        Thread t2 = new Thread(s);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

class First implements Runnable{
    SharedData1 d1;
    SharedData2 d2;

    First(SharedData1 x, SharedData2 y){
        d1 = x;
        d2 = y;
    }

    public void run(){
        synchronized(d1){
            try{
                    if(d1.flag == true){
                        d1.wait();
                    }
                    
                    d1.data = (int)(Math.random() * 10 + 2);
                    d1.flag = true;
                    d1.notifyAll();
                    System.out.println("Thread-1: Data Sent to Thread-2 = " + d1.data);
                
            }catch(Exception e){
                System.out.print(e);
            }
        }

        synchronized(d2){
            try{
                if(d2.flag == false){
                    System.out.println("Thread-1: Waiting for Thread-2 to share data");
                    d2.wait();    
                }
                
                System.out.println("Thread-1: Data Recevied From Thread-2 = " + d2.data);
                d2.flag = false;
                d2.notifyAll();
            
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

class Second implements Runnable{
    SharedData1 d1;
    SharedData2 d2;

    Second(SharedData1 x, SharedData2 y){
        d1 = x;
        d2 = y;
    }

    public void run(){
        synchronized(d1){
            try{
                if(d1.flag == false){
                    System.out.println("Thread-2: Waiting for Thread-1 to share data");;
                    d1.wait();
                }
                
                System.out.println("Thread-2: Data Recevied From Thread-1 = " + d1.data);
                d1.flag = false;
                d1.notifyAll();
                
            }catch(Exception e){
                System.out.println(e);
            }
        }
        
        synchronized(d2){
            try{
                if(d2.flag == true){
                    d2.wait();
                }
               
                d2.data = (int)(Math.random() * 10 + 2);
                d2.flag = true;
                d2.notifyAll();
                System.out.println("Thread-2: Data Sent to Thread-1 = " + d2.data);
            
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

class SharedData1{
    int data;
    boolean flag;

    SharedData1(){
        data = 0;
        flag = false;
    }
}

class SharedData2{
    int data;
    boolean flag;

    SharedData2(){
        data = 0;
        flag = false;
    }
}