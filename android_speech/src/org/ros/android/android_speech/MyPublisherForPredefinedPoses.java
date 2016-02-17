package org.ros.android.android_speech;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

/**
 * Created by kkuei on 2016/2/17.
 */
public class MyPublisherForPredefinedPoses extends AbstractNodeMain {
    private String topic_name = "/andbot/predefinedPoses";
    private Publisher<std_msgs.UInt8> publisher;

    public MyPublisherForPredefinedPoses() {
        topic_name = "/andbot/predefinedPoses";
    }

    public MyPublisherForPredefinedPoses(String topic)
    {
        topic_name = topic;
    }
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("MyPublisherForPredefinedPoses/publisher");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {

        publisher = connectedNode.newPublisher(topic_name, std_msgs.UInt8._TYPE); ///*std_msgs.String._TYPE*/


    }

    public void publishMessage(int pose)    {
        std_msgs.UInt8 msg = publisher.newMessage();
        msg.setData((byte) pose);
        publisher.publish(msg);
    }
}
