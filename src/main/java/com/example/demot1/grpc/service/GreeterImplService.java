package com.example.demot1.grpc.service;

import com.example.demot1.grpc.dto.modle.HelloReply;
import com.example.demot1.grpc.dto.modle.HelloRequest;
import com.example.demot1.grpc.dto.rpc.HelloWorldGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yichu.zhang
 * @Date: 2019-09-29 14:26
 */
@Slf4j
@Component
public class GreeterImplService extends HelloWorldGrpc.HelloWorldImplBase {
    /**
     * 一问一答
     * @param request
     * @param responseObserver
     */
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        //逻辑处理
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        //使用响应观察者的onNext()方法返回结果
        responseObserver.onNext(reply);
        //已经完成了对RPC的处理。
        responseObserver.onCompleted();
    }

    /**
     * 一次请求/流式响应
     * @param request
     * @param responseObserver
     */
    @Override
    public void listen(HelloRequest request,
                       StreamObserver<HelloReply> responseObserver){
        //逻辑处理
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        reply = HelloReply.newBuilder().setMessage("welcome !").build();
        responseObserver.onNext(reply);
        reply = HelloReply.newBuilder().setMessage("out !").build();
        responseObserver.onNext(reply);
        //已经完成了对RPC的处理。
        responseObserver.onCompleted();
    }

    /**
     * 流失请求/一次响应
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<HelloRequest> speak(
            StreamObserver<HelloReply> responseObserver) {

        StringBuilder sb = new StringBuilder();
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest helloRequest) {
                log.info("speak receive = {}", helloRequest.getName());
                sb.append(helloRequest.getName()).append(", ");
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("speak error, ex = {}", throwable);
            }

            @Override
            public void onCompleted() {

                responseObserver.onNext(HelloReply.newBuilder().setMessage("hello, "+ sb.toString() +" welcom !").build());
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 流式请求/流失响应
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<HelloRequest> chat(
            StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest helloRequest) {
                responseObserver.onNext(HelloReply.newBuilder().setMessage(helloRequest.getName() +", fuck you! out!").build());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("speak chat, ex = {}", throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }


}
