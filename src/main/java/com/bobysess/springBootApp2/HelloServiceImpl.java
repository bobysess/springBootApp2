package com.bobysess.springBootApp2;


import org.springframework.stereotype.Service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

//@Service
@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    
    @Override
    public void sayHello (HelloRequest req, StreamObserver<HelloResponse> respObserver) {
        var resp = HelloResponse.newBuilder().setMessage("Hello " + req.getName()).build();
        respObserver.onNext(resp);
        respObserver.onCompleted();
    }
}
