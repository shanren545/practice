package shanren.interview;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class IpAnalyzer {
    public static final int PARTITTION_COUNT = 100;
    public static final String TMP_DIR = System.getProperty("java.io.tmpdir") + "/kohjp8wq6mwcguhlyq9p";

    public static void main(String[] args) throws IOException {
        String logFile = generateTestDataIfNecessary();
        IpAnalyzer ipAnalyzer = new IpAnalyzer();
        IpInfo ipInfo = ipAnalyzer.getMostAccessIp(logFile);
        System.out.println("访问最多的来源ip为：");
        System.out.println(ipInfo);
    }


    public IpInfo getMostAccessIp(String logFile) throws IOException {
        // TODO 这里ip的表示可以优化成int型表示形式
        Map<String, Integer> ipCount = new HashMap(PARTITTION_COUNT * 4 / 3);
        IpInfo maxIp = new IpInfo();

        // 将文件按ip进行hash分区，则相同的ip会落在相同的分区
        Path[] partittions = partittionLogFileByHash(logFile);

        // TODO 可考虑并行处理， 计算每个文件中访问次数最多的ip， 然后从这些ip中选取最多访问的ip即为结果
        for (Path partittion : partittions) {
            IpInfo ipInfo = getMostAccessIpByPartittion(partittion);
            Integer count = ipCount.getOrDefault(ipInfo.getIp(), 0);
            count += ipInfo.getAccessCount();
            ipCount.put(ipInfo.getIp(), count);

            if (maxIp.getAccessCount() < count) {
                maxIp = new IpInfo(ipInfo.getIp(), count);
            }
        }

        return maxIp;
    }


    // TODO 可考虑并行处理
    private IpInfo getMostAccessIpByPartittion(Path partittion) throws IOException {
        IpInfo maxIpInfo = new IpInfo();
        Map<String, Integer> ipCount = new HashMap<>();
        Files.lines(partittion).forEach(ip -> {
            int count = ipCount.getOrDefault(ip, 0);
            ipCount.put(ip, ++count);

            if (maxIpInfo.getAccessCount() < count) {
                maxIpInfo.setIp(ip);
                maxIpInfo.setAccessCount(count);
            }
        });
        return maxIpInfo;
    }

    /**
     * TODO 优化点：分区可以考虑多线程、文件异步读写 将超大文件进行分区，保证不返回空
     */
    private Path[] partittionLogFileByHash(String logFile) throws IOException {
        Path logPath = FileSystems.getDefault().getPath(logFile);
        Path dir = getAndCleanTmpDir();

        Path[] partittionFiles = new Path[PARTITTION_COUNT];
        BufferedWriter[] writer = new BufferedWriter[PARTITTION_COUNT];
        try {
            for (int i = 0; i < PARTITTION_COUNT; i++) {
                Path file = FileSystems.getDefault().getPath(TMP_DIR + "/" + i);
                partittionFiles[i] = Files.createFile(file);
                writer[i] = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
            }

            Files.lines(logPath).map(line -> {
                // 格式：访问时间,来源IP,响应结果,响应耗时
                return line.split(",")[1].trim();
            }).forEach(ip -> {
                // 将相同的ip写入到同一分区
                int fileIndex = ip.hashCode() % PARTITTION_COUNT;
                fileIndex = fileIndex < 0 ? -fileIndex : fileIndex;
                try {
                    writer[fileIndex].write(ip);
                    writer[fileIndex].newLine();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            });
        } finally {
            closeQuietly(writer);
        }


        return partittionFiles;
    }

    private Path getAndCleanTmpDir() throws IOException {
        Path dir = FileSystems.getDefault().getPath(TMP_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
            return dir;
        }


        Files.walk(dir).forEach(file -> {
            if (!Files.isDirectory(file)) {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        return dir;
    }

    private void closeQuietly(BufferedWriter[] writers) {
        if (null != writers) {
            for (BufferedWriter w : writers) {
                if (null != w) {
                    try {
                        w.close();
                    } catch (IOException expected) {}
                }
            }
        }
    }

    public static class IpInfo {
        private String ip;
        private int accessCount;

        public IpInfo() {
            this.ip = "";
            this.accessCount = 0;
        }

        public IpInfo(String ip, int accessCount) {
            this.ip = ip;
            this.accessCount = accessCount;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getAccessCount() {
            return accessCount;
        }

        public void setAccessCount(int accessCount) {
            this.accessCount = accessCount;
        }

        @Override
        public String toString() {
            return "[ip=" + ip + ", accessCount=" + accessCount + "]";
        }


    }

    public static String generateTestDataIfNecessary() throws IOException {
        Path file = FileSystems.getDefault().getPath("./webserver.log");
        if (Files.exists(file)) {
            return file.toAbsolutePath().toString();
        }
        BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
        for (int i = 0; i < 1000000; i++) {
            int ipPart = ThreadLocalRandom.current().nextInt(254);
            // 格式：访问时间,来源IP,响应结果,响应耗时
            writer.write("2020-03-26 11:23:11,");
            writer.write(ipPart + "." + ipPart + "." + ipPart + "." + ipPart + ",");
            writer.write("200,");
            writer.write("50");
            writer.newLine();
        }
        writer.close();
        return file.toAbsolutePath().toString();
    }
}
