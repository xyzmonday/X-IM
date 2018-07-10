package com.yff.xim;

import com.yff.xim.server.connector.IConnectorService;
import com.yff.xim.server.message.IMessageProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XImApplicationTests {

    @Autowired
    IConnectorService connectorManager;

    @Autowired
    IMessageProcessor messageProcessor;

    @Test
    public void contextLoads() {
        System.out.println(connectorManager);
        System.out.println(messageProcessor);
    }

}
