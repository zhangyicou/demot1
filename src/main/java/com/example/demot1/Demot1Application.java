package com.example.demot1;

import com.example.demot1.log.log4j2.LogHandler;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import com.example.demot1.config.ServerConfig;
import com.example.demot1.grpc.service.GreeterImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class Demot1Application implements CommandLineRunner {
	@Autowired
	private GreeterImplService greeterService;
	@Autowired
	private ServerConfig serverConfig;
	private Server grpcServer;
	@Value("${switch.grpc:0}")
	private int grpcSwitch;
	@Value("${switch.log4j2:0}")
	private int log4j2Switch;

	public static void main(String[] args) {

		//全异步日志
		System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
		System.setProperty("AsyncLogger.RingBufferSize", "262144");
		System.setProperty("AsyncLoggerConfig.RingBufferSize", "262144");
		SpringApplication.run(Demot1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(1 == grpcSwitch) {
			grpcServer = ServerBuilder.forPort(serverConfig.getPort() + 1)
                    .addService(greeterService)
                    .build();
			grpcServer.start();
			log.info("Grpc server started, listening on {}", serverConfig.getPort());
			grpcServer.awaitTermination();

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					if (grpcServer != null) {
						grpcServer.shutdown();
					}
				}
			});
		}

		if(1 == log4j2Switch) {
			int thread = 50;
			int msgSum = 10_000_000;
			long startTime = System.currentTimeMillis();
			CountDownLatch latch = new CountDownLatch(thread);
			ThreadPoolExecutor threadPool = new ThreadPoolExecutor(thread, thread, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(thread));
			IntStream.range(0, 50).forEach(index -> threadPool.execute(new LogHandler(index, msgSum / thread, latch)));

			latch.await();
			log.info("thread = {}, msgSum = {}, time = {}", thread, msgSum, System.currentTimeMillis() - startTime);
		}
	}
}
