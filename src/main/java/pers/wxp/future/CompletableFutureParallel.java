package pers.wxp.future;

import pers.wxp.util.LogPrintUtil;

import java.util.concurrent.CompletableFuture;

/**
 * @descpire 同时从新浪和网易查询证券代码，
 * 只要任意一个返回结果，就进行下一步查询价格，
 * 查询价格也同时从新浪和网易查询，只要任意一个返回结果，就完成操作:
 * @author wxp
 * @date 2020-11-25
 */
public class CompletableFutureParallel {
    public static void main(String[] args) throws Exception {
        LogPrintUtil.print("开始");
        // 两个CompletableFuture执行异步查询:
        //supplyAsync
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石化", "https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);

        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        cfFetch.get();
        LogPrintUtil.print("结束");
        Thread.sleep(10000);
    }

    static String queryCode(String name, String url) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        System.out.println("query code from " + url + "...");
        return "name";
    }

    static Double fetchPrice(String code, String url) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.println("query price from " + url + "...");
        return 5 + Math.random() * 20;
    }
}
