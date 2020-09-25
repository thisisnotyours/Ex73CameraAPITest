package com.suek.ex73cameraapitest;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


//SurfaceView : 고속버퍼뷰 (카메라 미리보기를 빠르게 그려내는 뷰)
public class MyCameraView extends SurfaceView implements SurfaceHolder.Callback {   //MyCamera 는 surface 이면서 callback 임

    //SurfaceView 가 보여줄 뷰들을 관리하는 관리자객체가 필요
    SurfaceHolder holder;

    Camera camera;    //hardware 꺼


    public MyCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder= getHolder();       //holder -> 고속으로(빠르게) surface 에 그려냄
        holder.addCallback(this);   //자동발동(리스너 같은것)
    }

    //이 뷰가 화면 Activity 에 보여질때 발동
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //카메라 셔터 열기!!  -객체리턴
        camera= Camera.open(0);   //0이 back 카메라, 1이 front 카메라

        //미리보기 설정
        try {
            camera.setPreviewDisplay(holder);
            //카메라는 무조건 가로방향임
            //그래서 프리뷰를 90도 회전
            camera.setDisplayOrientation(90);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //surfaceCreated() 실행 후에 뷰의 사이즈가 결정되었을 때 발동
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //카메라 미리보기 시작
        camera.startPreview();
    }

    //이 뷰가 화면에 보여지지 않을때
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //미리보기 종료
        camera.stopPreview();

        //카메라 셔터닫기
        camera.release();
        camera= null;
    }
}
