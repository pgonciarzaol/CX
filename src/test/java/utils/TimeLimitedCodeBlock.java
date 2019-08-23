package utils;

import java.util.concurrent.*;

public class TimeLimitedCodeBlock {

     int time;
     TimeUnit timeUnit;



//    public void runWithTimeout(final Runnable runnable, long timeout, TimeUnit timeUnit) throws Exception {
//        runWithTimeout(new Callable<Long>() {
//            @Override
//            public Long call() throws Exception {
//                return runnable.run();
//            }
//        }, timeout, timeUnit);
//    }

    public Long runWithTimeout(Callable<Long> callable, long timeout, TimeUnit timeUnit) throws Exception {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Long> future = executor.submit(callable);
        executor.shutdown(); // This does not cancel the already-scheduled task.
        try {
            return future.get(timeout, timeUnit);
        }
        catch (TimeoutException e) {
            //remove this if you do not want to cancel the job in progress
            //or set the argument to 'false' if you do not want to interrupt the thread
            future.cancel(true);
            System.out.println("\ncancel");
            throw e;
        }
        catch (ExecutionException e) {
            //unwrap the root cause
            Throwable t = e.getCause();
            if (t instanceof Error) {
                throw (Error) t;
            } else if (t instanceof Exception) {
                throw (Exception) t;
            } else {
                throw new IllegalStateException(t);
            }
        }
    }

}