import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator implements Runnable {
    private int num;
    private int currFloor;
    private Queue<Integer> requests = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition arrival = lock.newCondition();
    private boolean running = true;
    
    public Elevator(int num) {
        this.num = num;
        currFloor = 1;
    }

    public void acceptReq(int request) {
        lock.lock();
        try {
            requests.offer(request);
            arrival.signal();
        }
        finally { 
            lock.unlock();
        }
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            lock.lock();
            while (requests.isEmpty()) {
                try {
                    arrival.await();
                }
                catch (InterruptedException e) {
                    Frame.appendLog("Ошибка: " + e);
                }
            }
            int nextFloor = requests.poll();
            Frame.appendLog("Лифт " + num + " отправился в путь на этаж " + nextFloor);

            boolean goingUp = nextFloor > currFloor;
            if (goingUp) {
                for (int i = currFloor + 1; i < nextFloor; i++) {
                    currFloor = i;
                    Frame.appendLog("Лифт " + num + " проезжает " + currFloor + " этаж");
                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    if (checkForRequests()) {
                        Frame.changeToOpen(num, currFloor);
                        Frame.appendLog("Лифт " + num + " открыл двери");
                        try {
                            Thread.sleep(5000);
                        } 
                        catch (InterruptedException e) {
                            Frame.appendLog("Ошибка: " + e);
                        }
                        Frame.changeToClosed(num, currFloor);
                        Frame.appendLog("Лифт " + num + " закрыл двери");
                    }
                }
            }
            else {
                for (int i = currFloor - 1; i > nextFloor; i--) {
                    currFloor = i;
                    Frame.appendLog("Лифт " + num + " проезжает " + currFloor + " этаж");
                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    if (checkForRequests()) {
                        Frame.changeToOpen(num, currFloor);
                        Frame.appendLog("Лифт " + num + " открыл двери");
                        try {
                            Thread.sleep(5000);
                        } 
                        catch (InterruptedException e) {
                            Frame.appendLog("Ошибка: " + e);
                        }
                        Frame.changeToClosed(num, currFloor);
                        Frame.appendLog("Лифт " + num + " закрыл двери");
                    }
                }
            }

            Frame.appendLog("Лифт " + num + " прибыл на этаж " + nextFloor);
            Frame.changeToOpen(num, nextFloor);
            Frame.appendLog("Лифт " + num + " открыл двери");
            try {
                Thread.sleep(5000);
            } 
            catch (InterruptedException e) {
                Frame.appendLog("Ошибка: " + e);
            }
            Frame.changeToClosed(num, nextFloor);
            Frame.appendLog("Лифт " + num + " закрыл двери");
            lock.unlock();
        }
    }

    private boolean checkForRequests() {
        lock.lock();
        try {
            Queue<Integer> requestsCopy = new LinkedList<>();
            requestsCopy.addAll(requests);
            while (!requestsCopy.isEmpty()) {
                int request = requestsCopy.poll();
                if (request == currFloor) {
                    Frame.appendLog("Лифт " + num + " принял запрос на этаже " + request);
                    requests.remove(request);
                    return true;
                }
            }
        }
        finally {
            lock.unlock();
        }
        return false;
    }
}
