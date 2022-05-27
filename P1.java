public class P1{
    public static void main(String args[]) throws Exception{
        int m[] = {1, 2, 3, 4, 5};

        Average avg = new Average(5, m);
        Maximum ma = new Maximum(5, m);
        Minimum mi = new Minimum(5, m);

        Thread t1 = new Thread(avg);
        Thread t2 = new Thread(ma);
        Thread t3 = new Thread(mi);
    
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}

class Average implements Runnable{
    int m[];
    int n;

    Average(int n, int m[]){
        this.n = n;
        this.m = new int[n];

        for(int i=0; i<n;i++){
            this.m[i] = m[i];
        }
    }

    public void run(){
        int c = 0;
        for(int i : m){
            c = c + i;
        }

        System.out.println("Average = " + (c / (float)n));
    }
}

class Maximum implements Runnable{
    int m[];
    int n;

    Maximum(int n, int m[]){
        this.n = n;
        this.m = new int[n];

        for(int i=0; i<n;i++){
            this.m[i] = m[i];
        }
    }

    public void run(){
        int c = m[0];
        for(int i : m){
            if(i >= c)
                c = i;
        }

        System.out.println("Maximum = " + c);
    }
}

class Minimum implements Runnable{
    int m[];
    int n;

    Minimum(int n, int m[]){
        this.n = n;
        this.m = new int[n];

        for(int i=0; i<n;i++){
            this.m[i] = m[i];
        }
    }

    public void run(){
        int c = m[0];
        for(int i : m){
            if(i <= c)
                c = i;
        }

        System.out.println("Minimum = " + c);
    }
}