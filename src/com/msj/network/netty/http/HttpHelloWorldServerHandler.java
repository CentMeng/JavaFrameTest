package com.msj.network.netty.http;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
 
import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
  
  
  public class HttpHelloWorldServerHandler extends ChannelHandlerAdapter {
      private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };
  
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) {
          ctx.flush();
      }
  
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) {
          if (msg instanceof HttpRequest) {
              HttpRequest req = (HttpRequest) msg;
  
              if (HttpHeaderUtil.is100ContinueExpected(req)) {
                  ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
              }
              boolean keepAlive = HttpHeaderUtil.isKeepAlive(req);
              FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
              response.headers().set(CONTENT_TYPE, "text/plain");
              response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
  
              if (!keepAlive) {
                  ctx.write(response).addListener(ChannelFutureListener.CLOSE);
              } else {
                  response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                  ctx.write(response);
              }
          }
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
          cause.printStackTrace();
          ctx.close();
      }
  }