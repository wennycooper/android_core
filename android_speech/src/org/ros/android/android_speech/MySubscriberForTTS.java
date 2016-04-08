package org.ros.android.android_speech;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;


public class MySubscriberForTTS extends AbstractNodeMain {
    private MainActivity    mInstance;
    private int count = 0;

    public MySubscriberForTTS(MainActivity act) {
        super();
        mInstance = act;
        //mInstance.myMethod();
    }



    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("MySubscriberForTTS/MySubscriberForTTS");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Log log = connectedNode.getLog();
        Subscriber<std_msgs.String> subscriber = connectedNode.newSubscriber("/andbot/TTS/cmd", std_msgs.String._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.String>() {
            @Override
            public void onNewMessage(std_msgs.String msg) {
                log.info("Received message.getData(): " + msg.getData());
                //if (count > 0) mInstance.myMethod();
                //count ++;
                //byte = msg.getData();
                mInstance.ttsSpeak(msg.getData());
            }
        });
    }
}