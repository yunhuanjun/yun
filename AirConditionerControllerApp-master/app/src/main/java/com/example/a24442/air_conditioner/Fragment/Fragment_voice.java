package com.example.a24442.air_conditioner.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;


import com.alibaba.idst.nls.realtime.NlsListener;
import com.alibaba.idst.nls.realtime.StageListener;
import com.alibaba.idst.nls.realtime.NlsClient;
import com.alibaba.idst.nls.realtime.internal.protocol.NlsResponse;
import com.alibaba.idst.nls.realtime.internal.protocol.NlsRequest;
import com.example.a24442.air_conditioner.R;


import java.util.HashMap;

/**
 * Created by 24442 on 2018/5/8.
 */

public class Fragment_voice extends Fragment {

    private boolean isRecognizing = false;
    private ImageButton talk;
    private EditText theResult;
    private EditText other;
    private Context context;
    private NlsClient mNlsClient;
    private NlsRequest mNlsRequest;
    private HashMap<Integer,String> resultMap = new HashMap<Integer, String>();
    private int sentenceId = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_voice,container,false);
        context = getActivity().getApplicationContext();

        talk = (ImageButton) view.findViewById(R.id.clickToTalk);
        theResult = (EditText) view.findViewById(R.id.result);
        other = (EditText) view.findViewById(R.id.other);

        String appkey = "nls-service-shurufa16khz"; //请设置文档中Appkey

        mNlsRequest = new NlsRequest();
        mNlsRequest.setAppkey(appkey);    //appkey请从 "快速开始" 帮助页面的appkey列表中获取
        mNlsRequest.setResponseMode("normal");//流式为streaming,非流式为normal

        NlsClient.configure(getActivity().getApplicationContext()); //全局配置
        mNlsClient = NlsClient.newInstance(getActivity(), mRecognizeListener, mStageListener,mNlsRequest);

        mNlsClient.setMaxRecordTime(60000);  //设置最长语音
        mNlsClient.setMaxStallTime(1000);    //设置最短语音
        mNlsClient.setMinRecordTime(500);    //设置最大录音中断时间
        mNlsClient.setRecordAutoStop(false);  //设置VAD
        mNlsClient.setMinVoiceValueInterval(200); //设置音量回调时长

        talk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initStartRecognizing();
                        break;
                    case MotionEvent.ACTION_UP:
                        initStopRecognizing();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initStartRecognizing(){
        isRecognizing = true;
        mNlsRequest.authorize("LTAILzr2xVsOJrZZ", "EppiCZG0mYavgldp1TYbgStwETLBOF"); //请替换为用户申请到的数加认证key和密钥
        mNlsClient.start();
    }

    private void initStopRecognizing(){
        isRecognizing = false;
        mNlsClient.stop();
    }

    private NlsListener mRecognizeListener = new NlsListener() {

        @Override
        public void onRecognizingResult(int status, NlsResponse result) {
            switch (status) {
                case NlsClient.ErrorCode.SUCCESS:

                    if (result!=null){
                        if(result.getResult()!=null) {
                            //获取句子id对应结果。
                            if (sentenceId != result.getSentenceId()) {
                                sentenceId = result.getSentenceId();
                            }
                            resultMap.put(sentenceId,result.getText());

                            Log.i("asr", "[demo] callback onRecognizResult :" + result.getResult().getText());
                            theResult.setText(resultMap.get(sentenceId));
                            other.setText(JSON.toJSONString(result.getResult()));
                        }
                    }else {
                        Log.i("asr", "[demo] callback onRecognizResult finish!" );
                        theResult.setText("Recognize finish!");
                        other.setText("Recognize finish!");
                    }
                    break;
                case NlsClient.ErrorCode.RECOGNIZE_ERROR:
                    Toast.makeText(getActivity(), "recognizer error", Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.RECORDING_ERROR:
                    Toast.makeText(getActivity(),"recording error", Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.NOTHING:
                    Toast.makeText(getActivity(),"nothing", Toast.LENGTH_LONG).show();
                    break;
            }
            isRecognizing = false;
        }


    } ;




    private StageListener mStageListener = new StageListener() {
        @Override
        public void onStartRecognizing(NlsClient recognizer) {
            super.onStartRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onStopRecognizing(NlsClient recognizer) {
            super.onStopRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
            theResult.setText("");
            mNlsClient.stop();

        }

        @Override
        public void onStartRecording(NlsClient recognizer) {
            super.onStartRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onStopRecording(NlsClient recognizer) {
            super.onStopRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onVoiceVolume(int volume) {
            super.onVoiceVolume(volume);
        }

    };

}
