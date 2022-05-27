public class P4 {
    public static void main(String args[]) throws Exception{
        Queue q = new Queue();

        First f = new First(q);
        Second s = new Second(q);

        Thread t1 = new Thread(f);
        Thread t2 = new Thread(s);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

class Second implements Runnable{
    Queue qu;

    Second(Queue x){
        qu = x;
    }

    public void run(){
        synchronized(qu){
            try{
                for(int i=0; i<10; i++){
                    if(qu.front == -1){
                        System.out.println(i + ") Waiting For Producer to produce...");
                        qu.wait();
                    }
                    else{
                        System.out.println(i + ") Reading = " + qu.q[qu.front % 5] + " @ " + qu.front % 5);
                        
                        if(qu.front == qu.rear){
                            qu.front = -1;
                            qu.rear = -1;
                        }else{
                            qu.front++;
                        }
                        qu.notify();

                        Thread.sleep(400);
                    }
                }
            }catch(Exception e){
                System.out.println("Second - " + e);
            }
        }
    }
}

class First implements Runnable{
    Queue qu;

    First(Queue x){
        qu = x;
    }

    public void run(){
        synchronized(qu){
            try{
                for(int i=0; i<10; i++){
                    if(((qu.front == 0) && (qu.rear == 4)) || (qu.front == ((qu.rear + 1) % 5))){
                        System.out.println(i + ") Waiting For Consumer to Consumee...");
                        qu.wait();
                    }
                    else{
                        if(qu.front == -1)
                            qu.front = 0;
                        
                        qu.rear++;
                        qu.q[qu.rear % 5] = (int)(Math.random() * 10 + 2);
                        System.out.println(i + ") Inserted " + qu.q[qu.rear % 5] + " @ " + (qu.rear % 5));
                        qu.notify();

                        Thread.sleep(500);
                    }
                }
            }
            catch(Exception e){
                System.out.println("First - " + e);
            }
        }
    }
}

class Queue{
    int front;
    int rear;
    int q[];

    Queue(){
        front = -1;
        rear = -1;
        q = new int[5];
    }
}
