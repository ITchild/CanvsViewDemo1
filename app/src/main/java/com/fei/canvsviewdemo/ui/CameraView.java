package com.fei.canvsviewdemo.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;

    public CameraView(Context context) {
        super(context);
        init();
    }

    private void init() {
        SurfaceHolder mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        startCameraPreview();
    }

    public void startCameraPreview() {
        camera = Camera.open();
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 已经获得Surface的width和height，设置Camera的参数
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(width, height);
        List<Camera.Size> vSizeList = parameters.getSupportedPictureSizes();
        int twidth = 1280;
        int theight = 720;
        for (int num = 0; num < vSizeList.size(); num++) {
            Camera.Size vSize = vSizeList.get(num);
            Log.d("support_size", vSize.width + "x" + vSize.height);
            if (vSize.width > 1000 && vSize.width < 2000) {
                theight = vSize.height;
                twidth = vSize.width;
            }
        }
        parameters.setPictureSize(twidth, theight);
        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            //非常罕见的情况
            //个别机型在SupportPreviewSizes里汇报了支持某种预览尺寸，但实际是不支持的，设置进去就会抛出RuntimeException.
            e.printStackTrace();
            try {
                //遇到上面所说的情况，只能设置一个最小的预览尺寸
                parameters.setPreviewSize(1920, 1080);
                camera.setParameters(parameters);
            } catch (Exception e1) {
                //到这里还有问题，就是拍照尺寸的锅了，同样只能设置一个最小的拍照尺寸
                e1.printStackTrace();
                try {
                    parameters.setPictureSize(1920, 1080);
                    camera.setParameters(parameters);
                } catch (Exception ignored) {
                }
            }
        }

        //点击屏幕的聚焦
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                focus();
            }
        });
        try {
            // 设置显示
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException exception) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        destroy();
    }

    private void destroy() {
        camera.stopPreview();
        camera.release();
    }

    public void focus() {
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {

                }
            }
        });
    }

    public void capture() {
        camera.takePicture(null, null, new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // data是一个原始的JPEG图像数据，
                // 在这里我们可以存储图片，很显然可以采用MediaStore
                // 注意保存图片后，再次调用startPreview()回到预览
                Uri imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
                try {
                    Bitmap preparedBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
                    preparedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                    preparedBitmap.recycle();

                    System.out.println("uri : " + imageUri.toString());
                    OutputStream os = getContext().getContentResolver().openOutputStream(imageUri);
                    os.write(byteArrayOutputStream.toByteArray());
                    os.flush();
                    os.close();

                    Cursor cr = getContext().getContentResolver().query(imageUri, null, null, null, null);
                    int columnIndex = cr.getColumnIndex(MediaStore.Images.Media.DATA);
                    String path = null;
                    if (cr.moveToFirst()) {
                        path = cr.getString(columnIndex);
                    }
                    Log.d("save", "saved in : " + path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                camera.startPreview();
            }
        });
    }
}
