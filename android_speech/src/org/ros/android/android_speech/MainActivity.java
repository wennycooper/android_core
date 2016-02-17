package org.ros.android.android_speech;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.ros.android.RosActivity;
import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import org.ros.android.view.RosImageView;
//import org.ros.rosjava_tutorial_pubsub.Talker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import sensor_msgs.CompressedImage;

/**
 * Created by kkuei on 2016/02/16
 */
public class MainActivity extends RosActivity {

    private MyPublisher myPublisher;
    private MyPublisherForPredefinedPoses myPublisherForPredefinedPoses;
    private MyPublisherForFollowMe myPublisherForFollowMe;
    private MySubscriber mySubscriber;
    TextToSpeech tts;

//    private Talker talker;

    private Button mButton1;
    private static final int RESULT_SPEECH = 777;
    private static final String TAG = "ANDBOT_SPEECH";
//    TextToSpeech tts;
    private SpeechRecognizer speech = null;
//    private Intent recognizerIntent;


    public MainActivity() {
        super("ANDBOT_SPEECH", "ANDBOT_SPEECH");
    }

    public void myMethod()   {
        Log.i(TAG, "myMethod");
        runOnUiThread(new Runnable() {
            public void run()
            {
                mButton1.performClick();
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tts = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            tts.setLanguage(Locale.US);
                            tts.setSpeechRate((float) 0.85);
                            tts.setPitch((float) 0.4);

                            Log.i(TAG,
                                    "avalib"
                                            + tts.isLanguageAvailable(Locale.US));
                            Log.i(TAG, "" + tts.getLanguage());

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Speech initialize error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();

        mButton1 = (Button) findViewById(R.id.button);
        //mButton1.setEnabled(false);
        mButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start listening ....


                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK);

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Speech not supported", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            //speech.destroy();
            Log.i(TAG, "destroy");
        }

    }

    /*
    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech");

    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(TAG, "FAILED " + errorMessage);

    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(TAG, "onReadyForSpeech");
    }
    */

    /*
    @Override
    public void onResults(Bundle results) {
        Log.i(TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        //String text = "";


        if (matches.size() > 0) {
            Log.d(TAG, matches.get(0));


            String text = matches.get(0);
            myPublisher.publishMessage(text);  // added by KKUEI
        }

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(TAG, "onRmsChanged: " + rmsdB);

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
    */


    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        myPublisher = new MyPublisher();
        myPublisherForPredefinedPoses = new MyPublisherForPredefinedPoses();
        myPublisherForFollowMe = new MyPublisherForFollowMe();
        mySubscriber = new MySubscriber(MainActivity.this);
        //talker = new Talker();

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());
        nodeMainExecutor.execute(myPublisher, nodeConfiguration);
        nodeMainExecutor.execute(myPublisherForPredefinedPoses, nodeConfiguration);
        nodeMainExecutor.execute(myPublisherForFollowMe, nodeConfiguration);
        nodeMainExecutor.execute(mySubscriber, nodeConfiguration);
        //nodeMainExecutor.execute(talker, nodeConfiguration);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult()");
        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> matches = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (matches.size() > 0) {
                        Log.i(TAG, matches.get(0));


                        String text = matches.get(0);
                        myPublisher.publishMessage(text);  // added by KKUEI



                        /*
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        */

                        String nice_to_meet_you = "nice to meet you";
                        String your_name = "your name";
                        String introduce_yourself = "introduce yourself";
                        String limp = "limp";
                        String arms_movement = "arms movement";
                        String kung_fu = "kung fu";
                        String stop_follow_me = "stop follow me";
                        String follow_me = "follow me";

                        // [KKUEI] Process speech commands
                        if (text.contains(your_name)) {
                            //myPublisher.publishMessage(text);
                            tts.speak("My name is Andbot, sir", TextToSpeech.QUEUE_FLUSH, null);
                        } else if (text.contains(nice_to_meet_you)) {
                            tts.speak("It's my pleasure to meet you too.", TextToSpeech.QUEUE_FLUSH, null);


                        } else if (text.contains(introduce_yourself)) {
                            tts.speak("Sure!", TextToSpeech.QUEUE_ADD, null);
                            tts.speak("My name is Andbot", TextToSpeech.QUEUE_ADD, null);
                            tts.speak("I was developed by Advanced Robotics Technologies.", TextToSpeech.QUEUE_ADD, null);
                            tts.speak("I have three major functions, home care, home security and home entertainment", TextToSpeech.QUEUE_ADD, null);
                            tts.speak("As a family member, I hope I can bring security, happiness and great pleasant to everybody. Over.", TextToSpeech.QUEUE_ADD, null);

                        } else if (text.contains(arms_movement)) {
                            tts.speak("Yes, sir. I'm now showing you the arms movements", TextToSpeech.QUEUE_FLUSH, null);
                            myPublisherForPredefinedPoses.publishMessage(3);


                        } else if (text.contains(kung_fu)) {
                            tts.speak("Sure, here is the kung fu.", TextToSpeech.QUEUE_FLUSH, null);
                            myPublisherForPredefinedPoses.publishMessage(1);

                            // delay for a while
                            /*
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            myPublisherForPredefinedPoses.publishMessage(0);
                            */
                        } else if (text.contains(limp)) {
                            tts.speak("Yes, sir. limp", TextToSpeech.QUEUE_FLUSH, null);
                            myPublisherForPredefinedPoses.publishMessage(0);


                        } else if (text.contains(stop_follow_me)) {
                            tts.speak("Yes, sir. follow me is stopped.", TextToSpeech.QUEUE_FLUSH, null);
                            myPublisherForFollowMe.publishMessage(0);
                        } else if (text.contains(follow_me)) {
                            tts.speak("Yes, sir. follow me is started.", TextToSpeech.QUEUE_FLUSH, null);
                            myPublisherForFollowMe.publishMessage(1);
                        }
                    }

                }
                break;

            default:
                break;
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }
}
