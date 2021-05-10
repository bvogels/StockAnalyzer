package stockanalyzer.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader {
    @Override
    public int process(List<String> ticker) {
        ExecutorService exService = Executors.newCachedThreadPool();
        List<Future<String>> fList = new ArrayList<>();
        for (String s : ticker) {
            Future<String> f;
            f = exService.submit(() -> {
                return saveJson2File(s);
            });
            fList.add(f);
            }
        exService.shutdown();
        return 0;
    }
}