import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Frame.createFrame();
        Elevator firstEl = new Elevator(1);
        Elevator secondEl = new Elevator(2);
        Thread firstThread = new Thread(firstEl);
        Thread secondThread = new Thread(secondEl);
        firstThread.start();
        secondThread.start();
        Random random = new Random();
        int requestsNum = 100; // Количество запросов, которое будет просимулировано
        int reqId = 1;
        while (reqId <= requestsNum) {
            int request = random.nextInt(4) + 1;
            int whichEl = random.nextInt(2) + 1;
            Frame.appendLog("Вызван лифт " + whichEl + " на этаж " + request);
            if (whichEl == 1) {
                firstEl.acceptReq(request);
            }
            else {
                secondEl.acceptReq(request);
            }
            reqId += 1;
            try {
                Thread.sleep((random.nextInt(5)) * 1000);
            }
            catch(InterruptedException e) {
                Frame.appendLog("Ошибка: " + e);
            }
        }
        firstEl.stop();
        secondEl.stop();
        firstThread.join();
        secondThread.join();
    }
}