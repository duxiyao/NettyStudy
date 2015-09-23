/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelHandlerAdapter implements Runnable{

    private final ByteBuf firstMessage;
    private ChannelHandlerContext mctx;
    public static SocketChannel mSocketChannel;
    
    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
//        for (int i = 0; i < firstMessage.capacity(); i ++) {
//            firstMessage.writeByte((byte) i);
//        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//    	firstMessage.writeBytes("aa".getBytes());
//        ctx.writeAndFlush(firstMessage);
        mctx=ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
        mctx=ctx;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
    
    public void write(String s){
    	firstMessage.writeBytes(s.getBytes());
//    	if(mSocketChannel!=null)
//    		mSocketChannel.writeAndFlush(firstMessage);
    	mctx.writeAndFlush(firstMessage);
    }

	@Override
	public void run() {
		try {
			System.out.println("1");
			Thread.sleep(2000);
			System.out.println("2");
			write("abc");
			System.out.println("3");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
