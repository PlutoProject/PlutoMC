package ltd.kumo.plutomc.framework.bukkit.holograms.utils;

import lombok.NonNull;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.collection.PList;
import org.jetbrains.annotations.Contract;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PExecutor {

    private static boolean initialized = false;
    private static ExecutorService service;
    private static int threadId;

    /**
     * Initialize DExecutor. This method will set up ExecutorService for DecentHolograms.
     *
     * @param threads Amount of threads to use.
     */
    public static void init(int threads) {
        if (!initialized) {
            threadId = 0;
            service = Executors.newFixedThreadPool(threads, (runnable) -> {
                Thread thread = new Thread(runnable);
                thread.setName("Holograms Thread #" + ++threadId);
                thread.setPriority(Thread.NORM_PRIORITY);
                thread.setDaemon(true);
                thread.setUncaughtExceptionHandler((t, ex) -> {
                    Common.log("Exception encountered in " + t.getName());
                    ex.printStackTrace();
                });
                return thread;
            });
            initialized = true;
        }
    }

    /**
     * Complete all tasks and shutdown the service.
     */
    public static void shutdown() {
        service.shutdown();
        initialized = false;
    }

    /**
     * Execute given runnables using the ExecutorService.
     *
     * @param runnables the runnables.
     */
    public static void schedule(Runnable... runnables) {
        if (!initialized) {
            throw new IllegalStateException("DExecutor is not initialized!");
        }
        if (runnables == null || runnables.length == 0) {
            return;
        }
        create(runnables.length).queue(runnables).complete();
    }

    /**
     * Create new instance of DExecutor that handles scheduled runnables.
     *
     * @param estimate The estimated amount of runnables.
     * @return The new instance.
     * @throws IllegalStateException If the service is not initialized.
     */
    @NonNull
    @Contract("_ -> new")
    public static PExecutor create(int estimate) {
        if (!initialized) {
            throw new IllegalStateException("DExecutor is not initialized!");
        }
        return new PExecutor(service, estimate);
    }

    /**
     * Execute a runnable using the ExecutorService.
     *
     * @param runnable The runnable.
     */
    public static void execute(@NonNull Runnable runnable) {
        service.execute(runnable);
    }

    private final @NonNull ExecutorService executor;
    private final @NonNull PList<CompletableFuture<Void>> running;

    PExecutor(@NonNull ExecutorService executor, int estimate) {
        this.executor = executor;
        this.running = new PList<>(estimate);
    }

    /**
     * Schedule a new runnable.
     *
     * @param r The runnable.
     * @return CompletableFuture executing the runnable.
     */
    public CompletableFuture<Void> queue(@NonNull Runnable r) {
        synchronized (running) {
            CompletableFuture<Void> c = CompletableFuture.runAsync(r, executor);
            running.add(c);
            return c;
        }
    }

    /**
     * Schedule more runnables.
     *
     * @param runnables The runnables.
     * @return This instance. (For chaining)
     */
    public PExecutor queue(Runnable... runnables) {
        if (runnables == null || runnables.length == 0) {
            return this;
        }

        synchronized (running) {
            for (Runnable runnable : runnables) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, executor);
                running.add(future);
            }
        }
        return this;
    }

    /**
     * Complete all scheduled runnables.
     */
    public void complete() {
        synchronized (running) {
            try {
                CompletableFuture.allOf(running.toArray(new CompletableFuture[0])).get();
                running.clear();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
