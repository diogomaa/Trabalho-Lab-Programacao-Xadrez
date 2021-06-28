
package game.controller;


public class Timer implements java.lang.Runnable{

    @Override
    public void run() {
        this.runTimer();
    }

    public void runTimer(){
        int i = 60;
         while (i>0){
          System.out.println("Remaining: "+i+" seconds");
          try {
            i--;
            Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
           }
           catch (InterruptedException e) {
              
           }
         }
    }

}
