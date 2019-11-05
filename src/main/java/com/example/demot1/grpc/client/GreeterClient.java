package com.example.demot1.grpc.client;

import com.example.demot1.grpc.dto.modle.HelloReply;
import com.example.demot1.grpc.dto.modle.HelloRequest;
import com.example.demot1.grpc.dto.rpc.HelloWorldGrpc;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yichu.zhang
 *
 * @Date: 2019-09-29 15:00
 * GRPC服务
 */
@Slf4j
public class GreeterClient {
    private ManagedChannel channel;
    //阻塞式stub
    private HelloWorldGrpc.HelloWorldBlockingStub blockingStub;
    private HelloWorldGrpc.HelloWorldFutureStub futureStub;
    private HelloWorldGrpc.HelloWorldStub greeterStub;

    public GreeterClient(String host, int port){
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public GreeterClient(ManagedChannelBuilder<?> channelBuilder){
        this.channel = channelBuilder.build();
        this.blockingStub = HelloWorldGrpc.newBlockingStub(channel);
        this.futureStub = HelloWorldGrpc.newFutureStub(channel);
        this.greeterStub = HelloWorldGrpc.newStub(channel);
    }

    private void greetByBlocking(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;

        response = this.blockingStub.sayHello(request);
        log.info("Greet by blocking: {}", response.getMessage());
        System.out.println(response.getMessage());
    }

    private void listenByBlocking(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        Iterator<HelloReply> response;

        response = this.blockingStub.listen(request);
        while (response.hasNext()) {
            log.info("{}, Greet by blocking: {}", name, response.next().getMessage());
        }
    }

    private void greetByFuture(String name) throws ExecutionException, InterruptedException {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        ListenableFuture<HelloReply> response;

        response = this.futureStub.sayHello(request);
        log.info("Greet by future: {}", response.get().getMessage());
    }

    private void greet(String name) throws InterruptedException {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        StreamObserver<HelloReply> requestObserver = new StreamObserver<HelloReply>(){

            @Override
            public void onNext(HelloReply helloReply) {
                log.info("{}, greet on next message = {}", name, helloReply.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("{}, greet on error message = {}", name, throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("{}, greet on completed finished", name);
            }
        };

        this.greeterStub.sayHello(request, requestObserver);

//        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * 一次请求/流式响应
     * @param name
     * @throws InterruptedException
     */
    private void listen(String name) throws InterruptedException {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        StreamObserver<HelloReply> requestObserver = new StreamObserver<HelloReply>(){

            @Override
            public void onNext(HelloReply helloReply) {
                log.info("{}, list on next message = {}", name, helloReply.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("{}, list on error message = {}", name, throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("{}, list on completed finished", name);
            }
        };

        this.greeterStub.listen(request, requestObserver);
    }

    /**
     * 流失请求/一次响应
     */
    private void speak() throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);

        //请求
        StreamObserver<HelloRequest> requestObserver = this.greeterStub.speak(new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply helloReply) {
                log.info("speak on next message = {}", helloReply.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("speak on error message = {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("speak on completed finished");
                finishLatch.countDown();
            }
        });

        for (int i = 0; i < 10; i++) {
            requestObserver.onNext(HelloRequest.newBuilder().setName("zhangyicou-"+i).build());
        }

        requestObserver.onCompleted();

        finishLatch.await(1, TimeUnit.MINUTES);
    }

    /**
     * 流式请求/流失响应
     * @return
     */
    public void chat() throws InterruptedException {

        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<HelloRequest> requestObserver = this.greeterStub.chat(new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply helloReply) {
                log.info("chat on next message = {}", helloReply.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("chat on error message = {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("chat on completed finished");
                finishLatch.countDown();
            }
        });

        for (int i = 0; i < 10; i++) {
            requestObserver.onNext(HelloRequest.newBuilder().setName("zhangyicou-"+i).build());
        }

        requestObserver.onCompleted();

        finishLatch.await(1, TimeUnit.MINUTES);
    }

    public static void main(String[] args) throws Exception{
        GreeterClient client = new GreeterClient("localhost", 8081);
        //sayHello
       for(int i = 0; i < 10; i++) {
           client.greetByBlocking("zhangyicou"+i);
       }

        TimeUnit.SECONDS.sleep(2);
        client.shutdown();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
