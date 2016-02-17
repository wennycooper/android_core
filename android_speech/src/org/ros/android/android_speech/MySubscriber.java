package org.ros.android.android_speech;

import org.apache.commons.logging.Log;
import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;


public class MySubscriber extends AbstractNodeMain {
    private MainActivity    mInstance;
    private int count = 0;

    public MySubscriber(MainActivity act) {
        super();
        mInstance = act;
        //mInstance.myMethod();
    }



    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("MySubscriber/subscriber");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Log log = connectedNode.getLog();
        Subscriber<std_msgs.UInt8> subscriber = connectedNode.newSubscriber("/andbot/speechRecognition/cmd", std_msgs.UInt8._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.UInt8>() {
            @Override
            public void onNewMessage(std_msgs.UInt8 msg) {
                log.info("Received message.getData(): " + msg.getData());
                //if (count > 0) mInstance.myMethod();
                //count ++;
                //byte = msg.getData();
                mInstance.myMethod();
            }
        });
    }
}