package com.example.xmax.mycamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO=2;
    public static final int CHOOSE_PHOTO=3;
    private Button takePhoto;
    private Button choosePhoto;
    private ImageView picture;
    private Uri imageUri;
    //剪切过后的原图片
    private Bitmap sourcePic;
    //方便图片命名
    //public static Time t;
    public static String fileName;
    private List<PictureInfo> pictureList = new ArrayList<PictureInfo>();
    public class PictureInfo{
        private String file_path;
        private String file_name;
        public PictureInfo(String file_path,String file_name){
            this.file_name=file_name;
            this.file_path=file_path;
        }
        public String getPath(){
            return file_path;
        }
        public String getName(){
            return file_name;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取button和imageView的实例
        takePhoto = (Button)findViewById(R.id.take_photo);
        picture = (ImageView)findViewById(R.id.picture);
        choosePhoto = (Button)findViewById(R.id.choose_photo);
        //在button上注册点击事件
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File appDir = new File(Environment.getExternalStorageDirectory(),"charsCut");
                if(!appDir.exists()){//若文件夹不存在，则创建目录
                    appDir.mkdir();
                }
                fileName = System.currentTimeMillis()+"_1.jpg";
                File image=new File(appDir,fileName);
                try{
                    if(image.exists()){//防止图像重名，保存最新图像
                        image.delete();
                    }
                    image.createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }

                imageUri = Uri.fromFile(image);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);//启动相机程序
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,imageUri));
            }
        });
        choosePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File appDir = new File(Environment.getExternalStorageDirectory(),"charsCut");
                if(!appDir.exists()){//若文件夹不存在，则创建目录
                    appDir.mkdir();
                }
                fileName = System.currentTimeMillis()+"_1.jpg";
                File image=new File(appDir,fileName);
                try{
                    if(image.exists()){//防止图像重名，保存最新图像
                        image.delete();
                    }
                    image.createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }

                imageUri = Uri.fromFile(image);
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                intent.putExtra("crop",true);
                intent.putExtra("scale",true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,CROP_PHOTO);//启动相机程序
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,imageUri));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        sourcePic = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        //picture.setImageBitmap(Cut.doCut(bitmap));
                        picture.setImageBitmap(sourcePic);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(Environment.getExternalStorageDirectory())));
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.row_cut:
                if(sourcePic!=null) {
                    picture.setImageBitmap(Cut.doCut(sourcePic));
                }
                break;
            case R.id.col_cut:
                if(sourcePic!=null){
                    picture.setImageBitmap(Cut.doCut2(sourcePic));
                }
                break;
            default:
        }
        return true;
    }

}
