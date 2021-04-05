package customer.onpremise_service.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BPMSocketFactory extends SocketFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(BPMSocketFactory.class);
	
	private String myhostName = "bpmdb";
	private int myport = 1433;
	
	public BPMSocketFactory() {
        logger.info("Init BPMSocketFactory");
    }
	
	@Override
    public Socket createSocket() throws IOException {
		logger.info("BPMSocketFactory createSocket 1");
		InetSocketAddress vAddr = InetSocketAddress.createUnresolved(myhostName, myport);
		ConnectivitySocks5ProxySocket proxySocket = new ConnectivitySocks5ProxySocket();
		proxySocket.connect(vAddr, 3600);
		return proxySocket;
    }

	@Override
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		logger.info("BPMSocketFactory createSocket 2");
        InetSocketAddress vAddr = InetSocketAddress.createUnresolved(host, port);
        //InetSocketAddress vAddr = InetSocketAddress.createUnresolved(myhostName, myport);
        ConnectivitySocks5ProxySocket proxySocket = new ConnectivitySocks5ProxySocket();
        proxySocket.connect(vAddr,3600);
       return proxySocket;
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		logger.info("BPMSocketFactory createSocket 3");
		InetSocketAddress vAddr = InetSocketAddress.createUnresolved(host.getHostName(), port);
		//InetSocketAddress vAddr = InetSocketAddress.createUnresolved(myhostName, myport);
		ConnectivitySocks5ProxySocket proxySocket = new ConnectivitySocks5ProxySocket();
		proxySocket.connect(vAddr, 3600);
		return proxySocket;
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
			throws IOException, UnknownHostException {
		logger.info("BPMSocketFactory createSocket 4");
        //return createSocket(host,port);
        return new Socket(host, port, localHost, localPort);
	}

	@Override
	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
		logger.info("DummySocketFactory createSocket 5");
        //return createSocket(address,port);
        return new Socket(address, port, localAddress, localPort);
	}

}
